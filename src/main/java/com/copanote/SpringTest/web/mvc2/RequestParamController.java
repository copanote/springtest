package com.copanote.SpringTest.web.mvc2;


import com.copanote.SpringTest.web.argumentresolver.Login;
import com.copanote.SpringTest.web.argumentresolver.LoginDto;
import com.copanote.SpringTest.web.mvc2.dto.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {


    /**
     *  반환 타입이 없으면서 이렇게 응답에 값을 직접 집어넣으면 ,view 조회 x
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        response.getWriter().write("ok");
    }


    /**
     * @ReqeustParam 사용
     *  - 파라미터 이름으로 바인딩
     *  - HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name=xxx) 생략 가능
     * @ResponseBody 추가
     *  - View 조회를 무시하고, Http message body에 직접 해당 내용 입력
     */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam int age
    ) {
        log.info("memberName={}, age={}", memberName, age);
        return "ok";
    }


    @ResponseBody
    @RequestMapping("/request-param-required-default")
    public String requestParamRequiredAndDefault(
            @RequestParam(name = "username", required = true, defaultValue = "guest") String username,
            @RequestParam(name = "age", required = false) int age
    ) {
        log.info("memberName={}, age={}", username, age);
        return "ok";
    }


    /**
     * 파라미터를 Map, MultivalueMap으로 조회할 수 있다.
     * 파라미터의 값이 1개가 확실하다면 Map을 사용해도 되지만 그렇지 않다면 MultivalueMap을 사용하자.
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("paramMap={}", paramMap);
        return "ok";
    }


    /**
     *
     * 스프링MVC는 @ModelAttribute가 있으면 다음을 실행한다.
     *   - helloData 객체를 생성한다.
     *   - 요청파라미터의 이름으로 HelloData 객체의 프로퍼티를 찾는다.
     *   그리고 해당 프로퍼티의 setter를 호출하여 값 바인딩
     *
     * @ModelAttribute 생략할 수 있다.
     *  - String, int, Integer같은 단순 타입 - @RequestParam
     *  - 나머지 = @ModelAttribute
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("helloData={}", helloData);
        return "ok";
    }

    @GetMapping("/logindto")
    @ResponseBody
    public String homeLoginV3ArgumentResolver(@Login LoginDto loginDto) {
        return loginDto.toString();
    }


}
