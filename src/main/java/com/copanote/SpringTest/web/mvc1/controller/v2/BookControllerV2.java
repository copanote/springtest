package com.copanote.SpringTest.web.mvc1.controller.v2;

import com.copanote.SpringTest.web.mvc1.MyView;
import com.copanote.SpringTest.web.mvc1.controller.ControllerV2;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BookControllerV2 implements ControllerV2 {


    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return null;
    }
}
