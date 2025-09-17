package com.copanote.SpringTest.web.mvc1.controller.adapter;

import com.copanote.SpringTest.web.mvc1.ModelView;
import com.copanote.SpringTest.web.mvc1.controller.ControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3);
    }

    @Override
    public ModelView handle(HttpServletRequest req, HttpServletResponse res, Object handler) throws ServletException, IOException {
        ControllerV3 controller = (ControllerV3) handler;
        Map<String, String> paramMap = createParamMap(req);
        ModelView mv = controller.process(paramMap);
        return mv;
    }


    private Map<String, String> createParamMap(HttpServletRequest req) {
        //req에서 param 추출
        return new HashMap<>();
    }
}
