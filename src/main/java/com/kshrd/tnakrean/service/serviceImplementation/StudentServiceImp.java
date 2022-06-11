package com.kshrd.tnakrean.service.serviceImplementation;

//import com.kshrd.tnakrean.model.student.request.StudentRequest;

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
    public void studentDeleteAccount(Integer id) {
        studentRepository.studentDeleteAccount(id);
    }

    @Override
    public void studentDeactivateAccount(Integer id) {
        studentRepository.studentDeactivateAccount(id);
    }

    @Override
    public void studentLeaveClass(Integer id) {
        studentRepository.studentLeaveClass(id);
    }

    @Override
    public void selectStudentByClassID(Integer user_id, Integer class_id) {
        studentRepository.selectStudentByClassID(user_id, class_id);
    }

    @Override
    public void updateClassID(Integer new_class_id, Integer user_id) {
        studentRepository.updateClassID(new_class_id, user_id);
    }
}
