package com.tapcus.portfoliowebsitejava.controller;

import com.tapcus.portfoliowebsitejava.dto.AddMessageRequest;
import com.tapcus.portfoliowebsitejava.dto.IndexArticleResponse;
import com.tapcus.portfoliowebsitejava.model.Article;
import com.tapcus.portfoliowebsitejava.model.ArticleDetail;
import com.tapcus.portfoliowebsitejava.model.ArticleSimple;
import com.tapcus.portfoliowebsitejava.model.Member;
import com.tapcus.portfoliowebsitejava.service.ArticleService;
import com.tapcus.portfoliowebsitejava.util.Page;
import com.tapcus.portfoliowebsitejava.util.Result;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Validated
    @PostMapping("/article")
    public ResponseEntity<Result<Map<String, Object>>> uploadArticle(@RequestParam @NotBlank(message = "標題不能空白") @Length(max = 30, message = "標題過長") String title,
                                                                     @RequestParam @NotBlank(message = "介紹不能空白") @Length(max = 90, message = "介紹過長") String introduction,
                                                                     @RequestParam @NotBlank(message = "內文不能空白") @Length(max = 65535, message = "內文過長") String content,
                                                                     @RequestPart @NotNull(message = "請選擇一張封面上傳") MultipartFile cover,
                                                                     @RequestParam @NotBlank(message = "GitHub 路徑不能空白") @Pattern(regexp = "^https:\\/\\/github\\.com\\/[^\\s]+\\/[^\\s]+",
                                                                message = "密碼必須為英數混合且長度 8~16 碼") String git_file_path) throws IOException {
        // 驗證圖片大小
        // 取得檔名，驗證副檔名
        String filename = cover.getOriginalFilename();

        // 得到副檔名
        String fx = FilenameUtils.getExtension(filename);
        if(!(fx.equals("png") || fx.equals("jpg")))
            throw new IOException("檔案格式不正確");

        // 驗證 < 2Mb
        double fs = (double) cover.getSize() / 1024;
        if(fs >= 2048)
            throw new IOException("檔案大小大於 2 Mb");

        // 驗證長寛
        BufferedImage bufferedImage = ImageIO.read(cover.getInputStream());
        int height = bufferedImage.getHeight();
        int width = bufferedImage.getWidth();
        if(height > 600 || width > 600)
            throw new IOException("圖片大小不正確");

        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = (Member) object;

        Integer articleId = articleService.uploadArticle(member.getMemberId(), title, introduction, content, cover, git_file_path);

        Map<String, Object> map = new HashMap<>();
        map.put("articleId", articleId);

        Result<Map<String, Object>> r = new Result<>(200, "上傳成功", map);

        return ResponseEntity.status(HttpStatus.OK).body(r);

    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<Result<ArticleDetail>> getArticle(@PathVariable Integer articleId) {
        ArticleDetail articleDetail = articleService.getArticle(articleId);
        Result<ArticleDetail> r = new Result<>(200, "獲取成功",articleDetail);
        return ResponseEntity.status(HttpStatus.OK).body(r);
    }

    @DeleteMapping("/article/{articleId}")
    public ResponseEntity<Result<Object>> deleteArticle(@PathVariable Integer articleId) throws IOException {
        articleService.deleteArticle(articleId);
        Result<Object> r = new Result<>(200, "刪除成功");
        return ResponseEntity.status(HttpStatus.OK).body(r);
    }

    @Validated
    @PostMapping("/article/{articleId}/message")
    public ResponseEntity<Result<Map<String, Object>>> uploadMessage(@PathVariable Integer articleId,
                                                                     @RequestBody @Valid AddMessageRequest addMessageRequest) {

        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = (Member) object;
        Integer memberId = member.getMemberId();

        Integer messageId = articleService.uploadMessage(articleId, memberId, addMessageRequest.getContent());


        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);

        Result<Map<String, Object>> r = new Result<>(200, "上傳成功", map);

        return ResponseEntity.status(HttpStatus.OK).body(r);
    }

    @GetMapping("/articles")
    public ResponseEntity<Page<List<IndexArticleResponse>>> getArticles(@RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
                                                          @RequestParam(defaultValue = "0") @Min(0) Integer offset) {

        List<Article> articleList = articleService.getArticles(limit, offset);
        List<IndexArticleResponse> iarList = new ArrayList<>();

        for(Article article : articleList) {
            IndexArticleResponse iar = new IndexArticleResponse();
            iar.setArticle_id(article.getArticleId());
            iar.setTitle(article.getTitle());
            iar.setIntroduction(article.getIntroduction());
            iar.setCover_path(article.getCoverPath());
            iarList.add(iar);
        }

        Integer total = articleService.countArticle();

        Page<List<IndexArticleResponse>> result = new Page<>(200, "獲取成功", iarList);
        result.setLimit(limit);
        result.setOffset(offset);
        result.setTotal(total);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/articles/simple")
    public ResponseEntity<Page<List<ArticleSimple>>> getArticlesSimple(@RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
                                                                       @RequestParam(defaultValue = "0") @Min(0) Integer offset) {

        List<ArticleSimple> articleSimples = articleService.getArticlesSimple(limit, offset);

        Integer total = articleService.countArticleAll();

        Page<List<ArticleSimple>> result = new Page<>(200, "獲取成功", articleSimples);
        result.setLimit(limit);
        result.setOffset(offset);
        result.setTotal(total);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/article/{articleId}/viewable")
    public ResponseEntity<Result<Null>> setViewable(@PathVariable Integer articleId,
                                                    @RequestParam(defaultValue = "0") @Max(1) @Min(0) Integer view) {
        articleService.setViewable(articleId, view);
        Result<Null> r = new Result<>(200, "修改成功");
        return ResponseEntity.status(HttpStatus.OK).body(r);
    }

}
