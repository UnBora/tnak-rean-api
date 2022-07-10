package com.kshrd.tnakrean.model.mails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleEmail {
    private String receiver;
    private String msgBody;
    private String subject;
    @JsonIgnore
    private String sendTo;
}