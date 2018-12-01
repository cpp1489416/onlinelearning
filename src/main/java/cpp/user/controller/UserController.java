package cpp.user.controller;

import cpp.common.shrio.MySession;
import cpp.common.OutInfo;
import cpp.common.Page;
import cpp.user.po.User;
import cpp.user.po.UserCourseContent;
import cpp.user.service.UserCourseContentService;
import cpp.user.service.UserService;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserCourseContentService userCourseContentService;

    @RequestMapping("login.action")
    @ResponseBody
    public OutInfo login(User user) {
        try {
            MySession.login(user.getUsername(), user.getPassword());
            return OutInfo.success();
        } catch (Exception e) {
            e.printStackTrace();
            OutInfo out = new OutInfo();
            out.code = -1;
            out.status = e.getMessage();
            return out;
        }
    }

    @RequestMapping("isLoggedIn.action")
    @ResponseBody
    public OutInfo isLoggedIn() {
        User user = MySession.getUser();
        if (user == null) {
            return OutInfo.notLogin();
        } else {
            return OutInfo.success();
        }
    }

    @RequestMapping("logout.action")
    @ResponseBody
    public OutInfo logout() {
        MySession.logout();
        return OutInfo.success();
    }

    @RequestMapping("register.action")
    @ResponseBody
    public OutInfo register(User user) {
        User oldUser = userService.getUserByUsername(user.getUsername());
        if (oldUser != null) {
            OutInfo out = OutInfo.failure();
            out.status = "用户名或密码已存在";
            return out;
        } else {
            userService.addUser(user);
            return OutInfo.success();
        }
    }

    @RequestMapping("user.action")
    @ResponseBody
    public User user() {
        return MySession.getUser();
    }

    @RequestMapping("contents.action")
    @ResponseBody
    public List<UserCourseContent> contents() {
        User user = MySession.getUser();
        if (user == null) {
            return null;
        }
        int userId = user.getId();
        return userCourseContentService.listByUserId(userId);
    }

    static class ContentsIn {
        public Page<UserCourseContent> page;
    }

    @RequestMapping("contentsPage.action")
    @ResponseBody
    public Page<UserCourseContent> contentsPage(@RequestBody ContentsIn in) {
        User user = MySession.getUser();
        if (user == null) {
            return null;
        }
        int userId = user.getId();
        return userCourseContentService.listByUserIdPage(userId, in.page);
    }

    @RequestMapping("modifyInfo.action")
    @ResponseBody
    public OutInfo modifyInfo(User user) {
        User loggedUser = MySession.getUser();
        if (loggedUser == null || user.getId() != loggedUser.getId()) {
            return OutInfo.notLogin();
        }

        userService.modify(user);
        MySession.logout();
        return OutInfo.success();
    }

    @RequestMapping("modifyPassword.action")
    @ResponseBody
    public OutInfo modifyPassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        User loggedUser = MySession.getUser();
        if (loggedUser == null) {
            return OutInfo.notLogin();
        }

        if (!loggedUser.getPassword().equals(oldPassword)) {
            return OutInfo.failure("旧密码错误");
        }

        User user = new User();
        user.setId(loggedUser.getId());
        user.setPassword(newPassword);
        userService.modifyPassword(user);
        MySession.logout();;
        return OutInfo.success();
    }

    public static class UsersPageInInfo {
        public User user;
        public Page<User> page;
    }

    @RequiresRoles("admin")
    @RequestMapping("usersPage.action")
    @ResponseBody
    public Page<User> usersPage(@RequestBody UsersPageInInfo in) {
        return userService.listUsersPage(in.user, in.page);
    }

    @RequiresRoles("admin")
    @RequestMapping("modifyRole.action")
    @ResponseBody
    public OutInfo modifyRole(User user) {
        if (user.getRole() < 0 || user.getRole() > 2) {
            return OutInfo.failure("角色编号不对");
        }
        User oldUser = userService.getUserById(user.getId());
        if (oldUser.getRole() == 2 && user.getRole() < 2) {
            return OutInfo.failure("管理员不能降级");
        }

        userService.modifyRole(user);
        return OutInfo.success();
    }
}
