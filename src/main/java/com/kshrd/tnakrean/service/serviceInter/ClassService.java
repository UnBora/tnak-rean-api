package com.kshrd.tnakrean.service.serviceInter;

import io.swagger.v3.oas.models.security.SecurityScheme;

public interface ClassService {

//    Insert Class to Class Table
    void insertClass(String class_name);

//    Delete Class fromm Class Table
    void deleteClass(Integer classId);
}
