package huy.ntu.coffee_eco.Models.Entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class HoaDon {
    int maHD;
    int MaNV;
    Date ngaylap;
    Double thanhtien;

    public HoaDon(int maNV, Date ngaylap, Double thanhtien) {
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

    public int getMaNV() {
        return MaNV;
    }

    public void setMaNV(int maNV) {
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

