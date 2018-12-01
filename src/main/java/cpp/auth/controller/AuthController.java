package cpp.auth.controller;

import cpp.auth.mapper.AuthMapper;
import cpp.common.OutInfo;
import cpp.common.shrio.MySession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("auth") // 多余的功能，暂时不用
public class AuthController {
    @Autowired
    private AuthMapper authMapper;

    @RequestMapping("auth.action")
    @ResponseBody
    public OutInfo auth(@RequestParam("password") String password, HttpSession session) {
        if (password == null) {
            return OutInfo.failure();
        }
        if (authMapper.auth(password)) {
            session.setAttribute("authed", true);
            return OutInfo.success();
        } else {
            return OutInfo.failure();
        }
    }

    @RequestMapping("reload.action")
    @ResponseBody
    public OutInfo reload() {
        try {
            authMapper.reload();
            return OutInfo.success();
        } catch (Exception e) {
            return OutInfo.failure("读取xml文件错误： "+ e.getMessage());
        }
    }
}
