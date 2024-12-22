package huy.ntu.coffee_eco.Models.Entities;

public class ChiTietHoaDon {
    int maCTHD,maHD,maMenu,maLoai;
    int soluong;
    Float gia,thanhtien;

    public ChiTietHoaDon(int maHD, int maLoai, int maMenu, int soluong, Float gia, Float thanhtien) {
        this.maHD = maHD;
        this.maLoai = maLoai;
        this.maMenu = maMenu;
        this.soluong = soluong;
        this.gia = gia;
        this.thanhtien = thanhtien;
    }

    public int getMaCTHD() {
        return maCTHD;
    }

    public void setMaCTHD(int maCTHD) {
        this.maCTHD = maCTHD;
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

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public Float getGia() {
        return gia;
    }

    public void setGia(Float gia) {
        this.gia = gia;
    }

    public Float getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(Float thanhtien) {
        this.thanhtien = thanhtien;
    }
}
