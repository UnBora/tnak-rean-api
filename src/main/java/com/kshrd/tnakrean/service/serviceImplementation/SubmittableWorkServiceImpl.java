package com.kshrd.tnakrean.service.serviceImplementation;


import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkUpdateRequest;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittableWorkResponse;
import com.kshrd.tnakrean.repository.SubmittableWorkRepository;
import com.kshrd.tnakrean.service.serviceInter.SubmittableService;
import org.springframework.stereotype.Service;

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
