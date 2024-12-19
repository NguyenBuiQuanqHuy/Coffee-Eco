package huy.ntu.coffee_eco.Models.Entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class HoaDon {
    int maHD;
    String MaNV;
    Date ngaylap;
    Double thanhtien;

    List<ChiTietHoaDon> cthd=new ArrayList<>();

    public HoaDon(String maNV, Date ngaylap, Double thanhtien) {
        MaNV = maNV;
        this.ngaylap = ngaylap;
        this.thanhtien = thanhtien;
    }


    public List<ChiTietHoaDon> getCthd() {
        return cthd;
    }

    public void setCthd(List<ChiTietHoaDon> cthd) {
        this.cthd = cthd;
    }

    public void addChiTietHoaDon(ChiTietHoaDon chiTiet) {
        this.cthd.add(chiTiet);
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

