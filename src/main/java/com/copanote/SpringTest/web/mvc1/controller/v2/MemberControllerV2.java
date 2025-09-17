package com.copanote.SpringTest.web.mvc1.controller.v2;

import com.copanote.SpringTest.web.mvc1.MyView;
import com.copanote.SpringTest.web.mvc1.controller.ControllerV2;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MemberControllerV2 implements ControllerV2 {

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request 처리

        //service 호출

        //response
        String viewPath = "/WEB-INF/views/member.jsp";
        return new MyView(viewPath);
    }


}
