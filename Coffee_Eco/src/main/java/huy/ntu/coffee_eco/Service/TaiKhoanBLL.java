package huy.ntu.coffee_eco.Service;

import huy.ntu.coffee_eco.Models.Entities.TaiKhoan;
import huy.ntu.coffee_eco.Responsitories.TaiKhoanDAL;

public class TaiKhoanBLL {
    TaiKhoanDAL taiKhoanDAL=new TaiKhoanDAL();

    public boolean DangNhap(TaiKhoan taiKhoan){
        return taiKhoanDAL.dangNhap(taiKhoan);
    }
}
