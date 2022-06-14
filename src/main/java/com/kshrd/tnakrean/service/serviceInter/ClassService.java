package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.classModel.request.GetClassRequest;
import com.kshrd.tnakrean.model.classModel.response.ClassUpdateResponse;

import java.util.List;

public interface ClassService {

    //    Insert Class to Class Table
    void insertClass(String class_name);

    //    Delete Class fromm Class Table
    void deleteClass(Integer classId);

    //    Update Class
    void UpdateClass(Integer id, String className);

    //Get Class
    List<GetClassRequest> getAllClass();
}
