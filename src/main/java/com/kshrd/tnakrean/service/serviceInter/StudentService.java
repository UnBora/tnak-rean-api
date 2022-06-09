package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.student.response.StudentResponse;

import java.util.List;

public interface StudentService {
    List<StudentResponse> getStudent(StudentResponse user_role_id);
}
