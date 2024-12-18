package huy.ntu.coffee_eco.Responsitories;

import huy.ntu.coffee_eco.Models.Entities.TaiKhoan;
import huy.ntu.coffee_eco.Untils.ComonUntils;
import huy.ntu.coffee_eco.Untils.DSUntils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaiKhoanDAL {
    public boolean dangNhap(TaiKhoan taiKhoan){
        try{
            Connection conn = DSUntils.DBConnect();
            String hashedPassword =ComonUntils.hashPassword(taiKhoan.getMatkhau());
            String sql = "SELECT * FROM nhanvien WHERE TenTK = ? AND MatKhau = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, taiKhoan.getTenTK());
            statement.setString(2,hashedPassword);
            statement.executeQuery();
            DSUntils.CloseConnect(conn);
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
