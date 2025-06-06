package Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import Entity.LoginUser;
import Service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/ProfileUpdateController")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)  // 최대 5MB
public class ProfileUpdateController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");


        if (loginUser == null) {
            request.setAttribute("message", "로그인이 필요합니다.");
            request.setAttribute("nextPage", "MypageController");
    		request.getRequestDispatcher("/WEB-INF/View/editResult.jsp").forward(request, response);

            return;
        }

        Part filePart = request.getPart("profileImage");
        if (filePart == null || filePart.getSize() == 0) {
            request.setAttribute("message", "파일이 선택되지 않았습니다.");
            request.setAttribute("nextPage", "MypageController");
    		request.getRequestDispatcher("/WEB-INF/View/editResult.jsp").forward(request, response);

            return;
        }

        // 원본 파일명 → 확장자 추출
        String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); //파일 이름만 추출하기
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //. 이후로 가져와서 처리해야하는데
        String baseName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        String timestamp = String.valueOf(System.currentTimeMillis());
        String safeFileName = loginUser.getEmail().split("@")[0] + "_" + baseName + "_" + timestamp + extension;
        
        // 저장 경로 지정
        String saveDir = getServletContext().getRealPath("/resources/images");
        File uploadDir = new File(saveDir);		//없으면 또 만들어야해
        if (!uploadDir.exists()) uploadDir.mkdirs();

        // 저장
        String fullPath = saveDir + File.separator + safeFileName;
        filePart.write(fullPath);		//업로드 된 파일 실제 경로에 파일로 저장시켜버리기

        // DB 업데이트
        userService.updateProfileImage(loginUser.getEmail(),safeFileName);

        request.setAttribute("message", "프로필이 성공적으로 변경되었습니다.");
        request.setAttribute("nextPage", "MypageController");
        request.getRequestDispatcher("/WEB-INF/View/editResult.jsp").forward(request, response);
        // 리다이렉트
        //response.sendRedirect("MypageController");
    }
}
