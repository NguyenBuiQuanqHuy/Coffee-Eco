package huy.ntu.coffee_eco.Responsitories;

import huy.ntu.coffee_eco.Models.Entities.NhanVien;
import huy.ntu.coffee_eco.Models.Entities.TaiKhoan;
import huy.ntu.coffee_eco.Untils.ComonUntils;
import huy.ntu.coffee_eco.Untils.DSUntils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaiKhoanDAL {
    public boolean dangNhap(TaiKhoan taiKhoan) {
        try {
            Connection conn = DSUntils.DBConnect();
            String hashedPassword = ComonUntils.hashPassword(taiKhoan.getMatkhau());
            String sql = "SELECT * FROM nhanvien WHERE TenTK = ? AND MatKhau = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, taiKhoan.getTenTK());
            statement.setString(2, hashedPassword);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int maNV = resultSet.getInt("MaNV");
                NhanVien nhanVien = getNhanVienByMaNV(maNV);
                taiKhoan.setNhanVien(nhanVien);
                taiKhoan.getNhanVien().setMaNV(maNV);
                DSUntils.CloseConnect(conn);
                return true;
            } else {
                DSUntils.CloseConnect(conn);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private NhanVien getNhanVienByMaNV(int maNV) {
        NhanVien nhanVien=null;
        String query = "SELECT * FROM nhanvien WHERE MaNV = ?";
        try (Connection conn = DSUntils.DBConnect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, maNV);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String tenNV = rs.getString("TenNV");
                String diaChi= rs.getString("DiaChi");
                String gioitinh= rs.getString("GioiTinh");
                String dienthoi= rs.getString("DienThoai");
                Float luong= rs.getFloat("Luong");
                String tenTK=rs.getString("TenTK");
                String matkhau=rs.getString("MatKhau");
                return new NhanVien(tenNV,diaChi,gioitinh,dienthoi,luong,tenTK,matkhau);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return nhanVien;
    }
}
