package huy.ntu.coffee_eco.Controller;

import huy.ntu.coffee_eco.HelloApplication;
import huy.ntu.coffee_eco.Models.Entities.LoaiHang;
import huy.ntu.coffee_eco.Service.MenuBLL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import huy.ntu.coffee_eco.Models.Entities.MenuItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;

public class MenuController {

    @FXML
    private ComboBox<LoaiHang> comboBoxLoai;
    @FXML
    private TextField textFieldName,textFieldGia,textFieldTimKiem;
    @FXML
    private TableView<MenuItem> tableViewMenu;
    @FXML
    private TableColumn<MenuItem,Integer> colLoaiSP;
    @FXML
    private TableColumn<MenuItem,Float> colGiaSP;
    @FXML
    private TableColumn<MenuItem,String> colTenSP;
    @FXML
    private ImageView imageSanPham;
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
        colLoaiSP.setCellFactory(col -> new TableCell<MenuItem, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    Integer maLoaiHang = getTableRow().getItem().getLoaiHang();
                    String tenLoaiHang = menuBLL.getTenLoaiHang(maLoaiHang);
                    setText(tenLoaiHang);
                }
            }
        });

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


        // Hiển thị thông tin của món được chọn
        tableViewMenu.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                textFieldName.setText(newSelection.getTenHang());
                textFieldGia.setText(String.valueOf(newSelection.getGia()));

                // Tìm và chọn đúng loại hàng trong ComboBox
                LoaiHang selectedLoai = loaiHangList.stream()
                        .filter(loai -> String.valueOf(loai.getMaloaihang()).equals(String.valueOf(newSelection.getLoaiHang())))
                        .findFirst()
                        .orElse(null);
                comboBoxLoai.setValue(selectedLoai);

                // Hiển thị ảnh nếu có
                if (newSelection.getHinhAnh() != null && !newSelection.getHinhAnh().isEmpty()) {
                    selectedImagePath = "/huy/ntu/coffee_eco/images/" + newSelection.getHinhAnh();
                        Image image = new Image(getClass().getResource(selectedImagePath).toExternalForm());
                        imageSanPham.setImage(image);

                } else {
                    selectedImagePath = null;
                    imageSanPham.setImage(null);
                }

            }
        });
    }

    public void handleChonAnh(){
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

    public void handleThemMenu(){
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

            MenuItem menuItem = new MenuItem(loaiHang.getMaloaihang(), tenHang, gia, savedImagePath);
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

    public void handleCapNhatMenu(){
        MenuItem selectedItem = tableViewMenu.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Vui lòng chọn món cần sửa!", ButtonType.OK);
            alert.show();
            return;
        }
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
            showAlert("Lỗi", "Giá phải là số hợp lệ!", Alert.AlertType.ERROR);
            return;
        }

        selectedItem.setLoaiHang(loaiHang.getMaloaihang());
        selectedItem.setTenHang(tenHang);
        selectedItem.setGia(gia);
        selectedItem.setHinhAnh(savedImagePath);
        menuBLL.UpdateMenu(selectedItem);
        tableViewMenu.refresh();
        showAlert("Thành công", "Cập nhật món thành công!", Alert.AlertType.INFORMATION);
        textFieldName.clear();
        textFieldGia.clear();
        comboBoxLoai.getSelectionModel().clearSelection();
        imageSanPham.setImage(null);
        savedImagePath = null;
    }

    public void handleXoaMenu() {
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

    public void handleTimKiem(){
        String tenMenu = textFieldTimKiem.getText().toString().trim().toLowerCase();
        if (tenMenu.isEmpty()) {
            tableViewMenu.setItems(menuList);
        } else {
            ObservableList<MenuItem> filteredList = FXCollections.observableArrayList();
            for (MenuItem menuItem : menuList) {
                if (menuItem.getTenHang().toLowerCase().contains(tenMenu)) {
                    filteredList.add(menuItem);
                }
            }
            tableViewMenu.setItems(filteredList);
        }
    }

    public void Thoat() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/thanhtoan-view.fxml"));
        Scene loginScene = new Scene(loader.load());
        Stage currentStage = (Stage) comboBoxLoai.getScene().getWindow();
        currentStage.setScene(loginScene);
        currentStage.show();
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
