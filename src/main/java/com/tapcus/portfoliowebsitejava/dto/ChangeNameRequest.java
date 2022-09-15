package com.tapcus.portfoliowebsitejava.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ChangeNameRequest {
    @Length(max = 12, message = "暱稱過長")
    @NotBlank(message = "暱稱不可為空")
    private String name;
}
