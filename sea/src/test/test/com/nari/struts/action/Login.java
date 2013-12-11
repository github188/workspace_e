package test.com.nari.struts.action;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import test.com.nari.pojo.TestDAO;

import com.nari.util.exception.DBAccessException;

public class Login {

	// 判断提交是否成功的标志
	public boolean success;
	// public String[] errors;

	public String username;
	public String userpass;
	public TestDAO testDAO;
	public List list;

	public String execute() throws Exception {
		return exe();
	}

	public String exe() throws Exception {

		setUsername("zhang");
		setList(testDAO.findAll());
		setSuccess(true);
		//int i = 1 / 0;
		// throw new Exception();
		// throw new DBException("假错误");
		return "success";

	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpass() {
		return userpass;
	}

	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}

	public String getUserTest() {
		System.out.println("fdsafdsafdsafa");
		return "ssssssssssss";
	}

	public void setTestDAO(TestDAO testDAO) {
		this.testDAO = testDAO;
	}

	/**
	 * 
	 * @return 可以使用以下注解来排除返回属性
	 */
	@JSON(serialize = false)
	public TestDAO getTestDAO() {
		return this.testDAO;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

}
