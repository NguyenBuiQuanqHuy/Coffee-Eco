package huy.ntu.coffee_eco.Controller;

import huy.ntu.coffee_eco.HelloApplication;
import huy.ntu.coffee_eco.Models.Entities.ChiTietHoaDon;
import huy.ntu.coffee_eco.Models.Entities.HoaDon;
import huy.ntu.coffee_eco.Service.ThongKeBLL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class ThongKeController {
    @FXML
    DatePicker dpNgayTao;
    @FXML
    TableView<HoaDon> tableViewHoaDon;
    @FXML
    TableColumn<HoaDon,Integer> colNhanVien;
    @FXML
    TableColumn<HoaDon, Timestamp> colNgayTao;
    @FXML
    TableColumn<HoaDon, Float> colTongTien;
    @FXML
    TableView<ChiTietHoaDon> tableViewCTHD;
    @FXML
    TableColumn<ChiTietHoaDon,Integer> colTenMon,colSoLuong;
    @FXML
    TableColumn<ChiTietHoaDon,Float> colDonGia,colThanhTien;
    private ThongKeBLL thongKeBLL=new ThongKeBLL();
    private ObservableList<HoaDon> hoaDons= FXCollections.observableArrayList();
    private ObservableList<ChiTietHoaDon> CTHDs=FXCollections.observableArrayList();

    public void initialize(){
        colNhanVien.setCellValueFactory(new PropertyValueFactory<>("maNV"));
        colNhanVien.setCellFactory(col -> new TableCell<HoaDon, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    Integer maNV = getTableRow().getItem().getMaNV();
                    String tenNV = thongKeBLL.getNhanVien(maNV);
                    setText(tenNV);
                }
            }
        });
        colNgayTao.setCellValueFactory(new PropertyValueFactory<>("ngaylap"));
        colTongTien.setCellValueFactory(new PropertyValueFactory<>("thanhtien"));
        colTongTien.setCellFactory(column -> new TableCell<>() {
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

        thongKeBLL.LoadHoaDon(hoaDons);
        tableViewHoaDon.setItems(hoaDons);

        tableViewHoaDon.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->{
            CTHDs.clear();
            thongKeBLL.LoadCTHD(CTHDs,newSelection.getMaHD());
            tableViewCTHD.setItems(CTHDs);

            colTenMon.setCellValueFactory(new PropertyValueFactory<>("maMenu"));
            colTenMon.setCellFactory(col -> new TableCell<ChiTietHoaDon, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        Integer maMon = getTableRow().getItem().getMaMenu();
                        String tenMon = thongKeBLL.getMenu(maMon);
                        setText(tenMon);
                    }
                }
            });

            colSoLuong.setCellValueFactory(new PropertyValueFactory<>("soluong"));
            colDonGia.setCellValueFactory(new PropertyValueFactory<>("gia"));
            colThanhTien.setCellValueFactory(new PropertyValueFactory<>("thanhtien"));
            colDonGia.setCellFactory(column -> new TableCell<>() {
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

        });
    }

    public void handleThongKe() {
        LocalDate selectedDate = dpNgayTao.getValue();

        if (selectedDate == null) {
            tableViewHoaDon.setItems(hoaDons);
            return;
        }

        ObservableList<HoaDon> filteredHoaDons = FXCollections.observableArrayList();
        for (HoaDon hoaDon : hoaDons) {
            if (hoaDon.getNgaylap().toLocalDateTime().toLocalDate().equals(selectedDate)) {
                filteredHoaDons.add(hoaDon);
            }
        }
        tableViewHoaDon.setItems(filteredHoaDons);

        if (filteredHoaDons.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Kết quả");
            alert.setHeaderText(null);
            alert.setContentText("Không tìm thấy hóa đơn nào cho ngày đã chọn.");
            alert.showAndWait();
        }
    }

    public void Thoat() throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/thanhtoan-view.fxml"));
        Scene loginScene = new Scene(loader.load());
        Stage currentStage = (Stage) tableViewHoaDon.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.setScene(loginScene);
        newStage.initStyle(StageStyle.UNDECORATED);
        currentStage.close();
        newStage.show();
    }
}
