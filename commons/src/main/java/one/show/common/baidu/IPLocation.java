package one.show.common.baidu;


public class IPLocation {

	public class Point {
		public String x;
		public String y;
	}
	public class AddressDetail {
	    public String city;
	    public String city_code;
	    public String district;
	    public String province;
	    public String street;
	    public String street_number;
	}
	public String address;
	public AddressDetail address_detail;
	public Point point;
}
