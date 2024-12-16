package huy.ntu.coffee_eco.Controller;

import huy.ntu.coffee_eco.Models.Entities.LoaiHang;
import huy.ntu.coffee_eco.Service.MenuBLL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import huy.ntu.coffee_eco.Models.Entities.MenuItem;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;

public class MenuController {

    @FXML
    ComboBox<LoaiHang> comboBoxLoai;
    @FXML
    TextField textFieldName,textFieldGia;
    @FXML
    TableView<MenuItem> tableViewMenu;
    @FXML
    TableColumn<MenuItem,String> colLoaiSP;
    @FXML
    TableColumn<MenuItem,Float> colGiaSP;
    @FXML
    TableColumn<MenuItem,String> colTenSP;
    @FXML
    ImageView imageSanPham;
    String selectedImagePath = null;
    String savedImagePath = null;

   MenuBLL menuBLL=new MenuBLL();
   ObservableList<MenuItem>  menuList;
   ObservableList<LoaiHang> loaiHangList;

    public void initialize() {
        menuList = FXCollections.observableArrayList();
        loaiHangList=FXCollections.observableArrayList();


        colTenSP.setCellValueFactory(new PropertyValueFactory<>("tenHang"));
        colLoaiSP.setCellValueFactory(new PropertyValueFactory<>("loaiHang"));
        colGiaSP.setCellValueFactory(new PropertyValueFactory<>("gia"));

        colGiaSP.setCellFactory(column -> new TableCell<>() {
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

        menuBLL.loadDataMenu(menuList);
        tableViewMenu.setItems(menuList);

        menuBLL.loadLoaiHang(loaiHangList);
        comboBoxLoai.setItems(loaiHangList);
    }

    public void ChonAnh(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Hình ảnh", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(null);
            selectedImagePath = file.getAbsolutePath();
            try {
                File destDir = new File("src/main/resources/huy/ntu/coffee_eco/images/");
                String imageName = file.getName();
                savedImagePath =imageName;
                Path sourcePath = file.toPath();
                Path destinationPath = destDir.toPath().resolve(imageName);
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                Image image = new Image(destinationPath.toUri().toString());
                imageSanPham.setImage(image);
        } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    public void Them(){
            String tenHang = textFieldName.getText().trim();
            String giaStr = textFieldGia.getText().trim();
            LoaiHang loaiHang = comboBoxLoai.getValue();
            if (tenHang.isEmpty() || giaStr.isEmpty() || loaiHang == null || savedImagePath == null) {
                showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin và chọn ảnh!", Alert.AlertType.ERROR);
                return;
            }
            float gia;
            try {
                gia = Float.parseFloat(giaStr);
            } catch (NumberFormatException e) {
                showAlert("Lỗi", "Giá phải là số!", Alert.AlertType.ERROR);
                return;
            }

            MenuItem menuItem = new MenuItem(loaiHang.getTenloaihang(), tenHang, gia, savedImagePath);
            menuList.add(menuItem);
            menuBLL.AddMenu(menuItem);
            tableViewMenu.refresh();
                showAlert("Thành công", "Thêm món thành công!", Alert.AlertType.INFORMATION);
                textFieldName.clear();
                textFieldGia.clear();
                comboBoxLoai.getSelectionModel().clearSelection();
                imageSanPham.setImage(null);
                savedImagePath = null;
    }

    public void CapNhat(){

    }

    public void Xoa() {
        MenuItem selectedItem = tableViewMenu.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Vui lòng chọn món cần xóa!", ButtonType.OK);
            alert.show();
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc chắn muốn xóa món này?", ButtonType.YES, ButtonType.NO);
        confirmAlert.showAndWait();

        if (confirmAlert.getResult() == ButtonType.YES) {
                menuBLL.DeleteMenu(selectedItem.getId());
                menuList.remove(selectedItem);
                tableViewMenu.refresh();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Xóa món thành công", ButtonType.OK);
                alert.show();
        }
    }

    public void Thoat(){

    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
