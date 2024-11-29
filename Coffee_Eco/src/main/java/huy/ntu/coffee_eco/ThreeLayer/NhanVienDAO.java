package huy.ntu.coffee_eco.ThreeLayer;

import huy.ntu.coffee_eco.DBConnection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NhanVienDAO {
    // Phương thức thêm nhân viên vào cơ sở dữ liệu
    public boolean AddNhanVien(NhanVien nhanVien) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBConnection.DBConnect();
            // Câu lệnh INSERT không bao gồm id vì MySQL sẽ tự động tăng id
            String sql = "INSERT INTO nhanvien (TenNV, DiaChi, GioiTinh, DienThoai, TenTK, MatKhau) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nhanVien.getTen());
            stmt.setString(2, nhanVien.getDiachi());
            stmt.setString(3, nhanVien.getGioitinh());
            stmt.setString(4, nhanVien.getDienthoai());
            stmt.setString(5, nhanVien.getTaikhoan());
            stmt.setString(6, nhanVien.getMatkhau());
            int result = stmt.executeUpdate();
            DBConnection.CloseConnect(conn);
            return result>0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
        return false;
    }

//    // Phương thức đăng nhập (chỉ thêm người dùng vào bảng 'users')
//    public boolean Login(String taikhoan, String matkhauHash) {
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        try {
//            conn = DBConnection.DBConnect();
//            // Câu lệnh INSERT không bao gồm id vì MySQL sẽ tự động tăng id
//            String sql = "INSERT INTO users (TenTK, MatKhau) VALUES (?, ?)";
//            stmt = conn.prepareStatement(sql);
//            stmt.setString(1, taikhoan);
//            stmt.setString(2, matkhauHash);
//            int result = stmt.executeUpdate();
//            return result > 0;  // Trả về true nếu thêm thành công
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}
