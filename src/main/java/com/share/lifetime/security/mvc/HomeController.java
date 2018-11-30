package com.share.lifetime.security.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.share.lifetime.security.domain.entity.Users;
import com.share.lifetime.security.service.UsersService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UsersService usersService;

    @RequestMapping
    public ModelAndView list() {
        Users condition = new Users();
        List<Users> users = usersService.listUsersWithCondition(condition);
        return new ModelAndView("users/list", "users", users);
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(@ModelAttribute Users users) {
        log.info("========createForm========message:{}", users);
        return "users/edit";
    }

}
