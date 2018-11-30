package com.share.lifetime.security.mvc;

import java.lang.reflect.InvocationTargetException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.share.lifetime.security.domain.entity.Users;
import com.share.lifetime.security.service.UsersService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/users/")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public ModelAndView view(@PathVariable("id") Long id) throws IllegalAccessException, InvocationTargetException {
        log.info("========view========id:{}", id);
        Users condition = new Users();
        condition.setId(id);
        log.info("condition:{}",condition);
        Users users = usersService.getUsersWithCondition(condition);
        return new ModelAndView("users/show", "users", users);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(@Valid Users users, BindingResult result, RedirectAttributes redirect,
        Authentication authentication) {
        if (result.hasErrors()) {
            log.warn("========Incorrect input========users:{}",users);
            return new ModelAndView("users/show");
        }
        log.info("========create========users:{}", users);
        usersService.insertSelective(users);
        redirect.addFlashAttribute("globalMessage", "Successfully created a new users");
        return new ModelAndView("redirect:/users/{users.id}", "users.id", users.getId());
    }

}
