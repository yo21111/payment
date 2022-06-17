package com.project.payment.cardProc.code;

import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

public class CardProc {
    private final CardInfoRequest cardInfo;
    private final CardInfoRequest userRequest;

    public CardProc(CardInfoRequest cardInfo, CardInfoRequest userRequest) {
        this.cardInfo = cardInfo;
        this.userRequest = userRequest;
    }

    public CardInfoResponse authCardInfo() {
        CardInfoResponse cardInfoResponse = new CardInfoResponse();

        if(!userRequest.getCardNumber().equals(cardInfo.getCardNumber())) {
            // 카드번호 잘못 입력한 경우
            auth_Fail(cardInfoResponse, "없는 카드 번호입니다.");
        } else if (!userRequest.getValidPeriod().equals(cardInfo.getValidPeriod())) {
            // 유효기간 미일치한 경우
            auth_Fail(cardInfoResponse, "잘못된 유효기간입니다.");
        } else if (!periodValidation(userRequest.getValidPeriod())) {
            // 유효기간이 오늘날짜 이전일 경우
            auth_Fail(cardInfoResponse, "유효기간 오류.");
        } else if (!userRequest.getPassword().equals(cardInfo.getPassword())) {
            // 비밀번호를 잘못 입력한 경우
            auth_Fail(cardInfoResponse, "잘못된 비밀번호입니다.");
        } else {
            // 인증 성공 키 발급
            cardInfoResponse.setAuthKey(UUID.randomUUID().toString());
        }
        return cardInfoResponse;
    }

    private boolean periodValidation(String validPeriod) {
        LocalDate now = LocalDate.now();
        String[] period = validPeriod.split("-");
        LocalDate input = LocalDate.of(Integer.parseInt(period[0]), Integer.parseInt(period[1]), LocalDate.MAX.getDayOfMonth());
        return input.isAfter(now);
    }

    private void auth_Fail(CardInfoResponse cardInfoResponse, String message) {
        cardInfoResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        cardInfoResponse.setException(new IllegalArgumentException(message));
    }
}
