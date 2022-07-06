package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.user.response.GetStudentByClassIDResponse;
import com.kshrd.tnakrean.model.user.response.GetStudentByIDResponse;
import com.kshrd.tnakrean.model.user.response.GetAllStudentResponse;
import com.kshrd.tnakrean.model.user.response.StudentRequestClassResponse;

import java.util.List;

public interface StudentService {

    //    Get all Student
    List<GetAllStudentResponse> getAllStudent();

    //    Get Student by ID
    GetStudentByIDResponse getStudentById(Integer id);

    //    Student leave class
    void studentLeaveClassService(Integer users_id, Integer classroom_id, Integer class_id);

    //    Select User By Class ID
    List<GetStudentByClassIDResponse> selectStudentByClassID(Integer class_id);

    //Insert Student
    void insertStudent(Integer user_id);

    //update profile
    void updateprofileByID(Integer user_id, String name, String username,String img, String gender);

    List<StudentRequestClassResponse> getRequestClass(Integer classroom_id, Integer class_id);
}
