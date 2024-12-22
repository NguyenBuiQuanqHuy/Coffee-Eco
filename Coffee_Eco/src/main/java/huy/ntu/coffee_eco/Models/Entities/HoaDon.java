package huy.ntu.coffee_eco.Models.Entities;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HoaDon {
    int maHD;
    int MaNV;
    Timestamp ngaylap;
    Float thanhtien;

    public HoaDon(int maNV, Timestamp ngaylap, Float thanhtien) {
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

    public Timestamp getNgaylap() {
        return ngaylap;
    }

    public void setNgaylap(Timestamp ngaylap) {
        this.ngaylap = ngaylap;
    }

    public Float getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(Float thanhtien) {
        this.thanhtien = thanhtien;
    }
}

