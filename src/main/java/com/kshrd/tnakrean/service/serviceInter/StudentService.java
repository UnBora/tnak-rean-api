package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.student.response.GetStudentByClassIDResponse;
import com.kshrd.tnakrean.model.student.response.GetStudentByIDResponse;
import com.kshrd.tnakrean.model.student.response.GetAllStudentResponse;

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

    //    Student leave class
    void studentLeaveClass(Integer id);

    //    Select User By Class ID
    List<GetStudentByClassIDResponse>  selectStudentByClassID(Integer class_id);
}
