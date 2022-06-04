package com.kshrd.tnakrean.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneSignalPushNotification {

    private Long id;

    private String userName;

    private String idOneSignal;


}
