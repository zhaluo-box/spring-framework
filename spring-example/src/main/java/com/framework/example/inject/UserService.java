package com.framework.example.inject;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created  on 2022/9/7 15:15:07
 *
 * @author zl
 */
@Slf4j
@Component
public class UserService {

	{
		log.info(" UserService ----");
	}

	@Autowired
	EmailService emailService;

	@Resource
	private RoleService roleService;
}
