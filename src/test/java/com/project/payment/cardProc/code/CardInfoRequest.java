package com.project.payment.cardProc.code;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardInfoRequest {
    private String password;
    private String cardNumber;
    private String validPeriod;
}
