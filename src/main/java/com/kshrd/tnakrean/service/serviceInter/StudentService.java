package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.student.request.StudentRequest;
import com.kshrd.tnakrean.model.student.response.StudentResponse;

import java.util.List;

public interface StudentService {
    //    Get all Student
    List<StudentResponse> getStudent(Integer user_role_id);

    //    Get Student by ID
    StudentResponse getSudentById(Integer id);

    //    Update Student Class ID
    void updateClassID(Integer new_class_id, Integer user_id);

    //    Delete Student User
    StudentRequest deleteStudent(Integer id);

}
