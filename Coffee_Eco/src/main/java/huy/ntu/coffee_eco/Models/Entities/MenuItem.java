package huy.ntu.coffee_eco.Models.Entities;

public class MenuItem {
    private int id;
    private int loaiHang;
    private String tenHang;
    private float gia;
    private String hinhAnh;
    private String tenLoaiHang;

    public MenuItem(int loaiHang, String tenHang, float gia, String hinhAnh) {
        this.loaiHang = loaiHang;
        this.tenHang = tenHang;
        this.gia = gia;
        this.hinhAnh = hinhAnh;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getLoaiHang() { return loaiHang; }

    public void setLoaiHang(int loaiHang) { this.loaiHang = loaiHang; }

    public String getTenHang() { return tenHang; }

    public void setTenHang(String tenHang) { this.tenHang = tenHang; }

    public float getGia() { return gia; }

    public void setGia(float gia) { this.gia = gia; }

    public String getHinhAnh() { return hinhAnh; }

    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }

    @Override
    public String toString() {
        return tenHang;
    }
}
