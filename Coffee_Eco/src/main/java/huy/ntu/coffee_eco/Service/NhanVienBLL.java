package huy.ntu.coffee_eco.Service;

import huy.ntu.coffee_eco.Untils.ComonUntils;
import huy.ntu.coffee_eco.Models.Entities.NhanVien;
import huy.ntu.coffee_eco.Responsitories.NhanVienDAL;
import javafx.collections.ObservableList;

public class NhanVienBLL {
    NhanVienDAL nhanVienDAL=new NhanVienDAL();

    public void themNV(NhanVien nv){
        String matkhauHash = ComonUntils.hashPassword(nv.getMatkhau());
        nv.setMatkhau(matkhauHash);
        nhanVienDAL.AddNhanVien(nv);
    }

    public void loadNhanVienData(ObservableList<NhanVien> nhanVienList) {
        nhanVienDAL.LoadNhanVien(nhanVienList);
    }

    public void suaNV(NhanVien nv){
        String matkhauHash = ComonUntils.hashPassword(nv.getMatkhau());
        nv.setMatkhau(matkhauHash);
        nhanVienDAL.EditNhanVien(nv);
    }
    public void xoaNV(int maNV) {
        nhanVienDAL.DeleteNhanVien(maNV);
    }
}
