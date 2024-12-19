package huy.ntu.coffee_eco.Responsitories;

import huy.ntu.coffee_eco.Models.Entities.LoaiHang;
import huy.ntu.coffee_eco.Models.Entities.MenuItem;
import huy.ntu.coffee_eco.Untils.DSUntils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            conn.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

}
