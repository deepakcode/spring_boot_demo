package com.example.demo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class User {

    private long userId;

    @NotBlank(message ="Username can't be null")
    @Size(min=3, max=20, message="Username can't be less then 3 char or more then 20 char")
    private String userName;

}
