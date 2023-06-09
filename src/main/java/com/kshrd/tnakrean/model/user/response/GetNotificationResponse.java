package com.kshrd.tnakrean.model.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetNotificationResponse {
    Integer received_id;
    String received_name;
    String received_img;
    Integer sender_id;
    String sender_name;
    String sender_img;
    Integer received_class_id;
    String class_name;
    String type;
    String title;
    String action_on;
    String received_date;
}
