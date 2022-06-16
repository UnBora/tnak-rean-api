package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.classmaterials.response.ClassMaterialResponse;
import com.kshrd.tnakrean.model.classroom.response.ClassroomResponse;
import com.kshrd.tnakrean.model.teacher.request.TeacherStatusRequest;
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

    @Override
    public List<ClassMaterialResponse> getAllMaterialByCreatedById(Integer createdId) {
        return teacherRepository.getAllMaterialByCreatedById(createdId);
    }

    @Override
    public List<ClassroomResponse> getAllClassRoomByTeacherId(Integer createdId) {
        return teacherRepository.getAllClassRoomByTeacherId(createdId);
    }

    @Override
    public Boolean teacherStatus(TeacherStatusRequest teacherStatusRequest) {
        return teacherRepository.teacherStatus(teacherStatusRequest);
    }

    @Override
    public Boolean teacherDeleteAccount(Integer user_id) {
        return teacherRepository.teacherDeleteAccount(user_id);
    }

    @Override
    public Boolean deactivateTeacherAccount(Integer user_id) {
        return teacherRepository.deactivateTeacherAccount(user_id);
    }

    @Override
    public Boolean activateTeacherAccount(Integer user_id) {
        return teacherRepository.activateTeacherAccount(user_id);
    }


}
