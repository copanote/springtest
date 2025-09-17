package com.copanote.SpringTest.web.mvc1;


import com.copanote.SpringTest.web.mvc1.controller.ControllerV2;
import com.copanote.SpringTest.web.mvc1.controller.v2.BookControllerV2;
import com.copanote.SpringTest.web.mvc1.controller.v2.MemberControllerV2;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServlet2 extends HttpServlet {
    private Map<String, ControllerV2> routingTable = new HashMap<>();

    public FrontControllerServlet2() {
        routingTable.put("/front-controller/v2/member", new MemberControllerV2());
        routingTable.put("/front-controller/v2/book", new BookControllerV2());
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("frontControllerServletV1 service");
        String requestURI = req.getRequestURI();

        ControllerV2 controller = routingTable.get(requestURI);
        MyView view = controller.process(req, resp);
        view.render(req, resp);

    }
}
