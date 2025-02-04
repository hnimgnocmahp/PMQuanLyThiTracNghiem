package test;

import BUS.UserBUS;
import DTO.UserDTO;

public class main {
    public static void main(String[] args) {
      int i = UserBUS.getInstance().addUser(new UserDTO("Nguyen", "Van A", "whatsup@example.com", "123", 0, 1));

    }
}
