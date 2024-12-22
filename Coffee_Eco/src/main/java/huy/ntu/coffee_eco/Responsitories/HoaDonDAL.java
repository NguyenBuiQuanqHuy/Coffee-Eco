package huy.ntu.coffee_eco.Responsitories;

import huy.ntu.coffee_eco.Models.Entities.ChiTietHoaDon;
import huy.ntu.coffee_eco.Models.Entities.HoaDon;
import huy.ntu.coffee_eco.Models.Entities.LoaiHang;
import huy.ntu.coffee_eco.Models.Entities.MenuItem;
import huy.ntu.coffee_eco.Untils.DSUntils;

import java.sql.*;
import java.util.List;

public class HoaDonDAL {
    public void loadLoaiHang(List<LoaiHang> loaiHangs){
        String sql = "SELECT MaLoaiHang, TenLoaiHang FROM loaihang";
        try {
            Connection conn = DSUntils.DBConnect();
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int maloaihang = resultSet.getInt("MaLoaiHang");
                String tenloaihang = resultSet.getString("TenLoaiHang");
                loaiHangs.add(new LoaiHang(maloaihang, tenloaihang));
            }
            DSUntils.CloseConnect(conn);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTenLoaiHang(int maLoaiHang) {
        String tenLoaiHang = "Không xác định";
        try {
            Connection conn = DSUntils.DBConnect();
            String query = "SELECT TenLoaiHang FROM loaihang WHERE MaLoaiHang = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, maLoaiHang);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                tenLoaiHang = resultSet.getString("TenLoaiHang");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tenLoaiHang;
    }

    public void loadMenu(List<MenuItem> menuItems,int maLoaiHang){
        String query = "SELECT MaSP, TenSP, Gia, HinhAnh FROM menu WHERE LoaiHang = ?";
        try {
            Connection conn=DSUntils.DBConnect();
            PreparedStatement statement= conn.prepareStatement(query);
            statement.setInt(1,maLoaiHang);
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("MaSP");
                String tenHang = resultSet.getString("TenSP");
                float gia = resultSet.getFloat("Gia");
                String hinhAnh = resultSet.getString("HinhAnh");
                MenuItem item = new MenuItem(maLoaiHang, tenHang, gia, hinhAnh);
                item.setId(id);
                menuItems.add(item);
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


    public int themHoaDon(HoaDon hoaDon) {
        String query = "INSERT INTO hoadon (MaNV, NgayTao, TongTien ) VALUES (?, ?,?)";
        try {
            Connection conn=DSUntils.DBConnect();
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, hoaDon.getMaNV());
            statement.setTimestamp(2, hoaDon.getNgaylap());
            statement.setDouble(3,hoaDon.getThanhtien());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            else return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void themCTHD(ChiTietHoaDon cthd) {
        String query = "INSERT INTO chitiethd (MaHD, MaLoaiHang, MaSP, SoLuong, DonGia, ThanhTien) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection conn=DSUntils.DBConnect();
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, cthd.getMaHD());
            statement.setInt(2,cthd.getMaLoai());
            statement.setInt(3,cthd.getMaMenu());
            statement.setInt(4,cthd.getSoluong());
            statement.setFloat(5,cthd.getGia());
            statement.setFloat(6,cthd.getThanhtien());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
