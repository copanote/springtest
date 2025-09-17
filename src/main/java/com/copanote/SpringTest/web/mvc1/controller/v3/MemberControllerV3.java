package com.copanote.SpringTest.web.mvc1.controller.v3;

import com.copanote.SpringTest.web.mvc1.ModelView;
import com.copanote.SpringTest.web.mvc1.controller.ControllerV3;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.util.Map;

public class MemberControllerV3 implements ControllerV3 {

    @Override
    public ModelView process(Map<String, String> paramMap) throws ServletException, IOException {

        //response
        String viewPath = "/WEB-INF/views/member.jsp";
        return new ModelView(viewPath);
    }
}
