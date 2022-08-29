package com.tapcus.portfoliowebsitejava.controller;

import com.tapcus.portfoliowebsitejava.dto.MemberRegisterRequest;
import com.tapcus.portfoliowebsitejava.service.MemberService;
import com.tapcus.portfoliowebsitejava.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
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

}
