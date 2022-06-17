package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.user.response.TeacherResponse;
import com.kshrd.tnakrean.repository.TeacherRepository;
import com.kshrd.tnakrean.service.serviceInter.TeacherService;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImp implements TeacherService {
    final
    TeacherRepository teacherRepository;

    public TeacherServiceImp(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public TeacherResponse getTeacherById(Integer id) {
        return teacherRepository.getTeacherById(id);
    }


}
