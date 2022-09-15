package com.tapcus.portfoliowebsitejava.model;

import com.tapcus.portfoliowebsitejava.dto.IndexArticleResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MemberInfo {
    private Integer memberId;
    private byte[] avatar;
    private String name;
    private String email;
    private Date createdDate;
    private List<IndexArticleResponse> articles;
}
