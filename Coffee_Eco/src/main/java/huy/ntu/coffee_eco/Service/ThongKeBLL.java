package huy.ntu.coffee_eco.Service;

import huy.ntu.coffee_eco.Models.Entities.ChiTietHoaDon;
import huy.ntu.coffee_eco.Models.Entities.HoaDon;
import huy.ntu.coffee_eco.Responsitories.ThongKeDAL;

import java.util.List;

public class ThongKeBLL {
    ThongKeDAL thongKeDAL = new ThongKeDAL();

    public void LoadHoaDon(List<HoaDon> hoaDons){
        thongKeDAL.loadHoaDon(hoaDons);
    }

    public void LoadCTHD(List<ChiTietHoaDon> chiTietHoaDons, int mahoadon){
        thongKeDAL.loadCTHD(chiTietHoaDons,mahoadon);
    }

    public String getNhanVien(int maNV){
        return thongKeDAL.getNhanVien(maNV);
    }

    public String getMenu(int maMenu){
        return thongKeDAL.getMenu(maMenu);
    }
}
