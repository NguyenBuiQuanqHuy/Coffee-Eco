package huy.ntu.coffee_eco.Models.Entities;

public class LoaiHang {
    int maloaihang;
    String tenloaihang;

    public LoaiHang(int maloaihang, String tenloaihang) {
        this.maloaihang = maloaihang;
        this.tenloaihang = tenloaihang;
    }

    public LoaiHang() {
    }

    public int getMaloaihang() {
        return maloaihang;
    }

    public void setMaloaihang(int maloaihang) {
        this.maloaihang = maloaihang;
    }

    public String getTenloaihang() {
        return tenloaihang;
    }

    public void setTenloaihang(String tenloaihang) {
        this.tenloaihang = tenloaihang;
    }

    @Override
    public String toString() {
        return tenloaihang;
    }
}
