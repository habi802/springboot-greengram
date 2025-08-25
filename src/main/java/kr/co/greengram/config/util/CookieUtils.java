package kr.co.greengram.config.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

// 쿠키에 데이터 담고 빼고 할 때 사용하는 객체
@Slf4j
@Component
public class CookieUtils {
    /*
    response: 쿠키를 담을 때 필요함
    name: 쿠키에 담을 value의 레이블(키값)
    value: 쿠키에 담을 value
    maxAge: 쿠키에 담긴 value의 유효 기간
    path: 설정한 경로에 요청이 갈 때만 쿠키가 전달됨
     */
//    public void setCookie(HttpServletResponse response, String name, String value, int maxAge, String path) {
//        Cookie cookie = new Cookie(name, value);
//        cookie.setPath(path);
//        cookie.setMaxAge(maxAge);
//        cookie.setHttpOnly(true); // 보안 쿠키 설정
//        response.addCookie(cookie);
//    }

    //Res header에 내가 원하는 쿠키를 담는 메소드
    public void setCookie(HttpServletResponse res, String name, String value, int maxAge, String path) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path); //이 요청으로 들어올 때만 쿠키값이 넘어올 수 있도록
        cookie.setHttpOnly(true); //보안 쿠키 설정, 프론트에서 JS로 쿠키값을 얻을 수 없다.
        cookie.setMaxAge(maxAge);
        res.addCookie(cookie);
    }

    public void setCookie(HttpServletResponse res, String name, Object value, int maxAge, String path) {
        this.setCookie(res, name, serializeObject(value), maxAge, path);
    }

    public <T> T getValue(HttpServletRequest req, String name, Class<T> valueType) {
        Cookie cookie = getCookie(req, name);
        if (cookie == null) { return null; }
        if(valueType == String.class) {
            return (T) cookie.getValue();
        }
        return deserializeCookie(cookie, valueType);
    }

    private String serializeObject(Object obj) {
        return Base64.getUrlEncoder().encodeToString( SerializationUtils.serialize(obj) );
    }

    //역직렬화, 문자열값을 객체로 변환
    private <T> T deserializeCookie(Cookie cookie, Class<T> valueType) {
        return valueType.cast(
                SerializationUtils.deserialize( Base64.getUrlDecoder().decode(cookie.getValue()) )
        );
    }

    private Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies(); // 쿠키가 req에 여러 개가 있을 수 있기 때문에 배열로 리턴

        if (cookies != null && cookies.length > 0) {
            // 쿠키에 뭔가 담겨져 있다면...
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    // 쿠키에 담긴 이름이 같은 게 있다면
                    // 해당 쿠키를 리턴
                    return cookie;
                }
            }
        }

        return null;
    }

    public void deleteCookie(HttpServletResponse response, String name, String path) {
        setCookie(response, name, null, 0, path);
    }
}