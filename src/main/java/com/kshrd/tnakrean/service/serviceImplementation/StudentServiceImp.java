package com.kshrd.tnakrean.service.serviceImplementation;


import com.kshrd.tnakrean.model.user.response.GetStudentByClassIDResponse;
import com.kshrd.tnakrean.model.user.response.GetStudentByIDResponse;
import com.kshrd.tnakrean.model.user.response.GetAllStudentResponse;
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
    public List<GetAllStudentResponse> getAllStudent() {
        return studentRepository.getStudentFromDB();
    }

    @Override
    public GetStudentByIDResponse getStudentById(Integer id) {
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
    public void studentActivateAccount(Integer id) {
        studentRepository.studentActivateAccount(id);
    }

    @Override
    public void studentLeaveClassService(Integer users_id, Integer classroom_id, Integer class_id) {
        studentRepository.studentLeaveClassDB(users_id, classroom_id, class_id);
    }

    @Override
    public List<GetStudentByClassIDResponse> selectStudentByClassID(Integer class_id, Integer classroom_id) {
        return studentRepository.selectStudentByClassID(class_id, classroom_id);
    }

    @Override
    public void insertStudent(Integer users_id, Integer classroom_id, Integer class_id) {
        studentRepository.insertUserToTableStudent(users_id, classroom_id, class_id);
    }

    @Override
    public void updateprofileByID(Integer user_id, String name, String username,String img, String gender) {
        studentRepository.updateProfile(user_id,name, username, img, gender);
    }


}
