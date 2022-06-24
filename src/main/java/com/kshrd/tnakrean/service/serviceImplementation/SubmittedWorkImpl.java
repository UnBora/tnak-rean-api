package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.classmaterials.request.*;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittedWorkByMaterialIdResponse;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittedWorkByStudentIdAndClassIdResponse;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittedWorkResponse;
import com.kshrd.tnakrean.repository.SubmittedWorkRepository;
import com.kshrd.tnakrean.service.serviceInter.SubmittedWorkService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public List<SubmittedWorkResponse> getSubmittedByStudentId(int studentId) {
        return submittedWorkRepository.getSubmittedByStudentId(studentId);
    }

    @Override
    public boolean addSubmittedWork(SubmittedWorkStudentWorkRequest submittedWorkStudentWorkRequest) {
        submittedWorkStudentWorkRequest.setSubmitted_date(LocalDateTime.now());
        return submittedWorkRepository.addSubmittedWork(submittedWorkStudentWorkRequest);
    }

    public boolean updateSubmittedWork(SubmittedWorkUpdateStudentWorkRequest submittedWorkUpdateStudentWorkRequest) {
        return submittedWorkRepository.updateSubmittedWork(submittedWorkUpdateStudentWorkRequest);
    }

    @Override
    public Boolean deleteSubmittedWorkId(int id) {
      return submittedWorkRepository.deleteSubmittedWorkId(id);
    }

    @Override
    public List<SubmittedWorkResponse> getById(Integer id) {
        return submittedWorkRepository.getById(id);
    }

    @Override
    public List<SubmittedWorkByStudentIdAndClassIdResponse> getByStudentIdAndClassId(Integer student_id, Integer class_id) {
        return submittedWorkRepository.getByStudentIdAndClassId(student_id,class_id);
    }

    @Override
    public Boolean insertScore(SubmittedWorkStudentScoreRequest submittedWorkStudentScoreRequest) {
        return submittedWorkRepository.insertScore(submittedWorkStudentScoreRequest);
    }

    @Override
    public List<SubmittedWorkByMaterialIdResponse> getByClassMaterialId(Integer class_material_id) {
        return submittedWorkRepository.getByClassMaterialId(class_material_id);
    }
}