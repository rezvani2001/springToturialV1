package com.example.demo.Models;

import org.springframework.stereotype.Component;

@Component
public class ModelManager {

    public static Object getFieldValue(Object sample, String field) throws Exception {
        return sample.getClass().getMethod("get" + field).invoke(sample, null);
    }


    public static void setFieldValue(Object sample, String field, Object value) throws Exception {
        sample.getClass().getMethod("set" + field, value.getClass()).invoke(sample, value);
    }
}
