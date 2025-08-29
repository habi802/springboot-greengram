package kr.co.greengram.config.feignclient;

import feign.RequestInterceptor;
import kr.co.greengram.config.constants.ConstKakaoPay;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KakaoPayClientConfiguration {
    private final ConstKakaoPay constKakaoPay;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header("Content-Type", "application/json")
                .header(constKakaoPay.authorizationName, String.format("SECRET_KEY %s", constKakaoPay.secretKey));
    }
}
