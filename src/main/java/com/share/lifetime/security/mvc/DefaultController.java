package com.share.lifetime.security.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DefaultController {

	@RequestMapping("/**")
	public String notFound() {
		log.info("notFound");
		return "errors/404";
	}

}
