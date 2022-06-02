package com.kshrd.tnakrean.model.classmaterials.response;

import com.kshrd.tnakrean.model.NotificationTypes;
import com.kshrd.tnakrean.model.classmaterials.json.Content;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    NotificationTypes notificationTypes;
    Integer receiverId;
    Content content;
    Timestamp timestamp;
}
