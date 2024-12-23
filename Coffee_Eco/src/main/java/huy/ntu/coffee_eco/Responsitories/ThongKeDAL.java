package huy.ntu.coffee_eco.Responsitories;

import huy.ntu.coffee_eco.Models.Entities.ChiTietHoaDon;
import huy.ntu.coffee_eco.Models.Entities.HoaDon;
import huy.ntu.coffee_eco.Untils.DSUntils;

import java.sql.*;
import java.util.List;

public class ThongKeDAL {
    public void loadHoaDon(List<HoaDon> hoaDons){
        String query="SELECT * FROM hoadon";
        try {
            Connection conn = DSUntils.DBConnect();
            PreparedStatement statement= conn.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next()){
                int maHD= result.getInt("MaHD");
                int maNV=result.getInt("MaNV");
                Timestamp ngaytao=result.getTimestamp("NgayTao");
                Float tongtien=result.getFloat("TongTien");

                HoaDon hoaDon =new HoaDon(maNV,ngaytao,tongtien);
                hoaDon.setMaHD(maHD);
                hoaDons.add(hoaDon);
            }
            DSUntils.CloseConnect(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadCTHD(List<ChiTietHoaDon> chiTietHoaDons, int mahoadon){
        String query="SELECT * FROM chitiethd WHERE MaHD=?";
        try {
            Connection conn= DSUntils.DBConnect();
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1,mahoadon);
            ResultSet result = statement.executeQuery();

            while (result.next()){
                int maCTHD= result.getInt("MaCTHD");
                int maLH=result.getInt("MaLoaiHang");
                int maSP= result.getInt("MaSP");
                int sl= result.getInt("SoLuong");
                Float dongia= result.getFloat("DonGia");
                Float thanhtien=result.getFloat("ThanhTien");

                ChiTietHoaDon cthd= new ChiTietHoaDon(mahoadon,maLH,maSP,sl,dongia,thanhtien);
                cthd.setMaCTHD(maCTHD);
                chiTietHoaDons.add(cthd);
            }
            DSUntils.CloseConnect(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMenu(int maMenu) {
        String tenSP = "Không xác định";
        try {
            Connection conn = DSUntils.DBConnect();
            String query = "SELECT TenSP FROM menu WHERE MaSP = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, maMenu);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                tenSP = resultSet.getString("TenSP");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tenSP;
    }

    public String getNhanVien(int maNV) {
        String tenNV = "Đã nghỉ việc";
        try {
            Connection conn = DSUntils.DBConnect();
            String query = "SELECT TenNV FROM nhanvien WHERE MaNV = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, maNV);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                tenNV = resultSet.getString("TenNV");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tenNV;
    }
}
