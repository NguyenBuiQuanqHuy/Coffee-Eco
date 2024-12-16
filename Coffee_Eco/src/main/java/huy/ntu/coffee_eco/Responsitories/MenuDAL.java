package huy.ntu.coffee_eco.Responsitories;

import huy.ntu.coffee_eco.Models.Entities.LoaiHang;
import huy.ntu.coffee_eco.Models.Entities.MenuItem;
import huy.ntu.coffee_eco.Untils.DSUntils;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class MenuDAL {
    private Map<String, Integer> loaiHangMap = new HashMap<>();
    public void loadMenu(ObservableList<MenuItem> menuList) {
        try {
            Connection conn = DSUntils.DBConnect();
            String query = "SELECT m.MaHang, m.TenHang, l.TenLoaiHang, m.Gia, m.HinhAnh " +
                    "FROM menu m " +
                    "JOIN loaihang l ON m.LoaiHang = l.MaLoaiHang;";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("MaHang");
                String loaiHang = resultSet.getString("TenLoaiHang");
                String tenHang = resultSet.getString("TenHang");
                Float giaHang = resultSet.getFloat("Gia");
                String hinh = resultSet.getString("HinhAnh");

                MenuItem menu=new MenuItem(loaiHang,tenHang,giaHang,hinh);
                menu.setId(id);
                menuList.add(menu);
            }
            DSUntils.CloseConnect(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadLoaiHang(ObservableList<LoaiHang> loaiHangList){
        try {
            Connection conn = DSUntils.DBConnect();
            String sql = "SELECT * FROM LoaiHang";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int maLoaiHang = rs.getInt("MaLoaiHang");
                String tenLoai = rs.getString("TenLoaiHang");
                loaiHangList.add(new LoaiHang(maLoaiHang, tenLoai));
                loaiHangMap.put(tenLoai, maLoaiHang);
                loaiHangMap.put(tenLoai, maLoaiHang);
            }
        DSUntils.CloseConnect(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMenu(MenuItem menuItem) {
        try {
            int maLoaiHang = loaiHangMap.getOrDefault(menuItem.getLoaiHang(), -1);
            Connection conn = DSUntils.DBConnect();
            String sql = "INSERT INTO menu (LoaiHang, TenHang, Gia, HinhAnh) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, maLoaiHang);
            statement.setString(2, menuItem.getTenHang());
            statement.setFloat(3, menuItem.getGia());
            statement.setString(4, menuItem.getHinhAnh());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys != null && generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                menuItem.setId(generatedId);
            }

            DSUntils.CloseConnect(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteMenu(int menuId) {
        try {
            Connection conn = DSUntils.DBConnect();
            String sql = "DELETE FROM menu WHERE MaHang = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, menuId);
            statement.executeUpdate();
            DSUntils.CloseConnect(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
