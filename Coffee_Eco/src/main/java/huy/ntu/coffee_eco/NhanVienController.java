package huy.ntu.coffee_eco;

import huy.ntu.coffee_eco.ThreeLayer.NhanVien;
import huy.ntu.coffee_eco.ThreeLayer.NhanVienBLL;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class NhanVienController {

    @FXML
    private TextField txtHoten, txtDienthoai, txtDiachi, txtTaikhoan, txtMatkhau;

    @FXML
    private RadioButton RadiobuttonNam, RadiobuttonNu;

    private final NhanVienBLL nhanVienBLL;

    // Tạo ToggleGroup trong mã
    private ToggleGroup ToggleGroupGioiTinh;

    public NhanVienController() {
        nhanVienBLL = new NhanVienBLL();
    }

    public void initialize() {
        // Tạo ToggleGroup mới và gán cho các RadioButton
        ToggleGroupGioiTinh = new ToggleGroup();
        RadiobuttonNam.setToggleGroup(ToggleGroupGioiTinh);
        RadiobuttonNu.setToggleGroup(ToggleGroupGioiTinh);
    }

    @FXML
    public void AddButton() {
        // Kiểm tra các trường đầu vào
        String ten = txtHoten.getText().trim();
        String diachi = txtDiachi.getText().trim();
        String dienthoai = txtDienthoai.getText().trim();
        String tenTK = txtTaikhoan.getText().trim();
        String matkhau = txtMatkhau.getText().trim();

        // Kiểm tra xem các trường có bị bỏ trống không
        if (ten.isEmpty() || diachi.isEmpty() || dienthoai.isEmpty() || tenTK.isEmpty() || matkhau.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng điền đầy đủ thông tin!", ButtonType.OK);
            alert.show();
            return;
        }

        // Kiểm tra giới tính
        String gioitinh = "";
        if (RadiobuttonNam.isSelected()) {
            gioitinh = "Nam";
        } else if (RadiobuttonNu.isSelected()) {
            gioitinh = "Nữ";
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chọn giới tính!", ButtonType.OK);
            alert.show();
            return;
        }

        // Tạo đối tượng NhanVien
        NhanVien nv = new NhanVien(ten, diachi, gioitinh, dienthoai, tenTK, matkhau);

        // Thêm nhân viên vào cơ sở dữ liệu
        boolean result = nhanVienBLL.themNV(nv);
        if (result) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Thêm nhân viên thành công", ButtonType.OK);
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Thêm nhân viên thất bại", ButtonType.OK);
            alert.show();
        }
    }

    public void EditButton(){

    }

    public void DeleteButton(){

    }
}
