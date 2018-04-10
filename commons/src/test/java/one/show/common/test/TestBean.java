package one.show.common.test;

import org.springframework.beans.factory.InitializingBean;

public class TestBean implements InitializingBean{

	private String testName = null;
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("****************************");
		System.out.println("afterPropertiesSet is called,testName:-----"+testName);
		System.out.println("****************************");
	}
	
}
