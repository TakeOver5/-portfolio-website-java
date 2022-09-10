package com.tapcus.portfoliowebsitejava.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class ForgetPasswordRequest {
    @Length(max = 64, message = "信箱過長")
    @NotBlank(message = "信箱不可為空")
    @Email(message = "信箱格式不正確")
    private String email;
}
