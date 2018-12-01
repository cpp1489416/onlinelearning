package cpp.weixin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("weixin")
public class WeixinController {
    @RequestMapping("action.action")
    public void action(HttpServletRequest request, HttpServletResponse response) {
        String echostr = request.getParameter("echostr");

        try {
            response.getWriter().print(echostr);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}