package huy.ntu.coffee_eco.Controller;

import huy.ntu.coffee_eco.HelloApplication;
import huy.ntu.coffee_eco.Models.Entities.CurrentUser;
import huy.ntu.coffee_eco.Models.Entities.TaiKhoan;
import huy.ntu.coffee_eco.Service.TaiKhoanBLL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class TaiKhoanController {

    @FXML
    TextField textFieldTK;
    @FXML
    PasswordField matKhau;
    @FXML
    CheckBox checkBoxMK;
    @FXML
    TextField textFieldMK;

    TaiKhoanBLL taiKhoanBLL = new TaiKhoanBLL();


    public void initialize() {
        textFieldMK.setVisible(false);

        checkBoxMK.setOnAction(event -> {
            if (checkBoxMK.isSelected()) {
                matKhau.setVisible(false);
                textFieldMK.setVisible(true);
                textFieldMK.setText(matKhau.getText());
                //textFieldMK.requestFocus();
            } else {
                matKhau.setVisible(true);
                textFieldMK.setVisible(false);
                matKhau.setText(textFieldMK.getText());
            }
        });
        textFieldMK.textProperty().addListener((observable, oldValue, newValue) -> {
            matKhau.setText(newValue);
        });
    }


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
                CurrentUser.setCurrentUser(taiKhoan);
                DangNhapThanhCong();
            } else {
                showAlert("Sai tài khoản hoặc mật khẩu", Alert.AlertType.ERROR);
            }
    }
    private void DangNhapThanhCong() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/thanhtoan-view.fxml"));
        Scene thanhToanScene = new Scene(loader.load());
        Stage currentStage = (Stage) textFieldTK.getScene().getWindow();
        currentStage.setScene(thanhToanScene);
        currentStage.show();
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Thông Báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
