package org.kxw.commons.data;

import java.util.List;

/**
 * Create by kangxiongwei on 2017/11/25 上午12:42
 */
public class Teacher {

    private String name;
    private int sex;
    private List<Student> students;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
