package com.copanote.SpringTest.web.mvc1.controller;

import com.copanote.SpringTest.web.mvc1.ModelView;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.util.Map;

public interface ControllerV3 {
    ModelView process(Map<String, String> paramMap) throws ServletException, IOException;
}
