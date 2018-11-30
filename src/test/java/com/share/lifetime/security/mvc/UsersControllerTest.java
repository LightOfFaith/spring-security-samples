package com.share.lifetime.security.mvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.share.lifetime.security.config.DataConfig;
import com.share.lifetime.security.config.MyBatisConfig;
import com.share.lifetime.security.config.WebMvcConfig;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebMvcConfig.class, DataConfig.class, MyBatisConfig.class})
public class UsersControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testCreate() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("username", "dev");
        params.set("password", "password");
        params.set("remark", "研发");
        this.mockMvc.perform(post("/users/").params(params)).andExpect(status().isOk());
    }

}
