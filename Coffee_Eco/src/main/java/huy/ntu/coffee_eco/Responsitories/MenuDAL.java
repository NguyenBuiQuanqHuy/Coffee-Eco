package huy.ntu.coffee_eco.Responsitories;

import huy.ntu.coffee_eco.Models.Entities.LoaiHang;
import huy.ntu.coffee_eco.Models.Entities.MenuItem;
import huy.ntu.coffee_eco.Untils.DSUntils;
import javafx.collections.ObservableList;

import java.sql.*;

public class MenuDAL {
    public void loadMenu(ObservableList<MenuItem> menuList) {
        try {
            Connection conn = DSUntils.DBConnect();
            String query = "SELECT * FROM menu";
            PreparedStatement statement= conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("MaSP");
                int loaiHang = resultSet.getInt("LoaiHang");
                String tenHang = resultSet.getString("TenSP");
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
            Connection conn = DSUntils.DBConnect();
            String sql = "INSERT INTO menu (LoaiHang, TenSP, Gia, HinhAnh) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, menuItem.getLoaiHang());
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

    public void updateMenu(MenuItem menuItem){
        String sql= "UPDATE menu SET LoaiHang=?,TenSP=?, Gia=?, HinhAnh=? WHERE MaSP=?";
        try {
            Connection conn = DSUntils.DBConnect();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,menuItem.getLoaiHang());
            statement.setString(2,menuItem.getTenHang());
            statement.setFloat(3,menuItem.getGia());
            statement.setString(4, menuItem.getHinhAnh());
            statement.setInt(5,menuItem.getId());
            statement.executeUpdate();
            DSUntils.CloseConnect(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteMenu(int menuId) {
        try {
            Connection conn = DSUntils.DBConnect();
            String sql = "DELETE FROM menu WHERE MaSP = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, menuId);
            statement.executeUpdate();
            DSUntils.CloseConnect(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
