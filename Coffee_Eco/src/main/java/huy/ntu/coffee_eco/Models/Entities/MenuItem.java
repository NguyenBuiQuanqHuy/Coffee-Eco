package huy.ntu.coffee_eco.Models.Entities;

public class MenuItem {
    private int id;
    private String loaiHang;
    private String tenHang;
    private float gia;
    private String hinhAnh;

    public MenuItem(String loaiHang, String tenHang, float gia, String hinhAnh) {
        this.loaiHang = loaiHang;
        this.tenHang = tenHang;
        this.gia = gia;
        this.hinhAnh = hinhAnh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoaiHang() {
        return loaiHang;
    }

    public void setLoaiHang(String loaiHang) {
        this.loaiHang = loaiHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
