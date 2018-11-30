package com.share.lifetime.security.domain.entity;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class Authorities extends Entity {

    private String username;

    private String authority;

    private String remark;

}