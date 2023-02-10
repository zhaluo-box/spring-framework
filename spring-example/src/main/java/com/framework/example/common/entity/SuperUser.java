package com.framework.example.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created  on 2023/2/10 11:11:21
 *
 * @author zl
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SuperUser extends User {

	private String address;
}
