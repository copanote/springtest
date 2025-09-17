package com.copanote.SpringTest.web.mvc1.controller.adapter;

import com.copanote.SpringTest.web.mvc1.ModelView;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface MyHandlerAdapter {

    boolean supports(Object handler);

    ModelView handle(HttpServletRequest req, HttpServletResponse res, Object handler)
            throws ServletException, IOException;


}
