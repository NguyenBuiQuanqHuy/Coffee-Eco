package huy.ntu.coffee_eco.Models.Entities;

public class NhanVien {
    public int maNV;
    public String ten;
    public String diachi;
    public String gioitinh;
    public String dienthoai;
    public String taikhoan;
    public String matkhau;
    public float luong;

    public NhanVien() {
    }

    public NhanVien(String ten, String diachi, String gioitinh, String dienthoai, float luong, String taikhoan, String matkhau) {
        this.ten = ten;
        this.diachi = diachi;
        this.gioitinh = gioitinh;
        this.dienthoai = dienthoai;
        this.luong = luong;
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
    }

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
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

    public float getLuong() {
        return luong;
    }

    public void setLuong(float luong) {
        this.luong = luong;
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
