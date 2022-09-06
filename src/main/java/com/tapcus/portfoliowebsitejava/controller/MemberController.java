package com.tapcus.portfoliowebsitejava.controller;

import com.tapcus.portfoliowebsitejava.dto.MemberRegisterRequest;
import com.tapcus.portfoliowebsitejava.model.Member;
import com.tapcus.portfoliowebsitejava.service.MemberService;
import com.tapcus.portfoliowebsitejava.util.Page;
import com.tapcus.portfoliowebsitejava.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/welcome")
    public String welcome() {
        return "Hello";
    }

    @PostMapping("/register")
    public ResponseEntity<Result<Object>> register(@RequestBody @Valid MemberRegisterRequest memberRegisterRequest) {

        Integer memberId = memberService.register(memberRegisterRequest);

        Map<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);

        Result<Object> r = new Result<>();
        r.setCode(200);
        r.setMessage("創建成功");
        r.setData(map);

        return ResponseEntity.status(HttpStatus.OK).body(r);
    }

    @PostMapping("/avatar")
    public ResponseEntity<Result<Object>> updateAvatar(@RequestParam("file") MultipartFile file) throws IOException {

        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member userDetails = (Member) object;
        log.info("username --- {}", userDetails.getMemberId());

        // 取得檔名，驗證副檔名
        String filename = file.getOriginalFilename();

        // 得到副檔名
        String fx = FilenameUtils.getExtension(filename);
        if(!(fx.equals("png") || fx.equals("jpg")))
            throw new IOException("檔案格式不正確");

        // 驗證 < 64kb
        double fs = (double) file.getSize() / 1024;
        if(fs >= 64)
            throw new IOException("檔案大小大於 64 kb");

        // 驗證長寛
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        int height = bufferedImage.getHeight();
        int width = bufferedImage.getWidth();
        if(height > 200 || width > 200)
            throw new IOException("圖片大小不正確");

        byte[] avatar = memberService.updateAvatar(userDetails.getMemberId(), file);

        Map<String, Object> map = new HashMap<>();
        map.put("avatar", avatar);

        Result<Object> r = new Result<>(200, "更新成功", map);

        return ResponseEntity.status(HttpStatus.OK).body(r);
    }

    @GetMapping("/members")
    public ResponseEntity<Page<List<Member>>> getMembers(@RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
                                                         @RequestParam(defaultValue = "0") @Min(0) Integer offset) {

        List<Member> memberList = memberService.getMembers(limit, offset);
        Integer total = memberService.countProduct();

        Page<List<Member>> result = new Page<>(200, "操作成功", memberList);
        result.setLimit(limit);
        result.setOffset(offset);
        result.setTotal(total);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
