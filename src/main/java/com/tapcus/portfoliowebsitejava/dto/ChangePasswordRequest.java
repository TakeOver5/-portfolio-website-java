package com.tapcus.portfoliowebsitejava.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ChangePasswordRequest {

    @NotBlank(message = "舊密碼不可為空")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}",
            message = "密碼必須為英數混合且長度 8~16 碼")
    private String oldPassword;

    @NotBlank(message = "新密碼不可為空")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}",
            message = "密碼必須為英數混合且長度 8~16 碼")
    private String newPassword;

}
