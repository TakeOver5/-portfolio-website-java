package com.tapcus.portfoliowebsitejava.service;

import com.tapcus.portfoliowebsitejava.util.Result;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.IOException;

public interface ArticleService {
    public Integer uploadArticle(Integer memberId,
                                 String title,
                                 String introduction,
                                 String content,
                                 MultipartFile cover,
                                 String git_file_path) throws IOException;
}
