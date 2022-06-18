package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.submittableWork.request.SubmittableWorkRequest;
import com.kshrd.tnakrean.model.submittableWork.request.SubmittableWorkUpdateRequest;
import com.kshrd.tnakrean.model.submittableWork.response.SubmittableWorkResponse;

import java.util.List;

public interface SubmittableService {
    void delete(int id);

    List<SubmittableWorkResponse> getAll();

    SubmittableWorkResponse getById(int id);

    boolean insertSubmittableWork(SubmittableWorkRequest submittableWorkRequest);

    boolean updateSubmittableWork(SubmittableWorkUpdateRequest submittableWorkUpdateRequest);

    List<SubmittableWorkResponse> getSubmittableWorkByClassMaterialDetailType(Integer id);
}
