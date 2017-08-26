package com.backend_code;

/**
 * Created by Andy on 8/26/2017. data transfer object so dont have to deal with database stuff so
 * much later on. Super over built
 */

public class DatabaseDTO {
    private String semester;
    private String className;
    private float grade;

    public DatabaseDTO(String semester, String className, float grade){
        this.semester = semester;
        this.className = className;
        this.grade = grade;
    }


}
