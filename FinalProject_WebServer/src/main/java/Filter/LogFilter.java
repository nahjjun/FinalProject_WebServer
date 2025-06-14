package Filter;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

@WebFilter("/*") // 전체 경로 감시
public class LogFilter implements Filter {

    private PrintWriter logWriter;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            FileWriter fw = new FileWriter("C:/logs/skv_log.txt", true); // 저장 경로와 파일명
            logWriter = new PrintWriter(fw, true); 
        } catch (IOException e) {
            throw new ServletException("로그 파일 생성 실패", e);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        // 로그 기록할 항목들
        String log = String.format("[%s] %s 요청 - IP: %s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),req.getRequestURI(), req.getRemoteAddr());

        // 콘솔에도 출력 (선택)
        System.out.println(log);

        // 로그 파일에 기록
        logWriter.println(log);

        // 다음 필터 또는 서블릿으로 전달
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        if (logWriter != null) {
            logWriter.close();
        }
    }
}
