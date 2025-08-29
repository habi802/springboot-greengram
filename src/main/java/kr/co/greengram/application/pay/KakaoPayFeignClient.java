package kr.co.greengram.application.pay;

import kr.co.greengram.application.pay.model.KakaoPayApproveFeignReq;
import kr.co.greengram.application.pay.model.KakaoPayApproveRes;
import kr.co.greengram.application.pay.model.KakaoPayReadyFeignReq;
import kr.co.greengram.application.pay.model.KakaoPayReadyRes;
import kr.co.greengram.config.feignclient.KakaoPayClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "kakaoPayApi"
        , url = "${constants.pay.kakao.base-url}"
        , configuration = { KakaoPayClientConfiguration.class })
public interface KakaoPayFeignClient {
    @PostMapping(value = "/ready")
    KakaoPayReadyRes postReady(@RequestBody KakaoPayReadyFeignReq req);

    @PostMapping(value = "/approve")
    KakaoPayApproveRes postApprove(KakaoPayApproveFeignReq req);
}
