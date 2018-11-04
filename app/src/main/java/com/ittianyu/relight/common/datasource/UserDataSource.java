package com.ittianyu.relight.common.datasource;

import com.ittianyu.relight.common.bean.UserBean;

import java.util.Random;

public class UserDataSource {
    private static UserDataSource instance = new UserDataSource();
    public static UserDataSource getInstance() {
        return instance;
    }

    private Random random = new Random();
    private static String[] NAMES = {
            "tomcat", "jboss", "wildfly", "redis", "mongodb",
            "dubbo", "thrift", "http", "zookeeper", "spring",
            "mybatis", "ejb", "solr", "lucene", "hadoop",
            "spark", "storm", "kylin", "elasticsearch", "kafka",
            "akka", "logstash", "flume", "mahout"
    };

    public UserBean getUser() {
        int id = randomId();
        String name = randomName();
        return new UserBean(id, name);
    }

    private int randomId() {
        return random.nextInt(1000000);
    }

    private String randomName() {
        int index = random.nextInt(NAMES.length);
        return NAMES[index];
    }
}
