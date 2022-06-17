package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.classroom.response.ClassroomResponse;
import com.kshrd.tnakrean.model.classroom.response.GetClassByTeacherIdResponse;
import com.kshrd.tnakrean.repository.ClassroomRepository;
import com.kshrd.tnakrean.service.serviceInter.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<GetClassByTeacherIdResponse> getClassByTeacherId(Integer classroom_id, Integer class_id, String teacher_name, String class_name, Integer user_id) {

        return classroomRepository.getClassByTeacherId(classroom_id, class_id, teacher_name, class_name,user_id);
    }


}
