package org.kxw.commons.data;

/**
 * Create by kangxiongwei on 2017/11/25 上午12:41
 */
public class Student {

    private String username;
    private int age;
    private Teacher teacher;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
