package com.copanote.SpringTest.web.mvc1.controller;

import jakarta.servlet.ServletException;

import java.io.IOException;
import java.util.Map;

public interface ControllerV4 {
    String process(Map<String, String> paramMap, Map<String, Object> model) throws ServletException, IOException;
}
