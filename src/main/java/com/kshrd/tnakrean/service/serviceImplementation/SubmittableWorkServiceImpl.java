package com.kshrd.tnakrean.service.serviceImplementation;


import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkUpdateClassClassroomRequest;
import com.kshrd.tnakrean.model.classmaterials.request.SubmittableWorkUpdateDeadlineRequest;
import com.kshrd.tnakrean.model.classmaterials.response.SubmittableWorkResponse;
import com.kshrd.tnakrean.model.classmaterials.response.UpComingSubmittableWorkResponse;
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

    @Override
    public List<SubmittableWorkResponse> getAll() {
        return submittableWorkRepository.getAll();
    }

    @Override
    public SubmittableWorkResponse getById(int id) {
        return submittableWorkRepository.getById(id);
    }

    @Override
    public boolean insertSubmittableWork(SubmittableWorkRequest submittableWorkRequest) {
        return submittableWorkRepository.insertSubmittableWork(submittableWorkRequest);
    }

    @Override
    public boolean updateSubmittableWork(SubmittableWorkUpdateDeadlineRequest submittableWorkUpdateDeadlineRequest) {
        return submittableWorkRepository.updateSubmittableWork(submittableWorkUpdateDeadlineRequest);
    }

    @Override
    public Boolean delete(int id) {
        return submittableWorkRepository.delete(id);
    }

    @Override
    public List<SubmittableWorkResponse> getSubmittableWorkByClassMaterialDetailType(Integer id) {
        return submittableWorkRepository.getSubmittableWorkByClassMaterialDetailType(id);
    }

    @Override
    public List<UpComingSubmittableWorkResponse> getUpComingSubmittableWorkByStudentId(Integer studentId, Integer classRoomId, Integer classId) {
        return submittableWorkRepository.getUpComingSubmittableWorkByStudentId(studentId, classRoomId, classId);
    }

    @Override
    public List<SubmittableWorkResponse> getByClassIdAndClassId(Integer classroom_id, Integer class_id) {
        return submittableWorkRepository.getByClassIdAndClassId(classroom_id,class_id);
    }

    @Override
    public Boolean updateClassClassroom(SubmittableWorkUpdateClassClassroomRequest submittableWorkUpdateClassClassroomRequest) {
        return submittableWorkRepository.updateClassClassroom(submittableWorkUpdateClassClassroomRequest);
    }
}
