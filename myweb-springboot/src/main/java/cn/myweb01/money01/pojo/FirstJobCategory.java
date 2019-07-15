package cn.myweb01.money01.pojo;

import java.io.Serializable;

/**
 * 分类实体类
 */
public class FirstJobCategory implements Serializable {

    private Integer firstId;//分类id
    private String firstName;//分类名称

	public FirstJobCategory() {
	}

	public Integer getFirstId() {
		return firstId;
	}

	public void setFirstId(Integer firstId) {
		this.firstId = firstId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}
