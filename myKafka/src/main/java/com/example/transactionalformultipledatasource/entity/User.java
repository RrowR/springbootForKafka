package com.example.transactionalformultipledatasource.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * @Author Rrow
 * @Date 2023/4/3 16:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

    private String username;
    private String password;

}
