package com.copanote.SpringTest.web.mvc1;


import com.copanote.SpringTest.web.mvc1.controller.ControllerV4;
import com.copanote.SpringTest.web.mvc1.controller.v4.BookControllerV4;
import com.copanote.SpringTest.web.mvc1.controller.v4.MemberControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServlet4 extends HttpServlet {
    private Map<String, ControllerV4> routingTable = new HashMap<>();

    public FrontControllerServlet4() {
        routingTable.put("/front-controller/v4/member", new MemberControllerV4());
        routingTable.put("/front-controller/v4/book", new BookControllerV4());
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("frontControllerServletV1 service");
        String requestURI = req.getRequestURI();

        ControllerV4 controller = routingTable.get(requestURI);
        Map<String, String> paramMap = createParamMap(req);
        Map<String, Object> model = new HashMap<>();

        String viewName = controller.process(paramMap, model);
        MyView view = viewResolver(viewName);

        view.render(model, req, resp);
    }

    private Map<String, String> createParamMap(HttpServletRequest req) {
        //req에서 param 추출
        return new HashMap<>();
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
