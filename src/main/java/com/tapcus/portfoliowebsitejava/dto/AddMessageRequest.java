package com.tapcus.portfoliowebsitejava.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class AddMessageRequest {

    @NotBlank(message = "留言不能空白")
    @Length(max = 80, message = "留言過長")
    private String content;

}
