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
import java.sql.Timestamp;
import java.text.DecimalFormat;

public class HoaDonController {
    @FXML
    private TextField textFieldSoluong;
    @FXML
    private Label labelTenNV;
    @FXML
    private ComboBox<LoaiHang> comboBoxLoai;
    @FXML
    private ComboBox<MenuItem> comboBoxMenu;
    @FXML
    private Text textGia,textThanhTien;
    @FXML
    private ImageView imageSP;
    @FXML
    private TableView<ChiTietHoaDon> tableViewCTHD;
    @FXML
    private TableColumn<ChiTietHoaDon,Integer> colMenu;
    @FXML
    private TableColumn<ChiTietHoaDon,Integer> colSoLuong;
    @FXML
    private TableColumn<ChiTietHoaDon,Float> colGia,colThanhTien;

    private HoaDonBLL hoaDonBLL=new HoaDonBLL();
    private ObservableList<ChiTietHoaDon> chiTietHoaDons = FXCollections.observableArrayList();
    private TaiKhoan currentUser = CurrentUser.getCurrentUser();
    public void initialize() {
        labelTenNV.setText(currentUser.getNhanVien().getTen());
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
            protected void updateItem(Float gia, boolean empty) {
                super.updateItem(gia, empty);
                if (empty || gia == null) {
                    setText(null);
                } else {
                    setText(format.format(gia));
                }
            }
        });

        colThanhTien.setCellValueFactory(new PropertyValueFactory<>("thanhtien"));
        colThanhTien.setCellFactory(column -> new TableCell<>() {
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

    public void handleThemSP() {
        MenuItem selectedMenuItem = comboBoxMenu.getValue();
        LoaiHang selectedLoaiHang = comboBoxLoai.getValue();
        if (selectedMenuItem == null || selectedLoaiHang == null) {
            showAlert(Alert.AlertType.WARNING, "Thông báo", null, "Vui lòng chọn món và loại món!");
            return;
        }

        int soLuong = Integer.parseInt(textFieldSoluong.getText().trim());
        Float thanhtien = Float.parseFloat(textGia.getText().trim().replace(",", ""));
        Float dongia = selectedMenuItem.getGia();

        for (ChiTietHoaDon cthd : chiTietHoaDons) {
            if (cthd.getMaMenu() == selectedMenuItem.getId()) {
                showAlert(Alert.AlertType.WARNING, "Lỗi", null, "Món này đã có trong hóa đơn!");
                return;
            }
        }

        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(0, selectedLoaiHang.getMaloaihang(), selectedMenuItem.getId(), soLuong, dongia, thanhtien);
        chiTietHoaDons.add(chiTietHoaDon);
        tableViewCTHD.refresh();
        comboBoxLoai.getSelectionModel().clearSelection();
        comboBoxMenu.getSelectionModel().clearSelection();
        textFieldSoluong.clear();
        textGia.setText(" ");
        imageSP.setImage(null);
        textFieldSoluong.setDisable(true);
        TongTien();
        showAlert(Alert.AlertType.INFORMATION, "Thêm món thành công", null, "Món đã được thêm vào hóa đơn!");
    }


    public void handleXoaSP() {
        ChiTietHoaDon selectedChiTietHoaDon = tableViewCTHD.getSelectionModel().getSelectedItem();
        if (selectedChiTietHoaDon != null) {
            chiTietHoaDons.remove(selectedChiTietHoaDon);
            tableViewCTHD.refresh();
            TongTien();
            showAlert(Alert.AlertType.INFORMATION, "Xóa món thành công", null, "Món đã được xóa khỏi hóa đơn!");
        } else {
            showAlert(Alert.AlertType.WARNING, "Chưa chọn món", null, "Vui lòng chọn món để xóa!");
        }
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
        if (chiTietHoaDons.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Chưa có sản phẩm nào trong hóa đơn", "Vui lòng thêm sản phẩm trước khi thanh toán.");
            return;
        }

        TaiKhoan currentUser = CurrentUser.getCurrentUser();
        int maNV = currentUser.getNhanVien().getMaNV();
        float thanhTien = Float.parseFloat(textThanhTien.getText().trim().replace(",", ""));

        Timestamp ngayTao = new Timestamp(System.currentTimeMillis());

        HoaDon hoaDon = new HoaDon(maNV, ngayTao,thanhTien);
        int maHD = hoaDonBLL.ThemHoadon(hoaDon);

        for (ChiTietHoaDon chiTiet : chiTietHoaDons) {
            chiTiet.setMaHD(maHD);
            hoaDonBLL.ThemCTHD(chiTiet);
        }

        chiTietHoaDons.clear();
        tableViewCTHD.refresh();

        showAlert(Alert.AlertType.INFORMATION, "Thanh toán thành công", null, "Hóa đơn đã được tạo thành công!");

        textThanhTien.setText("");
        comboBoxLoai.getSelectionModel().clearSelection();
        comboBoxMenu.getSelectionModel().clearSelection();
        textFieldSoluong.clear();
        textGia.setText("");
        imageSP.setImage(null);
        textFieldSoluong.setDisable(true);
    }


    public void ThongKe() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/thongke-view.fxml"));
        Scene loginScene = new Scene(loader.load());
        Stage currentStage = (Stage) labelTenNV.getScene().getWindow();
        currentStage.setScene(loginScene);
        currentStage.show();
    }

    public void QuanLyMenu() throws IOException {
        if (currentUser == null || !currentUser.getTenTK().equalsIgnoreCase("admin")) {
            showAlert(Alert.AlertType.WARNING, "Quyền truy cập bị từ chối", "Bạn không có quyền truy cập", "Chức năng này chỉ dành cho Admin.");
            return;
        }
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/menu-view.fxml"));
        Scene loginScene = new Scene(loader.load());
        Stage currentStage = (Stage) labelTenNV.getScene().getWindow();
        currentStage.setScene(loginScene);
        currentStage.show();
    }

    public void QuanLyNhanVien() throws IOException {
        if (currentUser == null || !currentUser.getTenTK().equalsIgnoreCase("admin")) {
            showAlert(Alert.AlertType.WARNING, "Quyền truy cập bị từ chối", "Bạn không có quyền truy cập", "Chức năng này chỉ dành cho Admin.");
            return;
        }
        
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/nhanvien-view.fxml"));
        Scene loginScene = new Scene(loader.load());
        Stage currentStage = (Stage) labelTenNV.getScene().getWindow();
        currentStage.setScene(loginScene);
        currentStage.show();
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
            total += chiTiet.getThanhtien();
        }
        DecimalFormat format = new DecimalFormat("#,###.###");
        textThanhTien.setText(format.format(total));
    }

    public void DangXuat() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận Đăng xuất");
        alert.setHeaderText("Bạn có chắc chắn muốn đăng xuất?");
        alert.setContentText("Chọn 'OK' để đăng xuất hoặc 'Hủy' để tiếp tục sử dụng ứng dụng.");

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (result == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/login-view.fxml"));
            Scene thanhToanScene = new Scene(loader.load());
            Stage currentStage = (Stage) labelTenNV.getScene().getWindow();
            currentStage.close();
            Stage newStage = new Stage();
            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.setScene(thanhToanScene);
            newStage.show();
        }
    }

    public void Thoat(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận thoát");
        alert.setHeaderText("Bạn có chắc chắn muốn thoát?");
        alert.setContentText("Chọn 'Có' để thoát, 'Không' để hủy.");

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            Stage stage = (Stage) labelTenNV.getScene().getWindow();
            stage.close();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.show();
    }
}
