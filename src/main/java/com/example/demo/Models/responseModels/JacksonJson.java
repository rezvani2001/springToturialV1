package com.example.demo.Models.responseModels;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JacksonJson {
    public static Object transfer(String request, Object object) throws Exception {
        Pattern pattern = Pattern.compile(" *\"[0-9a-z A-z]+\" *: *\"[0-9a-z A-z]+\" *");
        Matcher matcher = pattern.matcher(request);

        Class objectClass = object.getClass();

        while (matcher.find()) {
            String[] strings = matcher.group().split("\" *: *\"");
            strings[0] = strings[0].split("\"")[1];
            strings[1] = strings[1].split("\"")[0];

            try {
                Class targetType = objectClass.getDeclaredField(strings[0]).getType();
                objectClass.getDeclaredField(strings[0])
                        .set(object, targetType == String.class? strings[1] : Integer.parseInt(strings[1]));
            } catch (Exception exception) {
                throw new Exception("bad argument");
            }

        }

        return object;
    }
}