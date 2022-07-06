package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.classmaterials.request.GetClassRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassByUserTeacherIdResponse;

import java.util.List;

public interface ClassService {

    //    Insert Class to Class Table
    void insertClass(String class_name);

    //    Delete Class fromm Class Table
    Boolean deleteClass(Integer classId);

    //    Update Class
    void UpdateClass(Integer id, String className);

    //Get Class
    List<GetClassRequest> getAllClass();

//    create class by user ID
    void creatClassByUserID(Integer id, String className);

    List<ClassByUserTeacherIdResponse> getByTeacherUserId(Integer user_id,Integer classroom_id);
}
