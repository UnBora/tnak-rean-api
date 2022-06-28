package com.kshrd.tnakrean.model.classmaterials.request;

import com.kshrd.tnakrean.model.classmaterials.response.ClassroomResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetClassroomByIdRequest {
    @Min(value = 1, message = "{validation.id.notNegative}")
    Integer classroomId;
}
