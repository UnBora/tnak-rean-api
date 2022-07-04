package com.kshrd.tnakrean.model.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentInsertRequest {
        @Min(value = 1, message = "{validation.userId.notEmpty}")
        Integer user_id;
        @Min(value = 1, message = "{validation.classroomId.notEmpty}")
        Integer classroom_id;
        @Min(value = 1, message = "{validation.classId.notEmpty}")
        Integer class_id;
}
