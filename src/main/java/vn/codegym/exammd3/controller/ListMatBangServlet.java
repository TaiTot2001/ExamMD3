package vn.codegym.exammd3.controller;

import vn.codegym.exammd3.dao.MatBangDAO;
import vn.codegym.exammd3.model.MatBang;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/listMatBang")
public class ListMatBangServlet extends HttpServlet {
    private final MatBangDAO dao = new MatBangDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try {
            String loaiMatBang = request.getParameter("loaiMatBang");
            String giaTienStr = request.getParameter("giaTien");
            String tangStr = request.getParameter("tang");

            Double giaTien = (giaTienStr != null && !giaTienStr.isEmpty()) ? Double.parseDouble(giaTienStr) : null;
            Integer tang = (tangStr != null && !tangStr.isEmpty()) ? Integer.parseInt(tangStr) : null;

            List<MatBang> list = dao.search(loaiMatBang, giaTien, tang);
            request.setAttribute("matBangList", list);
            request.getRequestDispatcher("view/listMatBang.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Có lỗi xảy ra khi tải danh sách mặt bằng.");
            request.getRequestDispatcher("view/listMatBang.jsp").forward(request, response);
        }
    }
}