package one.show.common.auth;

public class AuthTest {
	//当测试authTest时候，把genSecretTest生成的secret值赋值给它 
	 private static String secret="R2Q3S52RNXBTFTOM"; 
	  
	 public static void genSecretTest() {// 生成密钥 
		  secret = GoogleAuthenticator.generateSecretKey(); 
		 // 把这个qrcode生成二维码，用google身份验证器扫描二维码就能添加成功 
		 String qrcode = GoogleAuthenticator.getQRBarcode("2816661736@qq.com", secret); 
		 System.out.println("qrcode:" + qrcode + ",key:" + secret); 
	 } 
	 
	 public static void main(String[] args) {
//		 genSecretTest();
		 int code = 933432; 
		 long t = System.currentTimeMillis(); 
		 GoogleAuthenticator ga = new GoogleAuthenticator(); 
		 ga.setWindowSize(5);  //should give 5 * 30 seconds of grace...
		 boolean r = ga.check_code("57KQE5CPOL3SFZFA", code, t); 
		 System.out.println("检查code是否正确？" + r); 
	}
}
