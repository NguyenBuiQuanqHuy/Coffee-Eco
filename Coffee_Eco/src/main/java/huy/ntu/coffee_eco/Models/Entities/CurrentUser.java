package huy.ntu.coffee_eco.Models.Entities;

public class CurrentUser {
    private static TaiKhoan currentUser;

    public static TaiKhoan getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(TaiKhoan user) {
        currentUser = user;
    }
}
