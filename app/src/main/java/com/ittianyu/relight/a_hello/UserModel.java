package com.ittianyu.relight.a_hello;

import java.util.Random;

public class UserModel {
    private static UserModel instance;

    public static UserModel getInstance() {
        if (null == instance)
            instance = new UserModel();
        return instance;
    }

    public int randomId() {
        Random random = new Random();
        return random.nextInt(100000);
    }

    public String randomName() {
        String[] names = {"tom", "ketty", "marry", "jone", "bob", "jackson", "cat"};
        Random random = new Random();
        int index = random.nextInt(names.length);
        return names[index];
    }

    public User getUser() {
        int id = randomId();
        String name = randomName();
        return new User(id, name);
    }
}
