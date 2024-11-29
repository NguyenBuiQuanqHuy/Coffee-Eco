package huy.ntu.coffee_eco.Models.Entities;

public class NhanVien {
    private int id;
    private String ten;
    private String diachi;
    private String gioitinh;
    private String dienthoai;
    private String taikhoan;
    private String matkhau;

    public NhanVien(String ten, String diachi, String gioitinh, String dienthoai, String taikhoan, String matkhau) {
        this.ten = ten;
        this.diachi = diachi;
        this.gioitinh = gioitinh;
        this.dienthoai = dienthoai;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
    }

    public NhanVien() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getDienthoai() {
        return dienthoai;
    }

    public void setDienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }
}
