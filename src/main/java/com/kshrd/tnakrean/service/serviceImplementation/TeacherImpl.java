package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.teacher.response.TeacherResponse;
import com.kshrd.tnakrean.repository.TeacherRepository;
import com.kshrd.tnakrean.service.serviceInter.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherImpl implements TeacherService {
    final TeacherRepository teacherRepository;

    public TeacherImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<TeacherResponse> getAllTeacher() {
        return teacherRepository.getAllTeacher();
    }

    @Override
    public TeacherResponse getTeacherById(Integer user_id) {
        return teacherRepository.getTeacherById(user_id);
    }



}
