package com.example.androidstudy.db;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.LitePalSupport;

public class Student extends LitePalSupport implements Parcelable {
    private String name;
    private int age;
    private String sex;

    public Student() {
    }

    protected Student(Parcel in) {
        name = in.readString();
        age = in.readInt();
        sex = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(sex);
    }
}

