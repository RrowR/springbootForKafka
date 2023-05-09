package com.example.transactionalformultipledatasource.aspect;

import com.example.transactionalformultipledatasource.anno.MultipleDataSourceTransactional;
import com.example.transactionalformultipledatasource.common.Const;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Stack;

/**
 * @author: Rrow
 * @date: 2023/4/3 23:41
 * Description:
 */
@Component
@Aspect
public class MultipleDataSourceTransactionAspect {

    /**
     * 定义了一个线程安全的数组，里面存储的是栈，栈里面的对象是一个Map，Map的key是事务管理器，value是事务的状态
     */
   private static final ThreadLocal<Stack<Pair<DataSourceTransactionManager, TransactionStatus>>> THREAD_LOCAL = new ThreadLocal<>();
    // private static final ThreadLocal<Map<DataSourceTransactionManager, TransactionStatus>> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 引入springboot上下文，用于获取事务管理器
     */
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Const c;

    // 申明一个事务对象
    private DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();

    {
        // 关闭只读模式
        defaultTransactionDefinition.setReadOnly(false);
        // 设置事务的隔离级别，这里使用的是数据库的默认隔离级别
        defaultTransactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        // 设置事务的传播行为，这里使用的是支持当前事务，如果当前没有事务，就新建一个事务
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    }

    /*
        申明一个切点，切点为使用了@MultipleDataSourceTransactional注解的方法
     */
    @Pointcut("@annotation(com.example.transactionalformultipledatasource.anno.MultipleDataSourceTransactional)")
    public void pointCut() {

    }

    // 在切点之前执行
    @Before("pointCut() && @annotation(transactional)")
    public void before(MultipleDataSourceTransactional transactional) {
        // 从注解里获取事务管理器的名字
        // String[] transactionManagerNames = transactional.transactionManagers();
        String config = c.getConfig();
        String[] transactionManagerNames = config.split(",");
        // 创建一个栈
       Stack<Pair<DataSourceTransactionManager, TransactionStatus>> pairStack = new Stack<>();
        // HashMap<DataSourceTransactionManager, TransactionStatus> pairStack = new HashMap<>();
        for (String transactionManagerName : transactionManagerNames) {
            // 获取事务管理器
            DataSourceTransactionManager transactionManager = applicationContext.getBean(transactionManagerName, DataSourceTransactionManager.class);
            // 获取事务的状态
            TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);
            // 将事务管理器和事务的状态放入栈中
           pairStack.push(new ImmutablePair<>(transactionManager, transactionStatus));
            // pairStack.put(transactionManager, transactionStatus);
        }
        // 将存入了事务管理器和事务状态的栈放入线程安全的数组中
        THREAD_LOCAL.set(pairStack);
    }

    /**
     * 在方法执行完毕后，提交事务
     */
    @AfterReturning("pointCut()")
    public void afterReturning() {
       Stack<Pair<DataSourceTransactionManager, TransactionStatus>> pairStack = THREAD_LOCAL.get();
        // Map<DataSourceTransactionManager, TransactionStatus> map = THREAD_LOCAL.get();
        // for (Map.Entry<DataSourceTransactionManager, TransactionStatus> entry : map.entrySet()) {
        //     DataSourceTransactionManager mapKey = entry.getKey();
        //     TransactionStatus mapValue = entry.getValue();
        //     mapKey.commit(mapValue);
        // }

        // 遍历栈，提交事务
       while (!pairStack.isEmpty()) {
           // 弹出栈顶的事务管理器和事务状态
           Pair<DataSourceTransactionManager, TransactionStatus> pair = pairStack.pop();
           // 提交事务
           pair.getKey().commit(pair.getValue());
       }

        // 移除数组里的栈
        THREAD_LOCAL.remove();
    }

    // 在方法抛出异常后，回滚事务
    @AfterThrowing("pointCut()")
    public void afterThrowing() {
       Stack<Pair<DataSourceTransactionManager, TransactionStatus>> pairStack = THREAD_LOCAL.get();
        // Map<DataSourceTransactionManager, TransactionStatus> map = THREAD_LOCAL.get();
//         for (Map.Entry<DataSourceTransactionManager, TransactionStatus> entry : map.entrySet()) {
//             DataSourceTransactionManager mapKey = entry.getKey();
//             TransactionStatus mapValue = entry.getValue();
//             mapKey.rollback(mapValue);
//         }
//
//         遍历栈，回滚事务
       while (!pairStack.isEmpty()) {
           // 弹出栈顶的事务管理器和事务状态
           Pair<DataSourceTransactionManager, TransactionStatus> pair = pairStack.pop();
           // 回滚事务
           pair.getKey().rollback(pair.getValue());
       }
       THREAD_LOCAL.remove();
    }

}
