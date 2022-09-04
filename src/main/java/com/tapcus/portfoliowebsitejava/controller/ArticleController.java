package com.tapcus.portfoliowebsitejava.controller;

import com.tapcus.portfoliowebsitejava.dto.MemberRegisterRequest;
import com.tapcus.portfoliowebsitejava.util.Result;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;

public class ArticleController {

    @Validated
    @PostMapping("/article")
    public ResponseEntity<Result<Object>> uploadArticle(@RequestParam @NotBlank(message = "標題不能空白") @Length(max = 30, message = "標題過長") String title,
                                                        @RequestParam @NotBlank(message = "介紹不能空白") @Length(max = 90, message = "介紹過長") String introduction,
                                                        @RequestParam @NotBlank(message = "內文不能空白") @Length(max = 65535, message = "內文過長") String content,
                                                        @RequestPart @NotNull(message = "請選擇一張封面上傳") MultipartFile cover,
                                                        @RequestPart(required = false) MultipartFile file,
                                                        @RequestParam @NotBlank(message = "GitHub 路徑不能空白") @Pattern(regexp = "^https:\\/\\/github\\.com\\/[^\\s]+\\/[^\\s]+",
                                                                message = "密碼必須為英數混合且長度 8~16 碼") String git_file_path) {


        return null;
    }

}
