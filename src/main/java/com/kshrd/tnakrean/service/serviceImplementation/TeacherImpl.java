package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialResponse;
import com.kshrd.tnakrean.model.classmaterials.response.ClassroomResponse;
import com.kshrd.tnakrean.model.user.request.TeacherStatusRequest;
import com.kshrd.tnakrean.model.user.response.TeacherByClassAndClassroomResponse;
import com.kshrd.tnakrean.model.user.response.TeacherResponse;
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

    @Override
    public List<TeacherByClassAndClassroomResponse> getByClassAndClassrooms(Integer user_id,Integer class_id, Integer classroom_id) {
        return teacherRepository.getByClassAndClassrooms(user_id,class_id,classroom_id);
    }
}
