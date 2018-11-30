package com.share.lifetime.security.domain.entity;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class Users extends Entity {

    private String username;

    private String password;

    private Boolean enabled;

    private String nickname;

    private String realname;

    private String email;

    private String remark;

}
