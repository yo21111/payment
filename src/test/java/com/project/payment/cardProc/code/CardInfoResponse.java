package com.project.payment.cardProc.code;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CardInfoResponse {
    private String authKey;
    private HttpStatus httpStatus;
    private Exception exception;
}
