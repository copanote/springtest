package com.copanote.SpringTest.web.mvc1;


import com.copanote.SpringTest.web.mvc1.controller.ControllerV3;
import com.copanote.SpringTest.web.mvc1.controller.v3.BookControllerV3;
import com.copanote.SpringTest.web.mvc1.controller.v3.MemberControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServlet3 extends HttpServlet {
    private Map<String, ControllerV3> routingTable = new HashMap<>();

    public FrontControllerServlet3() {
        routingTable.put("/front-controller/v3/member", new MemberControllerV3());
        routingTable.put("/front-controller/v3/book", new BookControllerV3());
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("frontControllerServletV1 service");
        String requestURI = req.getRequestURI();

        ControllerV3 controller = routingTable.get(requestURI);
        Map<String, String> paramMap = createParamMap(req);

        ModelView mv = controller.process(paramMap);

        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(), req, resp);
    }

    private Map<String, String> createParamMap(HttpServletRequest req) {
        //req에서 param 추출
        return new HashMap<>();
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }


}
