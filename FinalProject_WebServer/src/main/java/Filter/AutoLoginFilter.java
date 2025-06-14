package Filter;

import java.io.IOException;
import java.util.Map;

import Entity.LoginUser;
import Service.LoginService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

@WebFilter("/*")
public class AutoLoginFilter implements Filter {
    
    private LoginService loginService = new LoginService(); // 자동 로그인 시 유저 정보 불러오기 위해

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); // 이미 생성된 세션 있으면 가져오고 없으면 null

        // 이미 로그인된 경우는 필터 패스
        if (session != null && session.getAttribute("loginUser") != null) {
            chain.doFilter(request, response);
            return;
        }

        // 로그인 안된 경우만 쿠키 확인
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("autoLogin".equals(cookie.getName())) {
                    String email = cookie.getValue();
                    
                    try {
                        Map<String, Object> userInfo = loginService.getUserInfo(email);
                        if (userInfo != null) {
                            int user_id = (int)userInfo.get("user_id");
                            String name = (String)userInfo.get("name");
                            String user_class = (String)userInfo.get("user_class");
                            LoginUser loginUser = new LoginUser(email, name, user_class, user_id);

                            HttpSession newSession = req.getSession(true);
                            newSession.setAttribute("loginUser", loginUser);

                            System.out.println("[AutoLoginFilter] 자동 로그인 성공 - " + email);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        chain.doFilter(request, response);
    }
}
