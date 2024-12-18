package huy.ntu.coffee_eco.Controller;

import huy.ntu.coffee_eco.HelloApplication;
import huy.ntu.coffee_eco.Models.Entities.LoaiHang;
import huy.ntu.coffee_eco.Models.Entities.MenuItem;
import huy.ntu.coffee_eco.Service.HoaDonBLL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HoaDonController {
    @FXML
    TextField textFieldSoluong;
    @FXML
    Label labelUser;
    @FXML
    ComboBox<LoaiHang> comboBoxLoai;
    @FXML
    ComboBox<MenuItem> comboBoxMenu;
    @FXML
    Text textGia,textThanhTien;
    @FXML
    ImageView imageSP;

    HoaDonBLL hoaDonBLL=new HoaDonBLL();

    public void initialize() {
        ObservableList<LoaiHang> loaiHangs = FXCollections.observableArrayList();
        hoaDonBLL.LoadLoaiHang(loaiHangs); // Lấy dữ liệu từ BLL
        comboBoxLoai.setItems(loaiHangs);
        comboBoxLoai.setOnAction(event -> {
            LoaiHang selectedLoaiHang = comboBoxLoai.getValue();
            if (selectedLoaiHang != null) {
                ObservableList<MenuItem> menuItems = FXCollections.observableArrayList();
                hoaDonBLL.LoadMenu(menuItems, selectedLoaiHang.getMaloaihang());
                comboBoxMenu.setItems(menuItems);
            }
        });

        comboBoxMenu.setOnAction(event -> {
            MenuItem selectedMenuItem = comboBoxMenu.getValue();
            if (selectedMenuItem != null) {
                textGia.setText(String.format("%,.0f", selectedMenuItem.getGia()));
                String imagePath = selectedMenuItem.getHinhAnh();
                String imagePathInResources = "/huy/ntu/coffee_eco/images/" + imagePath;
                Image image = new Image(getClass().getResourceAsStream(imagePathInResources));
                imageSP.setImage(image);
            }
        });
    }


    public void LogOut() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận Đăng xuất");
        alert.setHeaderText("Bạn có chắc chắn muốn đăng xuất?");
        alert.setContentText("Chọn 'OK' để đăng xuất hoặc 'Hủy' để tiếp tục sử dụng ứng dụng.");

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (result == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/login-view.fxml"));
            Scene loginScene = new Scene(loader.load());
            Stage currentStage = (Stage) comboBoxLoai.getScene().getWindow();
            Stage newStage = new Stage();
            newStage.setScene(loginScene);
            newStage.initStyle(StageStyle.UNDECORATED);
            currentStage.close();
            newStage.show();
        }
    }

    public void handleThemSP(){

    }

    public void minusSoluong(){

    }

    public void plusSoluong(){

    }

    public void handleThanhToan(){

    }

    public void ThongKe(){

    }

    public void QuanLyMenu(){

    }

    public void QuanLyNhanVien(){

    }
}
