package huy.ntu.coffee_eco.Controller;

import huy.ntu.coffee_eco.HelloApplication;
import huy.ntu.coffee_eco.Models.Entities.*;
import huy.ntu.coffee_eco.Models.Entities.MenuItem;
import huy.ntu.coffee_eco.Service.HoaDonBLL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.text.DecimalFormat;

public class HoaDonController {
    @FXML
    TextField textFieldSoluong;
    @FXML
    Label labelTenNV,labelTenTK;
    @FXML
    ComboBox<LoaiHang> comboBoxLoai;
    @FXML
    ComboBox<MenuItem> comboBoxMenu;
    @FXML
    Text textGia,textThanhTien;
    @FXML
    ImageView imageSP;
    @FXML
    TableView<ChiTietHoaDon> tableViewCTHD;
    @FXML
    TableColumn<ChiTietHoaDon,Integer> colLoai,colMenu;
    @FXML
    TableColumn<ChiTietHoaDon,Integer> colSoLuong;
    @FXML
    TableColumn<ChiTietHoaDon,Double> colGia;

    HoaDonBLL hoaDonBLL=new HoaDonBLL();
    private ObservableList<ChiTietHoaDon> chiTietHoaDons = FXCollections.observableArrayList();

    public void initialize() {

        //TaiKhoan currentUser = CurrentUser.getCurrentUser();
        TaiKhoan currentUser = CurrentUser.getCurrentUser();
        if (currentUser == null || currentUser.getNhanVien() == null) {
            System.out.println("CurrentUser hoặc nhân viên chưa được khởi tạo!");
        } else {
            System.out.println("MaNV: " + currentUser.getNhanVien().getMaNV());
        }

        labelTenNV.setText(currentUser.getNhanVien().getTen());
        labelTenTK.setText(currentUser.getTenTK());
        colLoai.setCellValueFactory(new PropertyValueFactory<>("maLoai"));
        colLoai.setCellFactory(col -> new TableCell<ChiTietHoaDon, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    Integer maLoaiHang = getTableRow().getItem().getMaLoai();
                    String tenLoaiHang = hoaDonBLL.getTenLoaiHang(maLoaiHang);
                    setText(tenLoaiHang);
                }
            }
        });

        colMenu.setCellValueFactory(new PropertyValueFactory<>("maMenu"));
        colMenu.setCellFactory(col -> new TableCell<ChiTietHoaDon, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    Integer maMenu = getTableRow().getItem().getMaMenu();
                    String tenSP = hoaDonBLL.getMenu(maMenu);
                    setText(tenSP);
                }
            }
        });
        colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soluong"));
        colGia.setCellValueFactory(new PropertyValueFactory<>("gia"));
        colGia.setCellFactory(column -> new TableCell<>() {
            private final DecimalFormat format = new DecimalFormat("#,###.###");

            @Override
            protected void updateItem(Double gia, boolean empty) {
                super.updateItem(gia, empty);
                if (empty || gia == null) {
                    setText(null);
                } else {
                    setText(format.format(gia));
                }
            }
        });


        tableViewCTHD.setItems(chiTietHoaDons);

        textFieldSoluong.setDisable(true);
        ObservableList<LoaiHang> loaiHangs = FXCollections.observableArrayList();
        hoaDonBLL.LoadLoaiHang(loaiHangs); 
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
                textFieldSoluong.setText("1");
                String imagePath = selectedMenuItem.getHinhAnh();
                String imagePathInResources = "/huy/ntu/coffee_eco/images/" + imagePath;
                Image image = new Image(getClass().getResourceAsStream(imagePathInResources));
                imageSP.setImage(image);
                textFieldSoluong.setDisable(false);
            }
        });

        textFieldSoluong.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue.isEmpty()) {
                    ThanhTien();
                }
            } catch (NumberFormatException e) {
                textFieldSoluong.setText(oldValue);
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
            Stage currentStage = (Stage) labelTenTK.getScene().getWindow();
            Stage newStage = new Stage();
            newStage.setScene(loginScene);
            newStage.initStyle(StageStyle.UNDECORATED);
            currentStage.close();
            newStage.show();
        }
    }

    public void handleThemSP() {
        MenuItem selectedMenuItem = comboBoxMenu.getValue();
        LoaiHang selectedLoaiHang= comboBoxLoai.getValue();
        int soLuong = Integer.parseInt(textFieldSoluong.getText().trim());
        double thanhTien = Double.parseDouble(textGia.getText().trim().replace(",", ""));
        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(0,selectedLoaiHang.getMaloaihang(), selectedMenuItem.getId(), soLuong, thanhTien);
        chiTietHoaDons.add(chiTietHoaDon);
        tableViewCTHD.refresh();
        comboBoxLoai.getSelectionModel().clearSelection();
        comboBoxMenu.getSelectionModel().clearSelection();
        textFieldSoluong.clear();
        textGia.setText(" ");
        imageSP.setImage(null);
        textFieldSoluong.setDisable(true);

        TongTien();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thêm món thành công");
        alert.setHeaderText(null);
        alert.setContentText("Món đã được thêm vào hóa đơn!");
    }


    public void minusSoluong(){
        int soLuong = Integer.parseInt(textFieldSoluong.getText().trim());
        if (soLuong > 1) {
            soLuong -= 1;
            textFieldSoluong.setText(String.valueOf(soLuong));
            ThanhTien();
        }
    }

    public void plusSoluong(){
        int soLuong = Integer.parseInt(textFieldSoluong.getText().trim());
        soLuong += 1;
        textFieldSoluong.setText(String.valueOf(soLuong));
        ThanhTien();
    }

    public void handleThanhToan() {
        // Kiểm tra nếu danh sách chi tiết hóa đơn rỗng
        if (chiTietHoaDons.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText("Chưa có sản phẩm nào trong hóa đơn");
            alert.setContentText("Vui lòng thêm sản phẩm trước khi thanh toán.");
            alert.showAndWait();
            return;
        }

        TaiKhoan currentUser = CurrentUser.getCurrentUser();
        int maNV = currentUser.getNhanVien().getMaNV();
        double thanhTien = Double.parseDouble(textThanhTien.getText().trim().replace(",", ""));
        java.sql.Date ngayTao = new java.sql.Date(System.currentTimeMillis());

        HoaDon hoaDon = new HoaDon(maNV, ngayTao,thanhTien);
        int maHD = hoaDonBLL.ThemHoadon(hoaDon);

        for (ChiTietHoaDon chiTiet : chiTietHoaDons) {
            chiTiet.setMaHD(maHD);
            hoaDonBLL.ThemCTHD(chiTiet);
        }

        chiTietHoaDons.clear();
        tableViewCTHD.refresh();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thanh toán thành công");
        alert.setHeaderText(null);
        alert.setContentText("Hóa đơn đã được tạo thành công!");
        alert.showAndWait();

        // Reset các thành phần giao diện
        textThanhTien.setText("");
        comboBoxLoai.getSelectionModel().clearSelection();
        comboBoxMenu.getSelectionModel().clearSelection();
        textFieldSoluong.clear();
        textGia.setText("");
        imageSP.setImage(null);
        textFieldSoluong.setDisable(true);
    }


    public void ThongKe(){

    }

    public void QuanLyMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/menu-view.fxml"));
        Scene loginScene = new Scene(loader.load());
        Stage currentStage = (Stage) labelTenTK.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.setScene(loginScene);
        newStage.initStyle(StageStyle.UNDECORATED);
        currentStage.close();
        newStage.show();
    }

    public void QuanLyNhanVien() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/nhanvien-view.fxml"));
        Scene loginScene = new Scene(loader.load());
        Stage currentStage = (Stage) labelTenTK.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.setScene(loginScene);
        newStage.initStyle(StageStyle.UNDECORATED);
        currentStage.close();
        newStage.show();
    }

    private void ThanhTien() {
        MenuItem menuItem=comboBoxMenu.getValue();
        float gia = menuItem.getGia();
        int soLuong = Integer.parseInt(textFieldSoluong.getText().trim());
        float totalPrice = gia * soLuong;
        String rounded=String.format("%,.0f",totalPrice);
        textGia.setText(String.valueOf(rounded));
    }

    private void TongTien() {
        double total = 0.0;
        for (ChiTietHoaDon chiTiet : chiTietHoaDons) {
            total += chiTiet.getGia();  // Lấy giá trị thanh toán của mỗi sản phẩm
        }

        // Hiển thị tổng tiền vào textThanhTien
        DecimalFormat format = new DecimalFormat("#,###.###");
        textThanhTien.setText(format.format(total));
    }
}
