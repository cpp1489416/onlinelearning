package cpp.auth.filter;

import cpp.auth.mapper.AuthMapper;
import cpp.common.shrio.MySession;
import cpp.user.po.User;
import org.apache.shiro.session.Session;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.LogRecord;

// @WebFilter("/*") 多余的功能，暂时不用
public class AuthFilter implements Filter {
    @Autowired
    private AuthMapper authMapper;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        Boolean b = (Boolean) request.getSession().getAttribute("authed");
        String url = request.getRequestURI();

        if (url.endsWith(".css") || url.endsWith(".js") ||
                url.endsWith("auth.html") ||
                url.endsWith("auth.action") || url.endsWith("/auth/reload.action") ||
                b != null && b ||
                !authMapper.needPassword() || url.contains("weixin")) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect("/onlinelearning/auth/auth.html");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
    }

    @Override
    public void destroy() {

    }
}
