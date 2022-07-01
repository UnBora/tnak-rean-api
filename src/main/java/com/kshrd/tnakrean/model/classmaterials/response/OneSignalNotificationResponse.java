package com.kshrd.tnakrean.model.classmaterials.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneSignalNotificationResponse {
    @NotBlank(message = "{validation.noti.message}")
    private String message;
    private Integer classId;
    private Integer classRoomId;
}
