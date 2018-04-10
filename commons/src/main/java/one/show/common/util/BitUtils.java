package one.show.common.util;

public class BitUtils {
	
	public static boolean isMatch(int value,int index){
		if(index<0){
			return false;
		}
		int n = 1<<index;
		return (value&n)==n;
	}

	public static void main(String[] args) {
		System.out.println(isMatch(57, 4));
	}
}
