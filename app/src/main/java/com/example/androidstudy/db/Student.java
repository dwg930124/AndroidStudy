package com.example.androidstudy.db;

import org.litepal.crud.LitePalSupport;

public class Student extends LitePalSupport {
    private String name;
    private int age;
    private String sex;

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

