package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.teacher.response.TeacherResponse;

import java.util.List;

public interface TeacherService {
    List<TeacherResponse> getAllTeacher();
    TeacherResponse getTeacherById(Integer user_id);


}
