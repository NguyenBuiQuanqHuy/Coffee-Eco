package huy.ntu.coffee_eco.Service;

import huy.ntu.coffee_eco.Models.Entities.ChiTietHoaDon;
import huy.ntu.coffee_eco.Models.Entities.HoaDon;
import huy.ntu.coffee_eco.Models.Entities.LoaiHang;
import huy.ntu.coffee_eco.Models.Entities.MenuItem;
import huy.ntu.coffee_eco.Responsitories.HoaDonDAL;

import java.util.List;

public class HoaDonBLL {
    HoaDonDAL hoaDonDAL=new HoaDonDAL();

    public void LoadLoaiHang(List<LoaiHang> loaiHangs){
        hoaDonDAL.loadLoaiHang(loaiHangs);
    }

    public String getTenLoaiHang(int maloaihang){
        return hoaDonDAL.getTenLoaiHang(maloaihang);
    }

    public void LoadMenu(List<MenuItem> menuItems,int maLoaiHang){
        hoaDonDAL.loadMenu(menuItems,maLoaiHang);
    }
    public String getMenu(int maMenu){
        return hoaDonDAL.getMenu(maMenu);
    }

    public int ThemHoadon(HoaDon hoaDon){
        return hoaDonDAL.themHoaDon(hoaDon);
    }

    public void ThemCTHD(ChiTietHoaDon cthd){
        hoaDonDAL.themCTHD(cthd);
    }
}
