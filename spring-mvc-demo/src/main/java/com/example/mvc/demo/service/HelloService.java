package com.example.mvc.demo.service;

import com.example.mvc.demo.entity.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created  on 2022/7/31 15:15:47
 *
 * @author zl
 */
@Service
public class HelloService {

	public ModelAndView hello() {
		Student student = new Student("张三", "张小黑", "北京");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("result", student);
		modelAndView.setViewName("hello");
		return modelAndView;
	}

}
