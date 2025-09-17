package com.copanote.SpringTest.web.mvc1;


import com.copanote.SpringTest.web.mvc1.controller.ControllerV1;
import com.copanote.SpringTest.web.mvc1.controller.v1.BookControllerV1;
import com.copanote.SpringTest.web.mvc1.controller.v1.MemberControllerV1;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServlet extends HttpServlet {
    private Map<String, ControllerV1> routingTable = new HashMap<>();

    public FrontControllerServlet() {
        routingTable.put("/front-controller/v1/member", new MemberControllerV1());
        routingTable.put("/front-controller/v1/book", new BookControllerV1());
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("frontControllerServletV1 service");
        String requestURI = req.getRequestURI();

        ControllerV1 controller = routingTable.get(requestURI);
        controller.process(req, resp);

    }
}
