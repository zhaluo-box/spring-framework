package com.example.mvc.demo.controller;

import com.example.mvc.demo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created  on 2022/7/31 15:15:46
 *
 * @author zl
 */
@Controller
@RequestMapping("/hello/")
public class HelloController {

	@Autowired
	private HelloService helloService;

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView hello(@RequestParam("name") String name, @RequestParam("nickName") String nickName, @RequestParam("address") String address) {
		System.out.println(name);
		System.out.println(nickName);
		System.out.println(address);
		return helloService.hello();
	}

	@RequestMapping(value = "open/", method = RequestMethod.GET)
	public String openPage() {
		return "hello";
	}
}
