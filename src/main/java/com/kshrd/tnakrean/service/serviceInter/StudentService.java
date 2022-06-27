package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.user.response.GetStudentByClassIDResponse;
import com.kshrd.tnakrean.model.user.response.GetStudentByIDResponse;
import com.kshrd.tnakrean.model.user.response.GetAllStudentResponse;

import java.util.List;

public interface StudentService {
    //    Get all Student
    List<GetAllStudentResponse> getAllStudent();

    //    Get Student by ID
    GetStudentByIDResponse getStudentById(Integer id);

    //    Delete Student User
    void studentDeleteAccount(Integer id);

    //    Student Deactivate account
    void studentDeactivateAccount(Integer id);

    //    Student activate x
    void studentActivateAccount(Integer id);

    //    Student leave class
    void studentLeaveClassService(Integer users_id, Integer classroom_id, Integer class_id);

    //    Select User By Class ID
    List<GetStudentByClassIDResponse> selectStudentByClassID(Integer class_id, Integer classroom_id);

    //Insert Student
    void insertStudent(Integer user_id, Integer classroom_id, Integer class_id);

    //update profile
    void updateprofileByID(Integer user_id, String name, String username,String img, String gender);
}
