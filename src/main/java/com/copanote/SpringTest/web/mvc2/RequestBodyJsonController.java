package com.copanote.SpringTest.web.mvc2;

import com.copanote.SpringTest.web.mvc2.dto.HelloData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();


    @ResponseBody
    @PostMapping("/request-body-json-v1")
    public String requestBodyJsonV1(@RequestBody String messageBody) throws JsonProcessingException {
        HelloData data = objectMapper.readValue(messageBody, HelloData.class);
        log.info("HelloData={}", data);
        return "ok";
    }


    /**
     *
     * HttpEntity, @RequestBody를 사용하면 HTTP메시지 컨버터가 HTTP메시지 바디의 내용을 우리가 원하는 문자나 객체로 변환해준다.
     *
     * @RequestBody는 생략 불가능.  생략한다면 , @ModelAttribute가 적용되어버린다.
     *
     */
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV1(@RequestBody HelloData data) {
        log.info("HelloData={}", data);
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV1(HttpEntity<HelloData> httpEntity) {
        HelloData data = httpEntity.getBody();
        log.info("HelloData={}", data);
        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public HelloData requestBodyJsonV4(@RequestBody HelloData data) {
        log.info("HelloData={}", data);
        return data;  //HTTP메시지 컨버터 --> JSON 응답
    }


}
