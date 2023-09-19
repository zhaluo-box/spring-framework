package com.framework.example.source.code.learn.context.validator;

import com.framework.example.common.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.validation.*;

import java.util.Locale;

/**
 * spring 自身的 validator 测试
 * Created  on 2023/9/19 16:16:16
 *
 * @author zl
 */
public class ValidatorTest {

	@Test
	@DisplayName("自定义Validator 测试")
	public void customerValidatorTest() {
		// 1. 创建 Validator
		Validator validator = new CustomerValidator();
		// 2. 判断是否支持目标对象的类型
		User user = new User();
		System.out.println("user 对象是否被 UserValidator 支持检验：" + validator.supports(user.getClass()));
		// 3. 创建 Errors 对象
		Errors errors = new BeanPropertyBindingResult(user, "user");
		validator.validate(user, errors);

		// 4. 获取 MessageSource 对象（基于内存的）
		MessageSource messageSource = createMessageSource();

		// 5. 输出所有的错误文案
		for (ObjectError error : errors.getAllErrors()) {
			String message = messageSource.getMessage(error.getCode(), error.getArguments(), Locale.getDefault());
			System.out.println(message);
		}
	}

	/**
	 * 创建一个基于内存的MessageSource
	 *
	 * @return
	 */
	private MessageSource createMessageSource() {
		StaticMessageSource messageSource = new StaticMessageSource();
		messageSource.addMessage("user.properties.not.null", Locale.getDefault(), "User 所有属性不能为空");
		messageSource.addMessage("id.required", Locale.getDefault(), "the id of User must not be null.");
		messageSource.addMessage("name.required", Locale.getDefault(), "the name of User must not be null.");
		return messageSource;
	}

	/**
	 * @see Errors 含有全局异常， 字段异常
	 */
	static class CustomerValidator implements Validator {

		@Override
		public boolean supports(Class<?> clazz) {
			return User.class.isAssignableFrom(clazz);
		}

		@Override
		public void validate(Object target, Errors errors) {
			User user = (User) target;
			/**
			 * 基于反射获取字段的值，value == null || !StringUtils.hasText(value.toString())
			 * 如果字段是基础数据类型，会被分配默认值，校验会失效
			 */
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "id.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");
			String userName = user.getName();
		}
	}

}
