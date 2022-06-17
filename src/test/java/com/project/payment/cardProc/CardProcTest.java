package com.project.payment.cardProc;

import com.project.payment.cardProc.code.CardInfoRequest;
import com.project.payment.cardProc.code.CardInfoResponse;
import com.project.payment.cardProc.code.CardProc;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Slf4j
@DisplayName("카드 인증 테스트")
public class CardProcTest {
    private CardInfoRequest saved = new CardInfoRequest("1234", "1111-2222-3333-4444", "2022-12");

    @Test
    @DisplayName("정확한 정보를 입력했을 경우")
    void success() {
        CardInfoRequest userRequest = new CardInfoRequest("1234", "1111-2222-3333-4444", "2022-12");
        CardProc cardProc = new CardProc(saved, userRequest);
        CardInfoResponse result = cardProc.authCardInfo();
        log.info("카드 인증 결과 = {}", result.toString());
    }
    
    @Nested
    @DisplayName("잘못된 정보를 입력한 경우")
    class FailTest {

        @Test
        @DisplayName("비밀번호를 잘못 입력한 경우")
        void fail_password() {
            CardInfoRequest userRequest = new CardInfoRequest("2324", "1111-2222-3333-4444", "2022-12");
            CardProc cardProc = new CardProc(saved, userRequest);
            CardInfoResponse result = cardProc.authCardInfo();
            log.info("카드 인증 결과 = {}", result.toString());
        }

        @Test
        @DisplayName("유효기간을 잘못 입력한 경우")
        void fail_period1() {
            CardInfoRequest userRequest = new CardInfoRequest("1234", "1111-2222-3333-4444", "2023-12");
            CardProc cardProc = new CardProc(saved, userRequest);
            CardInfoResponse result = cardProc.authCardInfo();
            log.info("카드 인증 결과 = {}", result.toString());
        }

        @Test
        @DisplayName("유효기간이 이미 지난 경우")
        void fail_period2() {
            CardInfoRequest saved2 = new CardInfoRequest("1234", "1111-2222-3333-4444", "2021-12");
            CardInfoRequest userRequest = new CardInfoRequest("1234", "1111-2222-3333-4444", "2021-12");
            CardProc cardProc = new CardProc(saved2, userRequest);
            CardInfoResponse result = cardProc.authCardInfo();
            log.info("카드 인증 결과 = {}", result.toString());
        }

        @Test
        @DisplayName("카드번호를 잘못 입력한 경우")
        void fail_cardNum() {
            CardInfoRequest userRequest = new CardInfoRequest("1234", "1111-2122-3333-4444", "2022-12");
            CardProc cardProc = new CardProc(saved, userRequest);
            CardInfoResponse result = cardProc.authCardInfo();
            log.info("카드 인증 결과 = {}", result.toString());
        }
    }
}

