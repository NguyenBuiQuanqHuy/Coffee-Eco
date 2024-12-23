package huy.ntu.coffee_eco.Responsitories;

import huy.ntu.coffee_eco.Untils.DSUntils;
import huy.ntu.coffee_eco.Models.Entities.NhanVien;
import javafx.collections.ObservableList;
import java.sql.*;


public class NhanVienDAL {
    public boolean AddNhanVien(NhanVien nhanVien) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DSUntils.DBConnect();
            String sql = "INSERT INTO nhanvien (TenNV, DiaChi, GioiTinh, DienThoai,Luong,TenTK, MatKhau) VALUES (?, ?, ?, ?, ?, ?,?)";
            stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, nhanVien.getTen());
            stmt.setString(2, nhanVien.getDiachi());
            stmt.setString(3, nhanVien.getGioitinh());
            stmt.setString(4, nhanVien.getDienthoai());
            stmt.setFloat(5,nhanVien.getLuong());
            stmt.setString(6, nhanVien.getTaikhoan());
            stmt.setString(7, nhanVien.getMatkhau());
            int result = stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys != null && generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                nhanVien.setMaNV(generatedId);
            }
            DSUntils.CloseConnect(conn);
            return result>0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
        return false;
    }

    public void LoadNhanVien(ObservableList<NhanVien> nhanVienList){
        try {
            Connection conn=DSUntils.DBConnect();
            String query = "SELECT * FROM nhanvien";
            Statement statement=conn.createStatement();
            ResultSet resultSet=statement.executeQuery(query);

            while (resultSet.next()){
                int manv = resultSet.getInt("MaNV");
                String ten = resultSet.getString("TenNV");
                String diachi = resultSet.getString("DiaChi");
                String gioitinh = resultSet.getString("GioiTinh");
                String dienthoai = resultSet.getString("DienThoai");
                Float luong = resultSet.getFloat("Luong");
                String tenTK = resultSet.getString("TenTK");
                String matkhau = resultSet.getString("MatKhau");

                NhanVien nv = new NhanVien(ten, diachi, gioitinh, dienthoai, luong, tenTK, matkhau);
                nv.setMaNV(manv);
                nhanVienList.add(nv);
            }
            DSUntils.CloseConnect(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void suaNV(NhanVien nv) {
        try {
            Connection conn = DSUntils.DBConnect();
            String query = "UPDATE nhanvien SET TenNV=?, DiaChi=?, GioiTinh=?, DienThoai=?, Luong=?, TenTK=?, MatKhau=? WHERE MaNV=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, nv.getTen());
            pstmt.setString(2, nv.getDiachi());
            pstmt.setString(3, nv.getGioitinh());
            pstmt.setString(4, nv.getDienthoai());
            pstmt.setFloat(5, nv.getLuong());
            pstmt.setString(6, nv.getTaikhoan());
            pstmt.setString(7, nv.getMatkhau());
            pstmt.setInt(8,nv.getMaNV());
            pstmt.executeUpdate();
            DSUntils.CloseConnect(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void xoaNV(int maNV) {
        try {
            Connection conn = DSUntils.DBConnect();
            String updateQuery = "UPDATE hoadon SET MaNV = 0 WHERE MaNV = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
            updateStmt.setInt(1, maNV);
            updateStmt.executeUpdate();

            String query = "DELETE FROM nhanvien WHERE MaNV = ? ";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, maNV);
            pstmt.executeUpdate();
            DSUntils.CloseConnect(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
