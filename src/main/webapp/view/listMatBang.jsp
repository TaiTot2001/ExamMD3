<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Danh sách Mặt Bằng - TComplex</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding: 20px;
            background-color: #f8f9fa;
        }

        .table th, .table td {
            vertical-align: middle;
        }

        .form-select, .form-control {
            max-width: 200px;
        }

        .search-form {
            margin-bottom: 30px;
        }

        .table thead {
            background-color: #0d6efd;
            color: white;
        }

        .table-striped > tbody > tr:nth-of-type(odd) {
            background-color: #e9f2ff;
        }
    </style>
</head>
<body>
<h2 class="mb-4">Danh sách Mặt Bằng TComplex</h2>

<form method="get" action="listMatBang" class="row g-3 search-form align-items-end">
    <div class="col-auto">
        <label class="form-label">Loại mặt bằng</label>
        <select name="loaiMatBang" class="form-select">
            <option value="">-- Tất cả --</option>
            <option value="Văn phòng chia sẻ" ${param.loaiMatBang=='Văn phòng chia sẻ'?'selected':''}>Văn phòng chia
                sẻ
            </option>
            <option value="Văn phòng trọn gói" ${param.loaiMatBang=='Văn phòng trọn gói'?'selected':''}>Văn phòng trọn
                gói
            </option>
        </select>
    </div>
    <div class="col-auto">
        <label class="form-label">Giá tiền</label>
        <input type="number" name="giaTien" class="form-control" placeholder="VNĐ" value="${param.giaTien}">
    </div>
    <div class="col-auto">
        <label class="form-label">Tầng</label>
        <input type="number" name="tang" class="form-control" placeholder="Tầng" value="${param.tang}">
    </div>
    <div class="col-auto">
        <button type="submit" class="btn btn-primary">Tìm kiếm</button>
        <a href="listMatBang" class="btn btn-secondary">Reset</a>
    </div>
</form>

<table class="table table-bordered table-striped table-hover">
    <thead>
    <tr>
        <th>Mã</th>
        <th>Trạng thái</th>
        <th>Diện tích (m²)</th>
        <th>Tầng</th>
        <th>Loại</th>
        <th>Giá (VNĐ)</th>
        <th>Bắt đầu</th>
        <th>Kết thúc</th>
        <th>Hành động</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="mb" items="${matBangList}">
        <tr>
            <td>${mb.maMatBang}</td>
            <td>${mb.trangThai}</td>
            <td>${mb.dienTich}</td>
            <td>${mb.tang}</td>
            <td>${mb.loaiMatBang}</td>
            <td>${mb.giaTien}</td>
            <td>${mb.ngayBatDauStr}</td>
            <td>${mb.ngayKetThucStr}</td>
            <td>
                <form action="deleteMatBang" method="post"
                      onsubmit="return confirm('Bạn có chắc muốn xóa mặt bằng ${mb.maMatBang} không?');">
                    <input type="hidden" name="maMatBang" value="${mb.maMatBang}">
                    <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty matBangList}">
        <tr>
            <td colspan="8" class="text-center">Không có dữ liệu</td>
        </tr>
    </c:if>
    </tbody>
</table>
</body>
</html>
