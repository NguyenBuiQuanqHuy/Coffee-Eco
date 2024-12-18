package huy.ntu.coffee_eco.Models.Entities;

public class ChiTietHoaDon {
    int maCTHD,maHD,maMenu;
    int soluong;
    double gia;

    public ChiTietHoaDon(int maHD, int maMenu, int soluong, double gia) {
        this.maHD = maHD;
        this.maMenu = maMenu;
        this.soluong = soluong;
        this.gia = gia;
    }

    public ChiTietHoaDon(int maCTHD, int maHD, int maMenu, int soluong, double gia) {
        this.maCTHD = maCTHD;
        this.maHD = maHD;
        this.maMenu = maMenu;
        this.soluong = soluong;
        this.gia = gia;
    }
}
