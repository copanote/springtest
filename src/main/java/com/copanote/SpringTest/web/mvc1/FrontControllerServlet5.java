package com.copanote.SpringTest.web.mvc1;


import com.copanote.SpringTest.web.mvc1.controller.adapter.ControllerV3HandlerAdapter;
import com.copanote.SpringTest.web.mvc1.controller.adapter.ControllerV4HandlerAdapter;
import com.copanote.SpringTest.web.mvc1.controller.adapter.MyHandlerAdapter;
import com.copanote.SpringTest.web.mvc1.controller.v3.BookControllerV3;
import com.copanote.SpringTest.web.mvc1.controller.v3.MemberControllerV3;
import com.copanote.SpringTest.web.mvc1.controller.v4.BookControllerV4;
import com.copanote.SpringTest.web.mvc1.controller.v4.MemberControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServlet5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();


    public FrontControllerServlet5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/member", new MemberControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/book", new BookControllerV3());
        handlerMappingMap.put("/front-controller/v5/v4/member", new MemberControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/book", new BookControllerV4());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Object handler = getHandler(req);
        if (handler == null) {
            //Not FOUND handler
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        ModelView mv = adapter.handle(req, resp, handler);

        MyView view = viewResolver(mv.getViewName());

        view.render(mv.getModel(), req, resp);

    }


    private Object getHandler(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("no handler");
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }


}
