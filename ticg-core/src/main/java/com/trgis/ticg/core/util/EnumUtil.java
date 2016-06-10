package com.trgis.ticg.core.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 枚举类
 * @Title: EnumUtil
 * @Description: 
 * @author liuyan
 * @date 2016年5月13日 下午4:45:08
 *
 */
public class EnumUtil {
	
	// 删除标志0未删除 1已删除
	public static enum DELFLAG {
		NOMAL(0, "未删除"), DEL(1, "已删除");
		private Integer value;
		private String name;

		DELFLAG(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return this.value;
		}

		public String getName() {
			return this.name;
		}
	}
	
	/**
	 * 状态
	 */
	public static enum STATUS {
		YQY(1, "已启用"), WQY(0, "未启用");
		private Integer value;
		private String name;

		STATUS(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return this.value;
		}

		public String getName() {
			return this.name;
		}
	}

	public static STATUS getStatus(Integer value) {
		for (STATUS status : STATUS.values()) {
			if (status.getValue() == value) {
				return status;
			}
		}
		return null;
	}
	
	public static STATUS getStatusByName(String name) {
		for (STATUS status : STATUS.values()) {
			if (StringUtils.equalsIgnoreCase(status.getName(), name)) {
				return status;
			}
		}
		return null;
	}
}
