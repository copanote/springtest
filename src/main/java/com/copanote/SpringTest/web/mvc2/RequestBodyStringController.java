package com.copanote.SpringTest.web.mvc2;


import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    /*
      HTTP Body를 통해 데이터가 직접 넘어오는 경우는 @RequestParam, @ModelAttribute를 사용할 수 없다.
      (물론 HTML Form형식으로 전달되는 경우는 요청 파라미터로 인정된다.)
     */


    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ServletInputStream inputStream = req.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("body={}", messageBody);
        resp.getWriter().write("ok");
    }

    /**
     * InputStream(Reader)
     * OutputStream(Writer)
     *
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyString(InputStream in, Writer writer) throws IOException {

        String messageBody = StreamUtils.copyToString(in, StandardCharsets.UTF_8);

        log.info("body={}", messageBody);
        writer.write("ok");
    }


    /**
     *
     * HttpEntity: HTTP header, body 정보를 편리하게 조회
     *  - 메시지 바디 정보를 직접 조회
     *  - HttpMessageConverter 사용 -> StringHttpMessageConverter적용
     *
     * 응답에서도 HttpEntity 사용 가능
     *  - 메시지 바디 정보 직접 반환
     *  - 헤더 정보 포함 가능
     *  - view조회 x
     *  - HttpMessageConverter 사용 -> StringHttpMessageConverter적용
     *
     */

    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyString(HttpEntity<String> httpEntity) {
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);

        return new HttpEntity<>("ok");
    }


    /**
     * @RequestBody를 사용하면 HTTP 메시지 바디 정보를 편리하게 조회할 수 있다. 참고로 헤더 정보가 필요하다면
     * HttpEntity를 사용하거나 @RequestHeader를 사용하면 된다.
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyString(@RequestBody String messageBody) {
        log.info("messageBody={}", messageBody);

        return "ok";
    }


}
