package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.classmaterials.request.GetClassRequest;
import com.kshrd.tnakrean.model.classmaterials.response.ClassByUserTeacherIdResponse;
import com.kshrd.tnakrean.repository.ClassRepository;
import com.kshrd.tnakrean.service.serviceInter.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceImp implements ClassService {
    final ClassRepository classRepository;

    @Autowired
    public ClassServiceImp(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Override
    public void insertClass( String class_name, String img) {
        classRepository.insertClass(class_name, img);
    }

    @Override
    public Boolean deleteClass(Integer classId) {
        return classRepository.deleteClass(classId);

    }

    @Override
    public void UpdateClass(Integer id, String className,String image) {
        classRepository.updateClass(id, className,image);
    }

    @Override
    public List<GetClassRequest> getAllClass() {
        return classRepository.getAllClass();
    }

    @Override
    public void creatClassByUserID(Integer id, String className) {
        classRepository.creatClassByUserID(id, className);
    }

    @Override
    public List<ClassByUserTeacherIdResponse> getByTeacherUserId(Integer user_id,Integer classroom_id) {
        return classRepository.getByTeacherUserId(user_id,classroom_id);
    }
}
