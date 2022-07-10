package com.kshrd.tnakrean.model.mails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {
    private String receiver;
    private String msgBody;
    private String subject;
    private String attachment;
    @JsonIgnore
    private String sendTo;
}
