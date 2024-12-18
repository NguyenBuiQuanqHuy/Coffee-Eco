package huy.ntu.coffee_eco.Service;

import huy.ntu.coffee_eco.Untils.ComonUntils;
import huy.ntu.coffee_eco.Models.Entities.NhanVien;
import huy.ntu.coffee_eco.Responsitories.NhanVienDAL;
import javafx.collections.ObservableList;

public class NhanVienBLL {
    NhanVienDAL nhanVienDAL=new NhanVienDAL();

    public NhanVienBLL() {
        this.nhanVienDAL = nhanVienDAL;
    }

    public boolean themNV(NhanVien nv){
        String matkhauHash = ComonUntils.hashPassword(nv.getMatkhau());
        nv.setMatkhau(matkhauHash);
        boolean kq = nhanVienDAL.AddNhanVien(nv);
        return kq;
    }

    public void loadNhanVienData(ObservableList<NhanVien> nhanVienList) {
        nhanVienDAL.LoadNhanVien(nhanVienList);
    }

    public void suaNV(NhanVien nv){
        nhanVienDAL.suaNV(nv);
    }
    public void xoaNV(String maNV) {
        nhanVienDAL.xoaNV(maNV);
    }

//    public void dangNhap(String tenTaiKhoan, String matkhau) {
//        String matkhauHash = ComonUntils.hashPassword(matkhau);
//        nhanVienDAL.Login(tenTaiKhoan, matkhauHash);
//    }

}
