package vn.codegym.exammd3.controller;

import vn.codegym.exammd3.dao.MatBangDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/deleteMatBang")
public class DeleteMatBangServlet extends HttpServlet {
    private MatBangDAO dao = new MatBangDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String maMatBang = request.getParameter("maMatBang");
        try {
            if (maMatBang != null && !maMatBang.isEmpty()) {
                dao.delete(maMatBang); // gọi phương thức xóa trong DAO
            }
            response.sendRedirect("listMatBang"); // quay về danh sách
        } catch (Exception e) {
            request.setAttribute("error", "Xóa không thành công. Vui lòng thử lại.");
            request.getRequestDispatcher("view/listMatBang.jsp").forward(request, response);
        }
    }
}
