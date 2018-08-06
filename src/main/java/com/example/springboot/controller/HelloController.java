package com.example.springboot.controller;

import com.example.springboot.service.TransUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {
    @Autowired
    private TransUrlService transUrlService;

    @RequestMapping(value = "/hello")
    public String sayHello(HttpServletRequest request) {
        String pin = request.getParameter("pin");
        int channel = Integer.parseInt(request.getParameter("channel"));
        return pin+channel;
    }

    @RequestMapping(value = "/")
    public String homePage() {
        return "index.html";
    }

    @RequestMapping(value = "/transform")
    public String transPage() {
        return "transform.html";
    }

    @ResponseBody
    @RequestMapping(value = {"/server", "/fz/server"})
    public String transUrl(HttpServletRequest request) {
        String hostPath = request.getScheme() + "://" + request.getServerName();
        String item_id = request.getParameter("item_id");
        String baiduFlag = request.getParameter("baidu");
        String result = transUrlService.getTransUrl(hostPath, item_id, baiduFlag);
        return result;
    }

    //fz目录的处理逻辑
    @RequestMapping(value = "/fz")
    public String homePageFz() {
        return "/fz/index.html";
    }

    @RequestMapping(value = "/fz/transform")
    public String transPageFz() {
        return "/fz/transform.html";
    }
}
