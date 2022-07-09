package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.classmaterials.request.SubmittedWorkStudentScoreRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittedWorkStudentWorkRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittedWorkUpdateStudentWorkRequest;
import com.kshrd.tnakrean.model.classmaterials.response.*;

import java.util.List;

public interface SubmittedWorkService {
   List<SubmittedWorkResponse> getAllSubmittedWork();

   List<SubmittedWorkResponse> getSubmittedByStudentId(int studentId);

   boolean addSubmittedWork(SubmittedWorkStudentWorkRequest submittedWorkStudentWorkRequest, Integer userId);

   SubmittedWorkUpdateStudentWorkRequest updateSubmittedWork(SubmittedWorkUpdateStudentWorkRequest submittedWorkUpdateStudentWorkRequest);

   SubmittedWorkResponse deleteSubmittedWorkId(int id);

   SubmittedWorkResponse getById(Integer id);

   SubmittedWorkStudentScoreRequest insertScore(SubmittedWorkStudentScoreRequest submittedWorkStudentScoreRequest);

   List<SubmittedWorkByMaterialIdResponse> getByClassMaterialId(Integer class_material_id);

   List<SubmittedWorkByClassroomClassSubmittableResponse> getByClassroomClassSubmittable(Integer classroom_id, Integer class_id, Integer submittable_work_id);

   List<SubmittedWorkResultByClassResponse> getByClassId(Integer class_id, Integer material_id);

   List<SubmittedWorkNotGradedByClassResponse> getNotGradedByClassId(Integer class_id, Integer material_id,Integer userId);

   List<StudentWorkBySubmittedWorkIdResponse> viewStudentWork(Integer submitted_work_id);
}
