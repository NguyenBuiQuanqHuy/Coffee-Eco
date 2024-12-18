package huy.ntu.coffee_eco.Service;

import huy.ntu.coffee_eco.Models.Entities.LoaiHang;
import huy.ntu.coffee_eco.Models.Entities.MenuItem;
import huy.ntu.coffee_eco.Responsitories.HoaDonDAL;

import java.util.List;

public class HoaDonBLL {
    HoaDonDAL hoaDonDAL=new HoaDonDAL();

    public void LoadLoaiHang(List<LoaiHang> loaiHangs){
        hoaDonDAL.loadLoaiHang(loaiHangs);
    }

    public void LoadMenu(List<MenuItem> menuItems,int maLoaiHang){
        hoaDonDAL.loadMenu(menuItems,maLoaiHang);
    }
}
