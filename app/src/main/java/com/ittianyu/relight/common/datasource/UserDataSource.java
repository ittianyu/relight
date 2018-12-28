package com.ittianyu.relight.common.datasource;

import com.ittianyu.relight.common.bean.UserBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    public UserBean getUserFromRemote() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int id = randomId();
        String name = randomName();
        return new UserBean(id, name);
    }

    public UserBean getUserFromRemoteWithRandomError() throws NetworkErrorException {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (random.nextInt(3) != 1) {
            throw new NetworkErrorException("network error");
        }

        int id = randomId();
        String name = randomName();
        return new UserBean(id, name);
    }

    public List<UserBean> getUsersFromRemote() throws NetworkErrorException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // mock empty and error
        switch (random.nextInt(4)) {
            case 0: {// empty
                return Collections.emptyList();
            }
            case 1: {// error
                throw new NetworkErrorException("network error");
            }
            // other: content
        }

        int count = randomCount();
        List<UserBean> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            int id = randomId();
            String name = randomName();
            UserBean bean = new UserBean(id, name);
            list.add(bean);
        }
        return list;
    }

    private int randomCount() {
        return random.nextInt(30) + 5;
    }

    private int randomId() {
        return random.nextInt(1000000);
    }

    private String randomName() {
        int index = random.nextInt(NAMES.length);
        return NAMES[index];
    }

    public static class NetworkErrorException extends RuntimeException {
        public NetworkErrorException() {
        }

        public NetworkErrorException(String message) {
            super(message);
        }

        public NetworkErrorException(String message, Throwable cause) {
            super(message, cause);
        }

        public NetworkErrorException(Throwable cause) {
            super(cause);
        }
    }
}
