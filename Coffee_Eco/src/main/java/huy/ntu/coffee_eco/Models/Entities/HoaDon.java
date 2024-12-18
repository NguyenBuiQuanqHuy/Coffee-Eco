package huy.ntu.coffee_eco.Models.Entities;

import java.sql.Date;

public class HoaDon {
    int maHD;
    String MaNV;
    Date ngaylap;
    Double thanhtien;

    public HoaDon(String maNV, Date ngaylap, Double thanhtien) {
        MaNV = maNV;
        this.ngaylap = ngaylap;
        this.thanhtien = thanhtien;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public Date getNgaylap() {
        return ngaylap;
    }

    public void setNgaylap(Date ngaylap) {
        this.ngaylap = ngaylap;
    }

    public Double getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(Double thanhtien) {
        this.thanhtien = thanhtien;
    }
}

