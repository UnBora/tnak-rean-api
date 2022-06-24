package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.classmaterials.request.SubmittedWorkStudentScoreRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittedWorkStudentWorkRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittedWorkUpdateStatusRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittedWorkUpdateStudentWorkRequest;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittedWorkByStudentIdAndClassIdResponse;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittedWorkResponse;

import java.util.List;

public interface SubmittedWorkService {
   List<SubmittedWorkResponse> getAllSubmittedWork();

   List<SubmittedWorkResponse> getSubmittedByStudentId(int studentId);

   boolean addSubmittedWork(SubmittedWorkStudentWorkRequest submittedWorkStudentWorkRequest);

   boolean updateSubmittedWork(SubmittedWorkUpdateStudentWorkRequest submittedWorkUpdateStudentWorkRequest);

   void deleteSubmittedWorkId(int id);

   void deleteByStudentId(Integer id);

   List<SubmittedWorkResponse> getById(Integer id);

   List<SubmittedWorkByStudentIdAndClassIdResponse> getByStudentIdAndClassId(Integer student_id, Integer class_id);

   Boolean insertScore(SubmittedWorkStudentScoreRequest submittedWorkStudentScoreRequest);
}
