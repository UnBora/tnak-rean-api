package com.kshrd.tnakrean.service.serviceImplementation;

import com.kshrd.tnakrean.model.classmaterials.request.*;
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
    public void deleteSubmittedWorkId(int id) {
        submittedWorkRepository.deleteSubmittedWorkId(id);
    }

    @Override
    public boolean updateResult(SubmittedWorkUpdateResultRequest submittedWorkUpdateResultRequest) {
        return submittedWorkRepository.updateResult(submittedWorkUpdateResultRequest);
    }

    @Override
    public void deleteByStudentId(Integer id) {
        submittedWorkRepository.deleteByStudentId(id);
    }

    @Override
    public boolean updateStatus(SubmittedWorkUpdateStatusRequest submittedWorkUpdateStatusRequest) {
        return submittedWorkRepository.updateStatus(submittedWorkUpdateStatusRequest);
    }
}