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
            String sql = "INSERT INTO nhanvien (ID,TenNV, DiaChi, GioiTinh, DienThoai,Luong,TenTK, MatKhau) VALUES (?,?, ?, ?, ?, ?, ?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,nhanVien.getMaNV());
            stmt.setString(2, nhanVien.getTen());
            stmt.setString(3, nhanVien.getDiachi());
            stmt.setString(4, nhanVien.getGioitinh());
            stmt.setString(5, nhanVien.getDienthoai());
            stmt.setFloat(6,nhanVien.getLuong());
            stmt.setString(7, nhanVien.getTaikhoan());
            stmt.setString(8, nhanVien.getMatkhau());
            int result = stmt.executeUpdate();
            DSUntils.CloseConnect(conn);
            return result>0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
        return false;
    }

  public boolean Login(String taikhoan, String matkhauHash) {
        Connection conn = null;
       PreparedStatement stmt = null;
        try {
            conn = DSUntils.DBConnect();
            String sql = "INSERT INTO users (TenTK, MatKhau) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, taikhoan);
            stmt.setString(2, matkhauHash);
           int result = stmt.executeUpdate();
            DSUntils.CloseConnect(conn);
            return result > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void LoadNhanVien(ObservableList<NhanVien> nhanVienList){
        try {
            Connection conn=DSUntils.DBConnect();
            String query = "SELECT * FROM nhanvien";
            Statement statement=conn.createStatement();
            ResultSet resultSet=statement.executeQuery(query);

            while (resultSet.next()){
                String maNV=resultSet.getString("ID");
                String ten = resultSet.getString("TenNV");
                String diachi = resultSet.getString("DiaChi");
                String gioitinh = resultSet.getString("GioiTinh");
                String dienthoai = resultSet.getString("DienThoai");
                Float luong = resultSet.getFloat("Luong");
                String tenTK = resultSet.getString("TenTK");
                String matkhau = resultSet.getString("MatKhau");

                NhanVien nv = new NhanVien(maNV,ten, diachi, gioitinh, dienthoai, luong, tenTK, matkhau);
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
            conn.setAutoCommit(false);
            String query = "UPDATE nhanvien SET TenNV=?, DiaChi=?, GioiTinh=?, DienThoai=?, Luong=?, TenTK=?, MatKhau=? WHERE ID=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1,nv.getMaNV());
            pstmt.setString(2, nv.getTen());
            pstmt.setString(3, nv.getDiachi());
            pstmt.setString(4, nv.getGioitinh());
            pstmt.setString(5, nv.getDienthoai());
            pstmt.setFloat(6, nv.getLuong());
            pstmt.setString(7, nv.getTaikhoan());
            pstmt.setString(8, nv.getMatkhau());
            pstmt.executeUpdate();
            conn.commit();
            conn.setAutoCommit(true);
            DSUntils.CloseConnect(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void xoaNV(String maNV) {
        try {
            Connection conn = DSUntils.DBConnect();
            String query = "DELETE FROM nhanvien WHERE ID=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, maNV);
            pstmt.executeUpdate();
            DSUntils.CloseConnect(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
