package com.copanote.SpringTest.web.mvc2;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@Slf4j
/*
   @Controller는 반환 값이 String이면 뷰 이름으로 인식된다. 그래서 뷰를 찾고 뷰가 렌더링된다.
   @RestController는 반환 값으로 뷰를 찾는 것이 아니라, HTTP메시지 바디에 바로 입력한다.
 */
@RestController
public class MappingController {

    /**
     * 기본요청
     * 둘다 허용 /hello-basic, /hello-basic/
     * HTTP METHOD 모두 허용 GET, HEAD, POST, PUT, PATCH, DELETE
     */
    @RequestMapping("/hello-basic")
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }

    /**
     * method 특정 HTTP 메서드 요청만 허용
     * GET, HEAD, POST, PUT, PATCH, DELETE
     */
    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappongGetV1() {
        log.info("mappingGetV1");
        return "ok";
    }

    /**
     * Http Method 매핑 축약
     *  @GetMapping
     *  @PostMapping
     *  @PutMapping
     *  @DeleteMapping
     *  @PatchMapping
     */
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mappingGetV2");
        return "OK";
    }

    /**
     * PathVariable 사용
     * 변수명이 같으면 ("userId") 생략가능
     */
    @GetMapping("/mapping/users/{userId}")
    public String mappingPath(@PathVariable("userId") String userId) {
        log.info("mappingPath userId={}", userId);
        return "ok";
    }

    /**
     * PathVariable 사용 다중
     */
    @GetMapping("/mapping/users/{userId}/oders/{oderId}")
    public String mappingPath(@PathVariable("userId") String userId, @PathVariable("orderId") int orderId) {
        log.info("mappingPath userId={}, oderId={}", userId, orderId);
        return "ok";
    }

    /**
     * 특정파라미터로 핸들러메서드 매핑
     */
    // 1. 파라미터가 없는 요청을 처리 (전체 사용자 목록 조회)
    // 요청 URL: GET /users
    @GetMapping(params = "mode!=details")
    @ResponseBody
    public String listAllUsers() {
        return "전체 사용자 목록을 반환합니다.";
    }

    // 2. 'mode=details' 파라미터가 있는 요청을 처리 (사용자 상세 정보 조회)
    // 요청 URL: GET /users?mode=details&id=123
    @GetMapping(params = "mode=details")
    @ResponseBody
    public String getUserDetails(@RequestParam("id") Long userId) {
        return "사용자 상세 정보 조회 (ID: " + userId + ")";
    }

    // 3. 'role=admin' 파라미터가 있는 요청을 처리 (관리자 목록 조회)
    // 요청 URL: GET /users?role=admin
    @GetMapping(params = "role=admin")
    @ResponseBody
    public String listAdminUsers() {
        return "관리자 사용자 목록을 반환합니다.";
    }


    /**
     *  특정헤더로 핸들러메서드 매핑
     */
    // 1. "X-API-Version: 2" 헤더가 있는 요청을 처리 (API v2)
    // 요청: GET /items (Header: X-API-Version: 2)
    @GetMapping(headers = "X-API-Version=2")
    @ResponseBody
    public String getItemsV2() {
        return "API 버전 2의 아이템 목록을 반환합니다.";
    }

    // 2. "X-API-Version" 헤더가 없는 요청을 처리 (기본 버전)
    // 요청: GET /items (Header: 없음)
    @GetMapping(headers = "X-API-Version!=2")
    @ResponseBody
    public String getItemsDefault() {
        return "기본 버전의 아이템 목록을 반환합니다.";
    }

    // 3. Accept 헤더에 따라 다른 응답을 생성
    // 요청: GET /items/accept (Header: Accept: application/json)
    @GetMapping(value = "/accept", headers = "Accept=application/json")
    @ResponseBody
    public String getItemsAsJson() {
        return "{ \"message\": \"JSON 형식의 응답입니다.\" }";
    }

    // 요청: GET /items/accept (Header: Accept: application/xml)
    @GetMapping(value = "/accept", headers = "Accept=application/xml")
    @ResponseBody
    public String getItemsAsXml() {
        return "<message>XML 형식의 응답입니다.</message>";
    }

    /**
     * 헤더 -> 미디어타입 조건 매핑 Content-Type, consume
     * HTTP 요청의 Content-Type헤더를 기반으로 미디어타입으로 매핑한다.
     */
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsumes() {
        log.info("mappingConsume");
        return "ok";
    }

    /**
     *  Accept 헤더 기반 Media Type
     *  Http요청의 Accept 헤더를 기반으로 미디어 타입으로 매핑한다.
     */
    @PostMapping(value = "/mapping-produce", produces = "text/html")
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }
}
