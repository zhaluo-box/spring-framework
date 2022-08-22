package com.framework.example.source.code.learn.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.junit.jupiter.api.Test;

/**
 * Created  on 2022/8/22 22:22:56
 *
 * @author zl
 */
public class TestLog4j2 {

	@Test
	public void test() {
		ExtendedLogger logger = LogManager.getContext().getLogger("a");
		logger.error("log4j2");
	}
}
