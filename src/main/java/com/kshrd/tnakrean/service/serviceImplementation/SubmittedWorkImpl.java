package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.classmaterials.request.*;
import com.kshrd.tnakrean.model.classmaterials.response.*;
import com.kshrd.tnakrean.repository.SubmittedWorkRepository;
import com.kshrd.tnakrean.service.serviceInter.SubmittedWorkService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmittedWorkImpl implements SubmittedWorkService {
    final SubmittedWorkRepository submittedWorkRepository;

    public SubmittedWorkImpl(SubmittedWorkRepository submittedWorkRepository) {
        this.submittedWorkRepository = submittedWorkRepository;
    }

    @Override
    public List<SubmittedWorkResponse> getAllSubmittedWork() {
        return submittedWorkRepository.getAllSubmittedWork();
    }

    @Override
    public List<SubmittedWorkResponse> getSubmittedByStudentId(Integer material_id, Integer user_id) {
        return submittedWorkRepository.getSubmittedByStudentId(material_id,user_id);
    }

    @Override
    public boolean addSubmittedWork(SubmittedWorkStudentWorkRequest submittedWorkStudentWorkRequest, Integer userId) {
        return submittedWorkRepository.addSubmittedWork(submittedWorkStudentWorkRequest,userId);
    }

    public SubmittedWorkUpdateStudentWorkRequest updateSubmittedWork(SubmittedWorkUpdateStudentWorkRequest submittedWorkUpdateStudentWorkRequest) {
        return submittedWorkRepository.updateSubmittedWork(submittedWorkUpdateStudentWorkRequest);
    }

    @Override
    public SubmittedWorkResponse deleteSubmittedWorkId(int id) {
      return submittedWorkRepository.deleteSubmittedWorkId(id);
    }

    @Override
    public SubmittedWorkResponse getById(Integer id) {
        return submittedWorkRepository.getById(id);
    }

    @Override
    public SubmittedWorkStudentScoreRequest insertScore(SubmittedWorkStudentScoreRequest submittedWorkStudentScoreRequest) {
        return submittedWorkRepository.insertScore(submittedWorkStudentScoreRequest);
    }

    @Override
    public List<SubmittedWorkByMaterialIdResponse> getByClassMaterialId(Integer class_material_id) {
        return submittedWorkRepository.getByClassMaterialId(class_material_id);
    }

    @Override
    public List<SubmittedWorkByClassroomClassSubmittableResponse> getByClassroomClassSubmittable(Integer classroom_id, Integer class_id, Integer submittable_work_id) {
        return submittedWorkRepository.getByClassroomClassSubmittable(classroom_id,class_id,submittable_work_id);
    }

    @Override
    public List<SubmittedWorkResultByClassResponse> getByClassId(Integer class_id, Integer material_id) {
        return submittedWorkRepository.getResultByClassId(class_id,material_id);
    }

    @Override
    public List<SubmittedWorkNotGradedByClassResponse> getNotGradedByClassId(Integer class_id, Integer material_id,Integer userId) {
        return submittedWorkRepository.getNotGradedByClassId(class_id,material_id,userId);
    }

    @Override
    public List<StudentWorkBySubmittedWorkIdResponse> viewStudentWork(Integer submitted_work_id) {
        return submittedWorkRepository.viewStudentWork(submitted_work_id);
    }

    @Override
    public List<SubmittedWorkResultByStudentIdResponse> getResultByStudentIdMaterialId(Integer material_id, Integer user_id) {
        return submittedWorkRepository.getResultByStudentIdMaterialId(material_id,user_id);
    }
}