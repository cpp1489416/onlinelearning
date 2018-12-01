package cpp.user.web;

import cpp.common.shrio.MySession;
import cpp.user.po.User;
import cpp.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String username = token.getUsername();
        String password = new String(token.getPassword());
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        User authUser = userService.getUserByUsernameAndPassword(user);
        if (authUser == null) {
            SecurityUtils.getSubject().logout();
            throw new AuthenticationException("用户名或密码错误");
        }
        SimpleAuthenticationInfo s  = new SimpleAuthenticationInfo(username, password, getName());

        return s;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
        User user = MySession.getUser() ;
        if (user == null) {
            return s;
        }
        if (user.getRole() == 0) {
            s.addRole("student");
        } else {
            s.addRole("teacher");
            if (user.getRole() == 2) {
                s.addRole("admin");
            }
        }

        return s;
    }
}
