package huy.ntu.coffee_eco.Models.Entities;

public class ChiTietHoaDon {
    int maCTHD,maHD,maMenu,maLoai;
    int soluong;
    double gia;

    public ChiTietHoaDon(int maHD, int maLoai, int maMenu, int soluong, double gia) {
        this.maHD = maHD;
        this.maLoai = maLoai;
        this.maMenu = maMenu;
        this.soluong = soluong;
        this.gia = gia;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaMenu() {
        return maMenu;
    }

    public void setMaMenu(int maMenu) {
        this.maMenu = maMenu;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }
}
