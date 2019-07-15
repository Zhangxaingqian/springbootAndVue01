package cn.myweb01.money01.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户实体类
 */
@Table(name="tab_user")//建立实体类和数据库表的映射关系
public class User implements Serializable {

	@Id//设置当前的uid为主键
	@GeneratedValue(generator = "JDBC")
    private Integer uid;//用户id
	@Column(name="username")
    private String username;//用户名，账号
    private String password;//密码
    private String name;//真实姓名
    private String birthday;//出生日期
    private String sex;//男或女
    private String telephone;//手机号
    private String email;//邮箱
    private String status;//激活状态，1代表激活，0代表未激活
    private String code;//激活码（要求唯一）
	private String sendEmailFlag;//发送邮件的标志
	@Transient
	private String remark;




	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
     * 无参构造方法
     */
    public User() {
    }


	public String getSendEmailFlag() {
		return sendEmailFlag;
	}

	public void setSendEmailFlag(String sendEmailFlag) {
		this.sendEmailFlag = sendEmailFlag;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


}
