package com.kshrd.tnakrean.model.classmaterials.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneSignalNotificationResponse {
    private Integer id;
    private String message;
    private Integer classId;
}
