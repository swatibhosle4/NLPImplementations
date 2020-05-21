package com.data.redact.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RequestsHandler {

	@RequestMapping("/")
	   public String redirectToHomepage() {
	      return "nlphome";
	   }
}
