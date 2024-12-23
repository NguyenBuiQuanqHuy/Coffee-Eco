package huy.ntu.coffee_eco.Service;

import huy.ntu.coffee_eco.Models.Entities.LoaiHang;
import huy.ntu.coffee_eco.Models.Entities.MenuItem;
import huy.ntu.coffee_eco.Responsitories.MenuDAL;
import javafx.collections.ObservableList;

public class MenuBLL {
    MenuDAL menuDAL=new MenuDAL();

    public void loadDataMenu(ObservableList<MenuItem> menuList){
        menuDAL.loadMenu(menuList);
    }
    public void loadLoaiHang(ObservableList<LoaiHang> loaiHangList){
        menuDAL.loadLoaiHang(loaiHangList);
    }

    public String getTenLoaiHang(int maloaihang){
        return menuDAL.getTenLoaiHang(maloaihang);
    }
    public void AddMenu(MenuItem menuItem){
        menuDAL.addMenu(menuItem);
    }

    public void UpdateMenu(MenuItem menuItem){
        menuDAL.updateMenu(menuItem);
    }

    public void DeleteMenu(int MenuID){
        menuDAL.deleteMenu(MenuID);
    }
}
