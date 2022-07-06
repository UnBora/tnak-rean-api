package com.kshrd.tnakrean.model.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetNotificationResponse {
    Integer received_id;
    Integer sender_id;
    Integer received_class_id;
    String type;
    String title;
    String action_on;
    String received_date;
}
