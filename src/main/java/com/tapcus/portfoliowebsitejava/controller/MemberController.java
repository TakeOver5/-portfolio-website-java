package com.tapcus.portfoliowebsitejava.controller;

import com.tapcus.portfoliowebsitejava.dto.MemberRegisterRequest;
import com.tapcus.portfoliowebsitejava.model.Member;
import com.tapcus.portfoliowebsitejava.service.MemberService;
import com.tapcus.portfoliowebsitejava.util.Page;
import com.tapcus.portfoliowebsitejava.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
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
