package ru.skypro.homework.dto.request;

import lombok.Getter;

import java.util.List;
@Getter
public class UserRequest {
    private String email;
    private String firstName;
    private long id;
    private String lastName;
    private String phone;
    private String regDate;
    private String city;
    private List<String> image;
}
