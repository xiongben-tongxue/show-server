package one.show.common;

public class AreaUtil {
	public static String[] getArea(String city){
		String [] strArr = new String[2];
		if(city!=null && !"".equals(city)){
			String[] split = city.split("市");
			if (split.length == 0)
				return new String[]{"0","0"};

			Object s = AreaNetCache.cityMap.get(split[0]);
			if (s == null)
				return new String[]{"0","0"};

			int num=(int) s;
			if(num>100){
				strArr[0]=String.valueOf(num/100);
				strArr[1] = String.valueOf(num);
			}else{
				strArr[0]=String.valueOf(num);
				strArr[1] = String.valueOf(num);
			}
		}else{
			strArr[0]=String.valueOf(0);
			strArr[1] = String.valueOf(0);
		}
		
		return strArr;
		
	}
	public static void main(String[] args) {
		System.out.println(AreaUtil.getArea("上海市")[0]+"---------"+AreaUtil.getArea("上海市")[1]);
	}
}
