package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.submittedWork.request.SubmittedWorkStudentWorkRequest;
import com.kshrd.tnakrean.model.submittedWork.request.SubmittedWorkUpdateResultRequest;
import com.kshrd.tnakrean.model.submittedWork.request.SubmittedWorkUpdateStudentWorkRequest;
import com.kshrd.tnakrean.model.submittedWork.response.SubmittedWorkResponse;

import java.util.List;

public interface SubmittedWorkService {
   List<SubmittedWorkResponse> getAllSubmittedWork();

   List<SubmittedWorkResponse> getSubmittedByStudentId(int studentId);

   boolean addSubmittedWork(SubmittedWorkStudentWorkRequest submittedWorkStudentWorkRequest);

   boolean updateSubmittedWork(SubmittedWorkUpdateStudentWorkRequest submittedWorkUpdateStudentWorkRequest);

   void deleteSubmittedWorkId(int id);

   boolean updateResult(SubmittedWorkUpdateResultRequest submittedWorkUpdateResultRequest);
}
