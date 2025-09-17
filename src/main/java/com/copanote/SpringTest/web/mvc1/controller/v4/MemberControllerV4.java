package com.copanote.SpringTest.web.mvc1.controller.v4;

import com.copanote.SpringTest.web.mvc1.controller.ControllerV4;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.util.Map;

public class MemberControllerV4 implements ControllerV4 {

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) throws ServletException, IOException {
        return "my-view";
    }
}
