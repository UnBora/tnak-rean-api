package com.kshrd.tnakrean.service.serviceImplementation;


import com.kshrd.tnakrean.model.apiresponse.ApiResponse;
import com.kshrd.tnakrean.model.apiresponse.BaseMessage;
import com.kshrd.tnakrean.model.submittableWork.request.SubmittableWorkRequest;
import com.kshrd.tnakrean.model.submittableWork.request.SubmittableWorkUpdateRequest;
import com.kshrd.tnakrean.model.submittableWork.response.SubmittableWorkResponse;
import com.kshrd.tnakrean.repository.SubmittableWorkRepository;
import com.kshrd.tnakrean.service.serviceInter.SubmittableService;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Base64;
import java.util.List;

@Service
public class SubmittableWorkServiceImpl implements SubmittableService {
    final SubmittableWorkRepository submittableWorkRepository;

    public SubmittableWorkServiceImpl(SubmittableWorkRepository submittableWorkRepository) {
        this.submittableWorkRepository = submittableWorkRepository;
    }

    public List<SubmittableWorkResponse> getAll() {
        return submittableWorkRepository.getAll();
    }

    public SubmittableWorkResponse getById(int id) {
        return submittableWorkRepository.getById(id);
    }

    public boolean insertSubmittableWork(SubmittableWorkRequest submittableWorkRequest) {
        return submittableWorkRepository.insertSubmittableWork(submittableWorkRequest);
    }

    public boolean updateSubmittableWork(SubmittableWorkUpdateRequest submittableWorkUpdateRequest) {
        return submittableWorkRepository.updateSubmittableWork(submittableWorkUpdateRequest);
    }

    public void delete(int id) {
        submittableWorkRepository.delete(id);
    }
}
