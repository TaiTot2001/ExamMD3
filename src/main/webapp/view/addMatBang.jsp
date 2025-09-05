<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Thêm Mặt Bằng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script>
        function validateForm() {
            const ma = document.getElementById("maMatBang").value.trim();
            const dienTich = parseFloat(document.getElementById("dienTich").value);
            const tang = parseInt(document.getElementById("tang").value);
            const giaTien = parseFloat(document.getElementById("giaTien").value);
            const ngayBatDau = document.getElementById("ngayBatDau").value;
            const ngayKetThuc = document.getElementById("ngayKetThuc").value;

            const maRegex = /^[A-Z0-9]{3}[A-Z0-9]{2}[A-Z0-9]{2}$/;
            if (!maRegex.test(ma)) {
                alert("Mã mặt bằng không đúng định dạng XXXXXXX\n*Có 7X với X là số hoặc các kýtự alphabet viết hoa ");
                return false;
            }
            if (dienTich <= 20) {
                alert("Diện tích phải > 20m2");
                return false;
            }
            if (tang < 1 || tang > 15) {
                alert("Tầng phải từ 1-15");
                return false;
            }
            if (giaTien <= 1000000) {
                alert("Giá tiền phải > 1.000.000 VNĐ");
                return false;
            }
            if (!ngayBatDau || !ngayKetThuc) {
                alert("Vui lòng nhập ngày bắt đầu và kết thúc");
                return false;
            }

            const startDate = new Date(ngayBatDau);
            const endDate = new Date(ngayKetThuc);
            const sixMonthsLater = new Date(startDate);
            sixMonthsLater.setMonth(sixMonthsLater.getMonth() + 6);

            if (endDate < sixMonthsLater) {
                alert("Ngày kết thúc phải cách ngày bắt đầu ít nhất 6 tháng");
                return false;
            }

            return true;
        }

        function formatDateForInput(dateStr) {
            if (!dateStr) return '';
            const parts = dateStr.split("/");
            return `${parts[2]}-${parts[1].padStart(2,'0')}-${parts[0].padStart(2,'0')}`;
        }
    </script>
</head>
<body class="container py-4">
<h2 class="mb-4">Thêm Mặt Bằng</h2>

<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>

<form action="addMatBang" method="post" onsubmit="return validateForm()">
    <div class="row mb-3">
        <label class="col-sm-3 col-form-label">Mã mặt bằng (*)</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" id="maMatBang" name="maMatBang" value="${param.maMatBang}" required>
        </div>
    </div>

    <div class="row mb-3">
        <label class="col-sm-3 col-form-label">Diện tích (*)</label>
        <div class="col-sm-9">
            <input type="number" class="form-control" id="dienTich" name="dienTich" min="21" value="${param.dienTich}"
                   required>
        </div>
    </div>

    <div class="row mb-3">
        <label class="col-sm-3 col-form-label">Trạng thái (*)</label>
        <div class="col-sm-9">
            <select class="form-select" id="trangThai" name="trangThai" required>
                <option value="Trống" ${param.trangThai=='Trống'?'selected':''}>Trống</option>
                <option value="Hạ tầng" ${param.trangThai=='Hạ tầng'?'selected':''}>Hạ tầng</option>
                <option value="Đầy đủ" ${param.trangThai=='Đầy đủ'?'selected':''}>Đầy đủ</option>
            </select>
        </div>
    </div>

    <div class="row mb-3">
        <label class="col-sm-3 col-form-label">Tầng (*)</label>
        <div class="col-sm-9">
            <select class="form-select" id="tang" name="tang" required>
                <c:forEach var="i" begin="1" end="15">
                    <option value="${i}" ${param.tang==i?'selected':''}>${i}</option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="row mb-3">
        <label class="col-sm-3 col-form-label">Loại văn phòng (*)</label>
        <div class="col-sm-9">
            <select class="form-select" id="loaiMatBang" name="loaiMatBang" required>
                <option value="Văn phòng chia sẻ" ${param.loaiMatBang=='Văn phòng chia sẻ'?'selected':''}>Văn phòng chia
                    sẻ
                </option>
                <option value="Văn phòng trọn gói" ${param.loaiMatBang=='Văn phòng trọn gói'?'selected':''}>Văn phòng
                    trọn gói
                </option>
            </select>
        </div>
    </div>

    <div class="row mb-3">
        <label class="col-sm-3 col-form-label">Giá cho thuê (*)</label>
        <div class="col-sm-9">
            <input type="number" class="form-control" id="giaTien" name="giaTien" min="1000001" value="${param.giaTien}"
                   required>
        </div>
    </div>

    <div class="row mb-3">
        <label class="col-sm-3 col-form-label">Ngày (*)</label>
        <div class="col-sm-4">
            <label for="ngayBatDau" class="form-label">Bắt đầu</label>
            <input type="text" class="form-control" id="ngayBatDau" name="ngayBatDau"
                   placeholder="dd/MM/yyyy" pattern="\d{2}/\d{2}/\d{4}"
                   value="${param.ngayBatDau != null ? param.ngayBatDau : ''}" required>
        </div>
        <div class="col-sm-4">
            <label for="ngayKetThuc" class="form-label">Kết thúc</label>
            <input type="text" class="form-control" id="ngayKetThuc" name="ngayKetThuc"
                   placeholder="dd/MM/yyyy" pattern="\d{2}/\d{2}/\d{4}"
                   value="${param.ngayKetThuc != null ? param.ngayKetThuc : ''}" required>
        </div>
    </div>
    <div class="mb-3 text-center">
        <button type="submit" class="btn btn-primary me-2">Lưu</button>
        <button type="reset" class="btn btn-secondary"
                onclick="return confirm('Bạn có chắc muốn xóa toàn bộ dữ liệu đã nhập?')">Hủy
        </button>
    </div>
</form>
</body>
</html>
