package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.student.response.StudentResponse;
import com.kshrd.tnakrean.repository.StudentRepository;
import com.kshrd.tnakrean.service.serviceInter.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImplementation implements StudentService {
    final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImplementation(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void createStudent(StudentResponse studentResponse) {

    }
}
