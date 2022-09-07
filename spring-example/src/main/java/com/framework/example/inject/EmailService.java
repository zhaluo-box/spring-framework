package com.framework.example.inject;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;

/**
 * Created  on 2022/9/7 15:15:07
 *
 * @author zl
 */
@Slf4j
@Service
public class EmailService {

	{
		log.info(" EmailService ----");
	}
}
