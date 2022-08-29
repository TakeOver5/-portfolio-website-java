package com.tapcus.portfoliowebsitejava.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class Member {

    private Integer memberId;
    private String name;
    private byte[] avatar;
    private String email;

    @JsonIgnore
    private String password;

    private Date createdDate;
    private String auth;

}
