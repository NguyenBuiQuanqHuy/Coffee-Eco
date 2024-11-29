package huy.ntu.coffee_eco.ThreeLayer;

import huy.ntu.coffee_eco.HashingPass;

public class NhanVienBLL {
    NhanVienDAO nhanVienDAO=new NhanVienDAO();

    public NhanVienBLL() {
        this.nhanVienDAO = nhanVienDAO;
    }

    public boolean themNV(NhanVien nv){
        String matkhauHash = HashingPass.hashPassword(nv.getMatkhau());
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
