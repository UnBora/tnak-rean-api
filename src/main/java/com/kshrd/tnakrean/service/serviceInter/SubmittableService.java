package com.kshrd.tnakrean.service.serviceInter;

import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkUpdateClassClassroomRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkUpdateDeadlineRequest;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittableWorkResponse;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittedWorkResponse;
import com.kshrd.tnakrean.model.classmaterials.response.UpComingSubmittableWorkResponse;

import java.util.List;
public interface SubmittableService {

    SubmittableWorkResponse delete(Integer id);

    List<SubmittableWorkResponse> getAll();

    SubmittableWorkResponse getById(int id);

    boolean insertSubmittableWork(SubmittableWorkRequest submittableWorkRequest);

    SubmittedWorkResponse updateSubmittableWork(SubmittableWorkUpdateDeadlineRequest submittableWorkUpdateDeadlineRequest);

    List<SubmittableWorkResponse> getSubmittableWorkByClassMaterialDetailType(Integer id);

    List<UpComingSubmittableWorkResponse> getUpComingSubmittableWorkByStudentId(Integer studentId, Integer classRoomId, Integer classId);

    List<SubmittableWorkResponse> getByClassIdAndClassId(Integer classroom_id, Integer class_id);

    SubmittableWorkUpdateClassClassroomRequest updateClassClassroom(SubmittableWorkUpdateClassClassroomRequest submittableWorkUpdateClassClassroomRequest);
}
