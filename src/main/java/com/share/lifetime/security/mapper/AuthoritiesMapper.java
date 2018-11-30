package com.share.lifetime.security.mapper;

import com.share.lifetime.security.domain.entity.Authorities;

public interface AuthoritiesMapper {
    int insert(Authorities record);

    int insertSelective(Authorities record);
}