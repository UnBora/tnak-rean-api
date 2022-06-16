package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.classModel.response.ClassUpdateResponse;
import com.kshrd.tnakrean.model.classroom.request.ClassroomRequest;
import com.kshrd.tnakrean.model.classroom.response.ClassroomResponse;
import com.kshrd.tnakrean.repository.ClassroomRepository;
import com.kshrd.tnakrean.service.serviceInter.ClassroomService;
import org.postgresql.util.GT;
import org.postgresql.util.PSQLException;
import org.postgresql.util.PSQLState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ClassroomServiceImp implements ClassroomService {

    final ClassroomRepository classroomRepository;
    @Autowired
    public ClassroomServiceImp(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @Override
    public List<ClassroomResponse> getAllClassroom() {
        return classroomRepository.getAllClassroom();
    }

    @Override
    public void insertClassroom(Integer class_id, Integer created_by, String des, String name){
        try {
            classroomRepository.insertClassroom(class_id, created_by, des, name);
        }catch (Exception e){

        }

    }

    @Override
    public ClassroomResponse getClassroomByID(Integer id) {
        return classroomRepository.getClassroomByID(id);
    }

    @Override
    public void updateClassroom(Integer class_id, Integer created_by, String des, String name) {
        try {
            classroomRepository.updateclassroom(class_id, created_by,des,name);
        }catch (Exception e){


        }
    }
}
