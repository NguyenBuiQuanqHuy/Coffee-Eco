package huy.ntu.coffee_eco.Models.Entities;

public class TaiKhoan {
    int id;
    String tenTK,matkhau;

    public TaiKhoan() {
    }

    public TaiKhoan(String tenTK, String matkhau) {
        this.tenTK = tenTK;
        this.matkhau = matkhau;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenTK() {
        return tenTK;
    }

    public void setTenTK(String tenTK) {
        this.tenTK = tenTK;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }
}