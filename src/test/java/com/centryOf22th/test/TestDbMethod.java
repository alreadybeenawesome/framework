package com.centryOf22th.test;

import com.centryOf22th.framework.dao.StudentDao;
import com.centryOf22th.framework.model.Student;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by louis on 16-10-31.
 */
public class TestDbMethod extends BaseTest {


    @Autowired
    private StudentDao studentDao;
    @Test
    public void testSave(){
        Student student =new Student();
        student.setStudentName("yuweijai");
        studentDao.save(student);
    }


    @Test
    public void testUpdate(){
        Student student =new Student();
        student.setStudentName("louis.yu hahahah");
        student.setId(1);
        studentDao.update(student);
    }

    @Test
    public void testGet(){

        Student student =studentDao.get(1);
        System.out.println(student.getStudentName()+"==="+student.getId());
    }
}
