package com.framework.example.inject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created  on 2022/9/7 15:15:09
 *
 * @author zl
 */
@Slf4j
@Service
public class RoleService {

	{
		log.info("Role Service");
	}

}
