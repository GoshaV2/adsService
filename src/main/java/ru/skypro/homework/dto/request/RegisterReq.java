package ru.skypro.homework.dto.request;

import lombok.Getter;
import ru.skypro.homework.dto.Role;

@Getter
public class RegisterReq {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
}
