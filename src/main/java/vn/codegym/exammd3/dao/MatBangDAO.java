package vn.codegym.exammd3.dao;

import vn.codegym.exammd3.model.MatBang;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MatBangDAO {
    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/tcomplex", "root", "123456");
    }

    public boolean exists(String maMatBang) throws SQLException {
        String sql = "SELECT COUNT(*) FROM MatBang WHERE maMatBang=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maMatBang);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(MatBang matBang) throws SQLException {
        String sql = "INSERT INTO MatBang VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, matBang.getMaMatBang());
            ps.setString(2, matBang.getTrangThai());
            ps.setDouble(3, matBang.getDienTich());
            ps.setInt(4, matBang.getTang());
            ps.setString(5, matBang.getLoaiMatBang());
            ps.setDouble(6, matBang.getGiaTien());
            ps.setDate(7, Date.valueOf(matBang.getNgayBatDau()));
            ps.setDate(8, Date.valueOf(matBang.getNgayKetThuc()));
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MatBang> getAll() throws SQLException {
        List<MatBang> list = new ArrayList<>();
        String sql = "SELECT * FROM MatBang";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new MatBang(
                        rs.getString("maMatBang"),
                        rs.getString("trangThai"),
                        rs.getDouble("dienTich"),
                        rs.getInt("tang"),
                        rs.getString("loaiMatBang"),
                        rs.getDouble("giaTien"),
                        rs.getDate("ngayBatDau").toLocalDate(),
                        rs.getDate("ngayKetThuc").toLocalDate()
                ));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<MatBang> search(String loaiMatBang, Double giaTien, Integer tang) throws SQLException {
        List<MatBang> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM MatBang WHERE 1=1");

        // Chỉ thêm điều kiện nếu người dùng nhập
        if (loaiMatBang != null && !loaiMatBang.isEmpty()) {
            sql.append(" AND loaiMatBang = ?");
        }
        if (giaTien != null) {
            sql.append(" AND giaTien BETWEEN ? AND ?");
        }
        if (tang != null) {
            sql.append(" AND tang = ?");
        }

        sql.append(" ORDER BY dienTich ASC"); // sắp xếp tăng dần theo diện tích

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (loaiMatBang != null && !loaiMatBang.isEmpty()) ps.setString(index++, loaiMatBang);
            if (giaTien != null) {
                ps.setDouble(index++, giaTien - 500000); // giá thấp hơn 500k
                ps.setDouble(index++, giaTien + 500000); // giá cao hơn 500k
            }
            if (tang != null) ps.setInt(index++, tang);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Lấy LocalDate từ java.sql.Date
                java.sql.Date dbNgayBatDau = rs.getDate("ngayBatDau");
                java.sql.Date dbNgayKetThuc = rs.getDate("ngayKetThuc");

                list.add(new MatBang(
                        rs.getString("maMatBang"),
                        rs.getString("trangThai"),
                        rs.getDouble("dienTich"),
                        rs.getInt("tang"),
                        rs.getString("loaiMatBang"),
                        rs.getDouble("giaTien"),
                        dbNgayBatDau != null ? dbNgayBatDau.toLocalDate() : null,
                        dbNgayKetThuc != null ? dbNgayKetThuc.toLocalDate() : null
                ));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return list;
    }
    public void delete(String maMatBang) throws SQLException {
        String sql = "DELETE FROM MatBang WHERE maMatBang = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maMatBang);
            ps.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
