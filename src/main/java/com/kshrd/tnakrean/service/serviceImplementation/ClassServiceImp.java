package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.repository.ClassRepository;
import com.kshrd.tnakrean.service.serviceInter.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassServiceImp implements ClassService {
    final ClassRepository classRepository;

    @Autowired
    public ClassServiceImp(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Override
    public void insertClass(String class_name) {
        classRepository.insertClass(class_name);
    }

    @Override
    public void deleteClass(Integer classId) {
        classRepository.deleteClass(classId);
    }
}
