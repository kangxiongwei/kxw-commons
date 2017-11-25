package org.kxw.commons.utils;

import org.junit.Assert;
import org.junit.Test;
import org.kxw.commons.data.Student;
import org.kxw.commons.data.Teacher;

import java.util.*;

/**
 * Create by kangxiongwei on 2017/11/25 上午12:38
 */
public class ReflectUtilsTest {

    @Test
    public void test() throws Exception {
        Student student = new Student();
        student.setUsername("zhangsan");
        student.setAge(20);

        Student student2 = new Student();
        student2.setUsername("lisi");
        student2.setAge(18);

        Teacher teacher = new Teacher();
        teacher.setName("zhanglaoshi");
        teacher.setSex(1);
        teacher.setStudents(Arrays.asList(student, student2));

        Map<String, Set<Student>> setMap = new HashMap<>();
        Set<Student> set = new HashSet<>();
        set.add(student);
        set.add(student2);
        setMap.put("1", set);

        Map<String, Object> map = ReflectUtils.bean2DeepMap(setMap);
        System.out.println(map);
    }

    @Test
    public void testPrimitive() {
        Student student = new Student();
        student.setUsername("zhangsan");
        student.setAge(20);
        Assert.assertTrue(student.getClass().isSynthetic());
        Assert.assertFalse(student.getClass().isPrimitive());
    }


}
