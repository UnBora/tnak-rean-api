package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.student.response.StudentResponse;
import com.kshrd.tnakrean.repository.StudentRepository;
import com.kshrd.tnakrean.service.serviceInter.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImp implements StudentService {

    final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImp(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentResponse> getStudent(Integer user_role_id) {
        return studentRepository.getStudentFromDB(user_role_id);
    }

    @Override
    public StudentResponse getSudentById(Integer id) {
        return studentRepository.getStudentFromDBById(id);
    }

    @Override
    public StudentResponse deleteStudent(Integer id) {
        return studentRepository.deleteStudent(id);
    }
}
