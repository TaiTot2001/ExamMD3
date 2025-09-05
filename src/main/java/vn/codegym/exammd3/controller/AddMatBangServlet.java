package vn.codegym.exammd3.controller;

import vn.codegym.exammd3.dao.MatBangDAO;
import vn.codegym.exammd3.model.MatBang;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

@WebServlet("/addMatBang")
public class AddMatBangServlet extends HttpServlet {
    private MatBangDAO dao = new MatBangDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("view/addMatBang.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try {
            String maMatBang = request.getParameter("maMatBang").trim().toUpperCase();
            String trangThai = request.getParameter("trangThai");
            double dienTich = Double.parseDouble(request.getParameter("dienTich"));
            int tang = Integer.parseInt(request.getParameter("tang"));
            String loaiMatBang = request.getParameter("loaiMatBang");
            double giaTien = Double.parseDouble(request.getParameter("giaTien"));

            // Sử dụng formatter dd/MM/yyyy
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate ngayBatDau = LocalDate.parse(request.getParameter("ngayBatDau"), formatter);
            LocalDate ngayKetThuc = LocalDate.parse(request.getParameter("ngayKetThuc"), formatter);

            // Kiểm tra mã đã tồn tại
            if (dao.exists(maMatBang)) {
                request.setAttribute("error", "Mã mặt bằng vừa thêm đã tồn tại");
                request.getRequestDispatcher("view/addMatBang.jsp").forward(request, response);
                return;
            }

            // Kiểm tra ngày kết thúc >= 6 tháng so với bắt đầu
            if (ChronoUnit.MONTHS.between(ngayBatDau, ngayKetThuc) < 6) {
                request.setAttribute("error", "Ngày kết thúc phải cách ngày bắt đầu ít nhất 6 tháng");
                request.getRequestDispatcher("view/addMatBang.jsp").forward(request, response);
                return;
            }

            // Tạo đối tượng và thêm vào DB
            MatBang mb = new MatBang(maMatBang, trangThai, dienTich, tang, loaiMatBang, giaTien, ngayBatDau, ngayKetThuc);
            dao.add(mb);

            response.sendRedirect("listMatBang");
        } catch (NumberFormatException | DateTimeParseException e) {
            request.setAttribute("error", "Dữ liệu nhập không hợp lệ. Ngày phải định dạng dd/MM/yyyy và số hợp lệ.");
            request.getRequestDispatcher("view/addMatBang.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Có lỗi xảy ra. Vui lòng thử lại.");
            request.getRequestDispatcher("view/addMatBang.jsp").forward(request, response);
        }
    }
}

