package huy.ntu.coffee_eco.Service;

import Untils.ComonUntils;
import huy.ntu.coffee_eco.Models.Entities.NhanVien;
import huy.ntu.coffee_eco.Responsitories.NhanVienDAL;

public class NhanVienBLL {
    NhanVienDAL nhanVienDAO=new NhanVienDAL();

    public NhanVienBLL() {
        this.nhanVienDAO = nhanVienDAO;
    }

    public boolean themNV(NhanVien nv){
        String matkhauHash = ComonUntils.hashPassword(nv.getMatkhau());
       nv.setMatkhau(matkhauHash);
        boolean kq = nhanVienDAO.AddNhanVien(nv);
        return kq;
    }
    // Phương thức đăng nhập
//    public boolean dangNhap(String tenTaiKhoan, String matkhau) {
//        String matkhauHash = HashingPass.hashPassword(matkhau); // Mã hóa mật khẩu bằng MD5
//        return nhanVienDAO.Login(tenTaiKhoan, matkhauHash);
//    }
}
