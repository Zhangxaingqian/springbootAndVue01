package cn.myweb01.money01.pojo;

import java.io.Serializable;

/**
 * 分类实体类
 */
public class SecondJobCategory implements Serializable {

	private Integer secondId;//分类id
	private Integer firstId;//分类id
	private String secondName;//分类名称

	public Integer getSecondId() {
		return secondId;
	}

	public void setSecondId(Integer secondId) {
		this.secondId = secondId;
	}

	public Integer getFirstId() {
		return firstId;
	}

	public void setFirstId(Integer firstId) {
		this.firstId = firstId;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
}
