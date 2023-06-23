package ru.skypro.homework.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String email;
    private String firstName;
    private long id;
    private String lastName;
    private String phone;
    private String regDate;
    private String city;
    private List<String> image;
}
