package com.kshrd.tnakrean.service.serviceImplementation;


import com.kshrd.tnakrean.model.classmaterials.request.ClassMaterialRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkUpdateClassClassroomRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkUpdateDeadlineRequest;
import com.kshrd.tnakrean.model.classmaterials.response.*;
import com.kshrd.tnakrean.repository.SubmittableWorkRepository;
import com.kshrd.tnakrean.service.serviceInter.SubmittableService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class SubmittableWorkServiceImpl implements SubmittableService {
    final SubmittableWorkRepository submittableWorkRepository;

    public SubmittableWorkServiceImpl(SubmittableWorkRepository submittableWorkRepository) {
        this.submittableWorkRepository = submittableWorkRepository;
    }

    @Override
    public List<SubmittableWorkResponse> getAll() {
        return submittableWorkRepository.getAll();
    }

    @Override
    public SubmittableWorkResponse getById(int id) {
        return submittableWorkRepository.getById(id);
    }

    @Override
    public boolean insertSubmittableWork(SubmittableWorkRequest submittableWorkRequest) {
        return submittableWorkRepository.insertSubmittableWork(submittableWorkRequest);
    }

    @Override
    public SubmittedWorkResponse updateSubmittableWork(SubmittableWorkUpdateDeadlineRequest submittableWorkUpdateDeadlineRequest) {
        return submittableWorkRepository.updateSubmittableWork(submittableWorkUpdateDeadlineRequest);
    }

    @Override
    public SubmittableWorkResponse delete(Integer id) {
        return submittableWorkRepository.delete(id);
    }

    @Override
    public List<UpComingSubmittableWorkResponse> getUpComingSubmittableWorkByStudentId(Integer studentId, Integer classId, Integer classRoomId) {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.of("+07:00")));
        System.out.println(timestamp);
        return submittableWorkRepository.getUpComingSubmittableWorkByStudentId(studentId, classId, classRoomId, timestamp);
    }

    @Override
    public List<SubmittableWorkByClassResponse> getByClassIdAndClassId(Integer classroom_id, Integer class_id) {
        return submittableWorkRepository.getByClassIdAndClassId(classroom_id,class_id);
    }

    @Override
    public SubmittableWorkUpdateClassClassroomRequest updateClassClassroom(SubmittableWorkUpdateClassClassroomRequest submittableWorkUpdateClassClassroomRequest) {
        return submittableWorkRepository.updateClassClassroom(submittableWorkUpdateClassClassroomRequest);
    }

    @Override
    public List<SubmittableWorkByTeacherResponse> getByTeacherUserId(Integer user_id) {
        return submittableWorkRepository.getByTeacherUserId(user_id);
    }

    @Override
    public List<SubmittableWorkByMaterialResponse> getByClassMaterialId(Integer material_id) {
        return submittableWorkRepository.getByClassMaterialId(material_id);
    }

    @Override
    public List<SubmittableWorkByClassIdTeacherIdResponse> getAllByClassIdTeacherUserId(Integer user_id, Integer class_id) {
        return submittableWorkRepository.getAllByClassIdTeacherUserId(user_id,class_id);
    }

    @Override
    public boolean createClassworks(ClassMaterialRequest classMaterialRequest, Integer user_id, Integer typeId) {
        return submittableWorkRepository.createQuiz(classMaterialRequest,user_id,typeId);
    }

    @Override
    public List<ClassWorkByFolderIDClassIDResponse> getByFolderIdClassId(Integer classroom_id, Integer class_id, Integer folderId) {
        return submittableWorkRepository.getByFolderIdClassId(class_id,classroom_id,folderId);
    }

    @Override
    public List<ClassWorkByFolderIDTeacherIDResponse> getByFolderIdTeacherId(Integer user_id, Integer folderId) {
        return submittableWorkRepository.getByFolderIdTeacherId(user_id,folderId);
    }
}
