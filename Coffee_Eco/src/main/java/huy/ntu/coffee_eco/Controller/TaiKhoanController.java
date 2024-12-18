package huy.ntu.coffee_eco.Controller;

import huy.ntu.coffee_eco.HelloApplication;
import huy.ntu.coffee_eco.Models.Entities.TaiKhoan;
import huy.ntu.coffee_eco.Service.TaiKhoanBLL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class TaiKhoanController {

    @FXML
    TextField textFieldTK;
    @FXML
    PasswordField matKhau;

    TaiKhoanBLL taiKhoanBLL = new TaiKhoanBLL();
    public void handleDangNhap() throws IOException {
        String taikhoan = textFieldTK.getText().trim();
        String matkhau = matKhau.getText().trim();

        if (taikhoan.isEmpty() || matkhau.isEmpty()) {
            showAlert("Tên tài khoản hoặc mật khẩu không được để trống.", Alert.AlertType.WARNING);
            return;
        }
        TaiKhoan taiKhoan = new TaiKhoan(taikhoan, matkhau);
        boolean isSuccess = taiKhoanBLL.DangNhap(taiKhoan);
            if (isSuccess) {
                showAlert("Đăng nhập thành công", Alert.AlertType.INFORMATION);
                DangNhapThanhCong();
            } else {
                showAlert("Sai tài khoản hoặc mật khẩu", Alert.AlertType.ERROR);
            }
    }
    private void DangNhapThanhCong() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/thanhtoan-view.fxml"));
        Scene thanhToanScene = new Scene(loader.load());
        Stage currentStage = (Stage) textFieldTK.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.setScene(thanhToanScene);
        newStage.initStyle(StageStyle.UNDECORATED);
        currentStage.close();
        newStage.show();
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Thông Báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
