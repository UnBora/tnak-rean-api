package com.kshrd.tnakrean.model.classmaterials.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetClassByClassroomIDResponse {
    Integer id;
    String className;
    Integer allStudent;
    String img;
}
