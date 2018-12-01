package cpp.common.shrio;

import cpp.user.po.User;
import cpp.user.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class MySession {
    @Autowired
    private static UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        MySession.userService = userService;
    }

    public static User getUser() {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.getUserByUsername(username);
        SecurityUtils.getSubject().getSession().setAttribute("session name", "this is a session object");
        return userService.getUserByUsername(username);
    }

    public static int getUserId() {
        return getUser() == null ? 0 : getUser().getId();
    }

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Object getSessionAttribute(String name) {
        return SecurityUtils.getSubject().getSession().getAttribute(name);
    }

    public static void setSessionAttribute(String name, Object value) {
        SecurityUtils.getSubject().getSession().setAttribute(name, value);
    }

    public static boolean isLogin() {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser && null != currentUser.getPrincipal()) {
            return true;
        }
        return false;
    }

    //shiro logout
    public static void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.logout();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }

    public static void login(String username, String password) throws Exception {
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        currentUser.login(token);
    }
}
