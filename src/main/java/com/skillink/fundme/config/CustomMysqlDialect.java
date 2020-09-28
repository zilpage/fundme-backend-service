package com.skillink.fundme.config;

import org.hibernate.dialect.MySQL5InnoDBDialect;

public class CustomMysqlDialect extends MySQL5InnoDBDialect {

    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}