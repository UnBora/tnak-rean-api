package com.kshrd.tnakrean.model.classmaterials.response;

import com.kshrd.tnakrean.model.NotificationTypes;
import com.kshrd.tnakrean.model.classmaterials.json.Content;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private NotificationTypes notificationTypes;
    private Integer receiverId;
    private Content content;
    private Date timestamp;
}
