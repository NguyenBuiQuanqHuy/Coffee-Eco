package huy.ntu.coffee_eco.Controller;

import huy.ntu.coffee_eco.Models.Entities.NhanVien;
import huy.ntu.coffee_eco.Service.NhanVienBLL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.text.DecimalFormat;

public class NhanVienController {

    @FXML
    private TextField txtHoten, txtDienthoai, txtDiachi, txtTaikhoan, txtMatkhau, txtLuong;

    @FXML
    private RadioButton RadiobuttonNam, RadiobuttonNu;

    @FXML
    private TableView<NhanVien> tableNhanVien;

    @FXML
    private TableColumn<NhanVien, String> colHoten, colDiachi, colGioitinh, colDienthoai;

    @FXML
    private TableColumn<NhanVien, Float> colLuong;

    private NhanVienBLL nhanVienBLL = new NhanVienBLL();
    private ObservableList<NhanVien> nhanVienList;

    @FXML
    private ToggleGroup ToggleGroupGioiTinh;

    public void initialize() {
        ToggleGroupGioiTinh = new ToggleGroup();
        RadiobuttonNam.setToggleGroup(ToggleGroupGioiTinh);
        RadiobuttonNu.setToggleGroup(ToggleGroupGioiTinh);

        nhanVienList = FXCollections.observableArrayList();
        colHoten.setCellValueFactory(new PropertyValueFactory<>("ten"));
        colDiachi.setCellValueFactory(new PropertyValueFactory<>("diachi"));
        colGioitinh.setCellValueFactory(new PropertyValueFactory<>("gioitinh"));
        colDienthoai.setCellValueFactory(new PropertyValueFactory<>("dienthoai"));
        colLuong.setCellValueFactory(new PropertyValueFactory<>("luong"));
        colLuong.setCellFactory(column -> new TableCell<>() {
            private final DecimalFormat format = new DecimalFormat("#,###.###");

            @Override
            protected void updateItem(Float gia, boolean empty) {
                super.updateItem(gia, empty);
                if (empty || gia == null) {
                    setText(null);
                } else {
                    setText(format.format(gia));
                }
            }
        });

        loadNhanVienData();
        tableNhanVien.setItems(nhanVienList);

    }

    private void loadNhanVienData() {
        nhanVienBLL.loadNhanVienData(nhanVienList);
    }

    @FXML
    public void AddButton() {
        String ten = txtHoten.getText().trim();
        String diachi = txtDiachi.getText().trim();
        String dienthoai = txtDienthoai.getText().trim();
        String tenTK = txtTaikhoan.getText().trim();
        String matkhau = txtMatkhau.getText().trim();
        Float luong;


        if (ten.isEmpty() || diachi.isEmpty() || dienthoai.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng điền đầy đủ thông tin!", ButtonType.OK);
            alert.show();
            return;
        }

        try {
            luong = Float.parseFloat(txtLuong.getText().trim());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Lương phải là một số hợp lệ!", ButtonType.OK);
            alert.show();
            return;
        }



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

        boolean taiKhoanTonTai = nhanVienList.stream().anyMatch(nv -> nv.getTaikhoan().equalsIgnoreCase(tenTK));
        if (taiKhoanTonTai) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Tên tài khoản đã tồn tại, vui lòng chọn tài khoản khác!", ButtonType.OK);
            alert.show();
            return;
        }

        NhanVien nv = new NhanVien(ten, diachi, gioitinh, dienthoai, luong, tenTK, matkhau);
        boolean result = nhanVienBLL.themNV(nv);
        if (result) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Thêm nhân viên thành công", ButtonType.OK);
            alert.show();
            nhanVienList.add(nv);
            tableNhanVien.setItems(nhanVienList);
            tableNhanVien.refresh();
            txtHoten.clear();
            txtDiachi.clear();
            txtDienthoai.clear();
            txtLuong.clear();
            txtTaikhoan.clear();
            txtMatkhau.clear();
            ToggleGroupGioiTinh.getSelectedToggle().setSelected(false);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Thêm nhân viên thất bại", ButtonType.OK);
            alert.show();
        }
    }


    public void EditButton() {
        NhanVien selectedNhanVien = tableNhanVien.getSelectionModel().getSelectedItem();
        if (selectedNhanVien == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Vui lòng chọn nhân viên để sửa!", ButtonType.OK);
            alert.show();
            return;
        }

        String ten = txtHoten.getText().trim();
        String diachi = txtDiachi.getText().trim();
        String dienthoai = txtDienthoai.getText().trim();
        String tenTK = txtTaikhoan.getText().trim();
        String matkhau = txtMatkhau.getText().trim();
        Float luong;

        try {
            luong = Float.parseFloat(txtLuong.getText().trim());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Lương phải là một số hợp lệ!", ButtonType.OK);
            alert.show();
            return;
        }

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

        selectedNhanVien.setTen(ten);
        selectedNhanVien.setDiachi(diachi);
        selectedNhanVien.setDienthoai(dienthoai);
        selectedNhanVien.setLuong(luong);
        selectedNhanVien.setGioitinh(gioitinh);
        selectedNhanVien.setTaikhoan(tenTK);
        selectedNhanVien.setMatkhau(matkhau);
        try {
            nhanVienBLL.suaNV(selectedNhanVien);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Sửa nhân viên thành công", ButtonType.OK);
            alert.show();
            tableNhanVien.refresh();
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Sửa nhân viên thất bại", ButtonType.OK);
            alert.show();
        }
    }


    public void DeleteButton() {
        NhanVien selectedNhanVien = tableNhanVien.getSelectionModel().getSelectedItem();
        if (selectedNhanVien == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Vui lòng chọn nhân viên để xóa!", ButtonType.OK);
            alert.show();
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc chắn muốn xóa nhân viên này?", ButtonType.YES, ButtonType.NO);
        confirmAlert.showAndWait();

        if (confirmAlert.getResult() == ButtonType.YES) {
            try {
                nhanVienBLL.xoaNV(selectedNhanVien.getMaNV());
                nhanVienList.remove(selectedNhanVien);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Xóa nhân viên thành công", ButtonType.OK);
                alert.show();
                tableNhanVien.refresh();
            } catch (RuntimeException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Xóa nhân viên thất bại", ButtonType.OK);
                alert.show();
            }
        }
    }

    public void ExitButton(){
        Stage stage = (Stage) txtLuong.getScene().getWindow();
        stage.close();
    }
}
