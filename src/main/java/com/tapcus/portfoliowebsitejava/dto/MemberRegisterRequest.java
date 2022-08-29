package com.tapcus.portfoliowebsitejava.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class MemberRegisterRequest {

    @Length(max = 12, message = "暱稱過長")
    @NotBlank(message = "暱稱不可為空")
    private String name;

    @Length(max = 64, message = "信箱過長")
    @NotBlank(message = "信箱不可為空")
    @Email(message = "信箱格式不正確")
    private String email;

    @NotBlank(message = "密碼不可為空")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}",
             message = "密碼必須為英數混合且長度 8~16 碼")
    private String password;
}
