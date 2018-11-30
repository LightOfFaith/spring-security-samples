 package com.share.lifetime.security.domain.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Entity {
     
     private Long id;
     
     private Date gmtCreate;
     
     private Date gmtModified;

}
