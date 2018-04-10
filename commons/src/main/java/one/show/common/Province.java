package one.show.common;


import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * @author wangzj
 * 
 */
public class Province implements Serializable {
	static Logger log = Logger.getLogger(Province.class);
	private static final long serialVersionUID = 4300060582052270583L;

	/**
	 * 中国行政区域划分(2006)
	 * 
	 * 23个省 河北省（冀） 山西省（晋） 辽宁省（辽） 吉林省（吉） 黑龙江省（黑） 江苏省（苏） 浙江省（浙） 安徽省（皖) 福建省（闽）
	 * 江西省（赣） 山东省（鲁） 河南省（豫） 湖北省（鄂） 湖南省（湘） 广东省（粤） 海南省（琼） 四川省（川、蜀） 贵州省（黔、贵）
	 * 云南省（滇、云） 陕西省（陕、秦） 甘肃省（甘、陇） 青海省（青） 台湾省（台）
	 * 
	 * 4个直辖市 北京市 天津市 上海市 重庆市
	 * 
	 * 5个自治区 广西壮族自治区 内蒙古自治区 西藏自治区 宁夏回族自治区 新疆维吾尔自治区
	 * 
	 * 2个特别行政区 香港特别行政区 澳门特别行政区
	 */

	
	public static final int ZHEJIANG = 10; // 浙江
	public static final int ANHUI = 11; // 安徽
	public static final int FUJIAN = 12; // 福建
	public static final int JIANGXI = 13; // 江西
	public static final int SHANDONG = 14; // 山东
	public static final int HENAN = 15; // 河南
	public static final int HUBEI = 16; // 湖北
	public static final int HUNAN = 17; // 湖南
	public static final int GUANGDONG = 18; // 广东
	public static final int HAINAN = 19; // 海南
	public static final int SICHUAN = 20; // 四川
	public static final int GUIZHOU = 21; // 贵州
	public static final int YUNNAN = 22; // 云南
	public static final int SHAANXI = 23; // 陕西
	public static final int GANSU = 24; // 甘肃
	public static final int QINGHAI = 25; // 青海
	public static final int TAIWAN = 26; // 台湾

	public static final int GUANGXI = 27; // 广西壮族自治区
	public static final int NEIMENGGU = 28; // 内蒙古自治区
	public static final int XIZHANG = 29; // 西藏自治区
	public static final int NINGXIA = 30; // 宁夏回族自治区
	public static final int XINJIANG = 31; // 新疆维吾尔自治区
	public static final int HONGKONG = 32; // 香港特别行政区
	public static final int MACAU = 33; // 澳门特别行政区
	public static final int OTHER = 34; // 其它
	public static final int CN = 35; // 中国
	public static final int College = 37; // 大学
	public static final int CZ88 = 36;
	public static final int YATAI = 38;
	
	public static final int BEIJING = 40; // 北京
	public static final int TIANJIN = 41; // 天津
	public static final int SHANGHAI = 42; // 上海
	public static final int CHONGQING = 43; // 重庆
	public static final int HEBEI = 44; // 河北
	public static final int SHANXI = 45; // 山西
	public static final int LIAONING = 46; // 辽宁
	public static final int JILIN = 47; // 吉林
	public static final int HEILONGJIANG = 48; // 黑龙江
	public static final int JIANGSU = 49; // 江苏
	
	
	
	public static final int DX_NETTYPE=1;
	public static final int LT_NETTYPE=2;
	public static final int TT_NETTYPE=3;
	public static final int GD_NETTYPE=4;
	public static final int GH_NETTYPE=5;	
	public static final int DXT_NETTYPE=6;
	public static final int JY_NETTYPE=7;
	public static final int YD_NETTYPE=8;
	public static final int WB_NETTYPE=9;
	public static final int OTHER_NETTYPE=10;

	public static int getNetTypeCode(String area) {
		int ret = Province.OTHER_NETTYPE;
		if (area.indexOf("电信通") != -1) {
			ret = Province.DXT_NETTYPE;
		} else if (area.indexOf("电信") != -1) {
			ret = Province.DX_NETTYPE;
		} else if (area.indexOf("联通") != -1) {
			ret = Province.LT_NETTYPE;
		} else if (area.indexOf("铁通") != -1) {
			ret = Province.TT_NETTYPE;
		} else if (area.indexOf("广电") != -1) {
			ret = Province.GD_NETTYPE;
		} else if (area.indexOf("歌华") != -1) {
			ret = Province.GH_NETTYPE;
		} else if (area.indexOf("教育") != -1 || area.indexOf("大学") != -1 || area.indexOf("学院") != -1 ) {
			ret = Province.JY_NETTYPE;
		} else if (area.indexOf("移动") != -1) {
			ret = Province.YD_NETTYPE;
		} else if ((area.indexOf("网吧") != -1) || (area.indexOf("网络") != -1) || (area.indexOf("网城") != -1)) {
			ret = Province.WB_NETTYPE;
		}
		return ret;

	}
	public static String getNetTypeName(int type) {
			String ret="";
		switch (type) {
		case Province.DX_NETTYPE:
			ret = "电信";
			break;
		case LT_NETTYPE:
			ret = "联通";
			break;
		case TT_NETTYPE:
			ret = "铁通";
			break;
		case GD_NETTYPE:
			ret = "广电";
			break;
		case GH_NETTYPE:
			ret = "歌华";
			break;
		case DXT_NETTYPE:
			ret = "电信通";
			break;
		case JY_NETTYPE:
			ret = "教育网或大学";
			break;
		case YD_NETTYPE:
			ret = "移动";
			break;
		case WB_NETTYPE:
			ret = "网吧";
			break;
		case OTHER_NETTYPE:
			ret = "其他";
			break;
		}
		return ret;

	}
	
	public static int getCountryCode(String country) {
		int ret = Province.OTHER;
		if(country.startsWith("中国")){
			ret=Province.CN;
		}else if (country.startsWith("河北省")) {
			if (country.startsWith("河北省石家庄")) {
				ret = 4401;
			} else if (country.startsWith("河北省唐山")) {
				ret = 4402;
			} else if (country.startsWith("河北省秦皇岛")) {
				ret = 4403;
			} else if (country.startsWith("河北省邯郸")) {
				ret = 4404;
			} else if (country.startsWith("河北省邢台")) {
				ret = 4405;
			} else if (country.startsWith("河北省保定")) {
				ret = 4406;
			} else if (country.startsWith("河北省张家口")) {
				ret = 4407;
			} else if (country.startsWith("河北省承德")) {
				ret = 4408;
			} else if (country.startsWith("河北省廊坊")) {
				ret = 4409;
			} else if (country.startsWith("河北省衡水")) {
				ret = 4410;
			} else if (country.startsWith("河北省沧州")) {
				ret = 4411;
			} else if (country.startsWith("河北省-")) {
				ret = 4413;
			} else {
				ret = 4412;
			}
			// ret = 44;
		} else if (country.startsWith("山西省")) {
			if (country.startsWith("山西省太原")) {
				ret = 4501;
			} else if (country.startsWith("山西省大同")) {
				ret = 4502;
			} else if (country.startsWith("山西省长治")) {
				ret = 4503;
			} else if (country.startsWith("山西省晋城")) {
				ret = 4504;
			} else if (country.startsWith("山西省朔州")) {
				ret = 4505;
			} else if (country.startsWith("山西省晋中")) {
				ret = 4506;
			} else if (country.startsWith("山西省运城")) {
				ret = 4507;
			} else if (country.startsWith("山西省忻州")) {
				ret = 4508;
			} else if (country.startsWith("山西省临汾")) {
				ret = 4509;
			} else if (country.startsWith("山西省吕梁")) {
				ret = 4510;
			} else if (country.startsWith("山西省阳泉")) {
				ret = 4511;
			} else if (country.startsWith("山西省-")) {
				ret = 4513;
			} else {
				ret = 4512;
			}
			// ret = Province.SHANXI;
		} else if (country.startsWith("辽宁省")) {
			if (country.startsWith("辽宁省沈阳")) {
				ret = 4601;
			} else if (country.startsWith("辽宁省大连")) {
				ret = 4602;
			} else if (country.startsWith("辽宁省鞍山")) {
				ret = 4603;
			} else if (country.startsWith("辽宁省抚顺")) {
				ret = 4604;
			} else if (country.startsWith("辽宁省本溪")) {
				ret = 4605;
			} else if (country.startsWith("辽宁省丹东")) {
				ret = 4606;
			} else if (country.startsWith("辽宁省锦州")) {
				ret = 4607;
			} else if (country.startsWith("辽宁省营口")) {
				ret = 4608;
			} else if (country.startsWith("辽宁省阜新")) {
				ret = 4609;
			} else if (country.startsWith("辽宁省辽阳")) {
				ret = 4610;
			} else if (country.startsWith("辽宁省盘锦")) {
				ret = 4611;
			} else if (country.startsWith("辽宁省铁岭")) {
				ret = 4612;
			} else if (country.startsWith("辽宁省朝阳")) {
				ret = 4613;
			} else if (country.startsWith("辽宁省葫芦岛")) {
				ret = 4614;
			} else if (country.startsWith("辽宁省-")) {
				ret = 4616;
			} else {
				ret = 4615;
			}
			// ret = 46;
		} else if (country.startsWith("吉林省")) {
			if (country.startsWith("吉林省长春")) {
				ret = 4701;
			} else if (country.startsWith("吉林省吉林")) {
				ret = 4702;
			} else if (country.startsWith("吉林省四平")) {
				ret = 4703;
			} else if (country.startsWith("吉林省辽源")) {
				ret = 4704;
			} else if (country.startsWith("吉林省通化")) {
				ret = 4705;
			} else if (country.startsWith("吉林省白山")) {
				ret = 4706;
			} else if (country.startsWith("吉林省松原")) {
				ret = 4707;
			} else if (country.startsWith("吉林省白城")) {
				ret = 4708;
			} else if (country.startsWith("吉林省延边")) {
				ret = 4709;
			} else {
				ret = 4710;
			}
			// ret = 47;
		} else if (country.startsWith("黑龙江省")) {
			if (country.startsWith("黑龙江省哈尔滨")) {
				ret = 4801;
			} else if (country.startsWith("黑龙江省齐齐哈尔市")) {
				ret = 4802;
			} else if (country.startsWith("黑龙江省鸡西市")) {
				ret = 4803;
			} else if (country.startsWith("黑龙江省双鸭山市")) {
				ret = 4804;
			} else if (country.startsWith("黑龙江省大庆市")) {
				ret = 4805;
			} else if (country.startsWith("黑龙江省伊春市")) {
				ret = 4806;
			} else if (country.startsWith("黑龙江省佳木斯市")) {
				ret = 4807;
			} else if (country.startsWith("黑龙江省七台河市")) {
				ret = 4808;
			} else if (country.startsWith("黑龙江省牡丹江市")) {
				ret = 4809;
			} else if (country.startsWith("黑龙江省黑河市")) {
				ret = 4810;
			} else if (country.startsWith("黑龙江省绥化市")) {
				ret = 4811;
			} else if (country.startsWith("黑龙江省大兴安岭")) {
				ret = 4812;
			} else if (country.startsWith("黑龙江省鹤岗市")) {
				ret = 4813;
			} else if (country.startsWith("黑龙江省-")) {
				ret = 4815;
			} else {
				ret = 4814;
			}
			// et = 8;
		} else if (country.startsWith("安徽省")) {
			if (country.startsWith("安徽省合肥")) {
				ret = 1101;
			} else if (country.startsWith("安徽省芜湖")) {
				ret = 1102;
			} else if (country.startsWith("安徽省蚌埠")) {
				ret = 1103;
			} else if (country.startsWith("安徽省淮南")) {
				ret = 1104;
			} else if (country.startsWith("安徽省马鞍山")) {
				ret = 1105;
			} else if (country.startsWith("安徽省淮北")) {
				ret = 1106;
			} else if (country.startsWith("安徽省铜陵")) {
				ret = 1107;
			} else if (country.startsWith("安徽省安庆")) {
				ret = 1108;
			} else if (country.startsWith("安徽省黄山")) {
				ret = 1109;
			} else if (country.startsWith("安徽省滁州")) {
				ret = 1110;
			} else if (country.startsWith("安徽省阜阳")) {
				ret = 1111;
			} else if (country.startsWith("安徽省宿州")) {
				ret = 1112;
			} else if (country.startsWith("安徽省巢湖")) {
				ret = 1113;
			} else if (country.startsWith("安徽省六安")) {
				ret = 1114;
			} else if (country.startsWith("安徽省毫州")) {
				ret = 1115;
			} else if (country.startsWith("安徽省池州")) {
				ret = 1116;
			} else if (country.startsWith("安徽省宜城")) {
				ret = 1117;
			} else if (country.startsWith("安徽省-")) {
				ret = 1119;
			} else {
				ret = 1118;
			}
			// ret = 11;

		} else if (country.startsWith("福建省")) {
			if (country.startsWith("福建省福州")) {
				ret = 1201;
			} else if (country.startsWith("福建省厦门")) {
				ret = 1202;
			} else if (country.startsWith("福建省莆田")) {
				ret = 1203;
			} else if (country.startsWith("福建省三明")) {
				ret = 1204;
			} else if (country.startsWith("福建省泉州")) {
				ret = 1205;
			} else if (country.startsWith("福建省漳州")) {
				ret = 1206;
			} else if (country.startsWith("福建省南平")) {
				ret = 1207;
			} else if (country.startsWith("福建省龙岩")) {
				ret = 1208;
			} else if (country.startsWith("福建省宁德")) {
				ret = 1209;
			} else if (country.startsWith("福建省-")) {
				ret = 1211;
			} else {
				ret = 1210;
			}
			// ret = 12;
		} else if (country.startsWith("江西省")) {
			if (country.startsWith("江西省南昌")) {
				ret = 1301;
			} else if (country.startsWith("江西省景德镇")) {
				ret = 1302;
			} else if (country.startsWith("江西省萍乡")) {
				ret = 1303;
			} else if (country.startsWith("江西省九江")) {
				ret = 1304;
			} else if (country.startsWith("江西省新余")) {
				ret = 1305;
			} else if (country.startsWith("江西省鹰潭")) {
				ret = 1306;
			} else if (country.startsWith("江西省赣州")) {
				ret = 1307;
			} else if (country.startsWith("江西省吉安")) {
				ret = 1308;
			} else if (country.startsWith("江西省宜春")) {
				ret = 1309;
			} else if (country.startsWith("江西省抚州")) {
				ret = 1310;
			} else if (country.startsWith("江西省上饶")) {
				ret = 1311;
			} else {
				ret = 1312;
			}
			// ret=13;
		} else if (country.startsWith("河南省")) {
			if (country.startsWith("河南省郑州")) {
				ret = 1501;
			} else if (country.startsWith("河南省开封")) {
				ret = 1502;
			} else if (country.startsWith("河南省洛阳")) {
				ret = 1503;
			} else if (country.startsWith("河南省平顶山")) {
				ret = 1504;
			} else if (country.startsWith("河南省焦作")) {
				ret = 1505;
			} else if (country.startsWith("河南省鹤壁")) {
				ret = 1506;
			} else if (country.startsWith("河南省新乡")) {
				ret = 1507;
			} else if (country.startsWith("河南省安阳")) {
				ret = 1508;
			} else if (country.startsWith("河南省濮阳")) {
				ret = 1509;
			} else if (country.startsWith("河南省许昌")) {
				ret = 1510;
			} else if (country.startsWith("河南省漯河")) {
				ret = 1511;
			} else if (country.startsWith("河南省三门峡")) {
				ret = 1512;
			} else if (country.startsWith("河南省南阳")) {
				ret = 1513;
			} else if (country.startsWith("河南省商丘")) {
				ret = 1514;
			} else if (country.startsWith("河南省信阳")) {
				ret = 1515;
			} else if (country.startsWith("河南省周口")) {
				ret = 1516;
			} else if (country.startsWith("河南省驻马店")) {
				ret = 1517;
			} else if (country.startsWith("河南省-")) {
				ret = 1519;
			} else {
				ret = 1518;
			}
			// ret=15;
		} else if (country.startsWith("湖北省")) {
			if (country.startsWith("湖北省武汉")) {
				ret = 1601;
			} else if (country.startsWith("湖北省黄石")) {
				ret = 1602;
			} else if (country.startsWith("湖北省襄樊")) {
				ret = 1603;
			} else if (country.startsWith("湖北省十堰")) {
				ret = 1604;
			} else if (country.startsWith("湖北省荆州")) {
				ret = 1605;
			} else if (country.startsWith("湖北省宜昌")) {
				ret = 1606;
			} else if (country.startsWith("湖北省荆门")) {
				ret = 1607;
			} else if (country.startsWith("湖北省鄂州")) {
				ret = 1608;
			} else if (country.startsWith("湖北省孝感")) {
				ret = 1609;
			} else if (country.startsWith("湖北省黄冈")) {
				ret = 1610;
			} else if (country.startsWith("湖北省咸宁")) {
				ret = 1611;
			} else if (country.startsWith("湖北省随州")) {
				ret = 1612;
			} else if (country.startsWith("湖北省恩施")) {
				ret = 1613;
			} else if (country.startsWith("湖北省-")) {
				ret = 1615;
			} else {
				ret = 1614;
			}

		} else if (country.startsWith("湖南省")) {
			if (country.startsWith("湖南省长沙")) {
				ret = 1701;
			} else if (country.startsWith("湖南省株洲")) {
				ret = 1702;
			} else if (country.startsWith("湖南省湘潭")) {
				ret = 1703;
			} else if (country.startsWith("湖南省衡阳")) {
				ret = 1704;
			} else if (country.startsWith("湖南省邵阳")) {
				ret = 1705;
			} else if (country.startsWith("湖南省岳阳")) {
				ret = 1706;
			} else if (country.startsWith("湖南省常德")) {
				ret = 1707;
			} else if (country.startsWith("湖南省张家界")) {
				ret = 1708;
			} else if (country.startsWith("湖南省益阳")) {
				ret = 1709;
			} else if (country.startsWith("湖南省郴州")) {
				ret = 1710;
			} else if (country.startsWith("湖南省永州")) {
				ret = 1711;
			} else if (country.startsWith("湖南省怀化")) {
				ret = 1712;
			} else if (country.startsWith("湖南省娄底")) {
				ret = 1713;
			} else if (country.startsWith("湖南省湘西")) {
				ret = 1714;
			} else if (country.startsWith("湖南省-")) {
				ret = 1716;
			} else {
				ret = 1715;
			}

		} else if (country.startsWith("海南省")) {
			ret = Province.HAINAN;
		} else if (country.startsWith("四川")) {
			if (country.startsWith("四川省成都")) {
				ret = 2001;
			} else if (country.startsWith("四川省自贡")) {
				ret = 2002;
			} else if (country.startsWith("四川省攀枝花")) {
				ret = 2003;
			} else if (country.startsWith("四川省泸州")) {
				ret = 2004;
			} else if (country.startsWith("四川省德阳")) {
				ret = 2005;
			} else if (country.startsWith("四川省绵阳")) {
				ret = 2006;
			} else if (country.startsWith("四川省广元")) {
				ret = 2007;
			} else if (country.startsWith("四川省遂宁")) {
				ret = 2008;
			} else if (country.startsWith("四川省内江")) {
				ret = 2009;
			} else if (country.startsWith("四川省乐山")) {
				ret = 2010;
			} else if (country.startsWith("四川省南充")) {
				ret = 2011;
			} else if (country.startsWith("四川省宜宾")) {
				ret = 2012;
			} else if (country.startsWith("四川省广安")) {
				ret = 2013;
			} else if (country.startsWith("四川省达州")) {
				ret = 2014;
			} else if (country.startsWith("四川省眉山")) {
				ret = 2015;
			} else if (country.startsWith("四川省雅安")) {
				ret = 2016;
			} else if (country.startsWith("四川省巴山")) {
				ret = 2017;
			} else if (country.startsWith("四川省资阳")) {
				ret = 2018;
			} else if (country.startsWith("四川省阿坝")) {
				ret = 2019;
			} else if (country.startsWith("四川省甘孜")) {
				ret = 2020;
			} else if (country.startsWith("四川省凉山")) {
				ret = 2021;
			} else if (country.startsWith("四川省巴中")) {
				ret = 2022;
			} else if (country.startsWith("四川省-")) {
				ret = 2024;
			} else {
				ret = 2023;
			}

			// ret = 20;
		} else if (country.startsWith("贵州省")) {
			ret = Province.GUIZHOU;
		} else if (country.startsWith("云南")) {
			ret = Province.YUNNAN;
		} else if (country.startsWith("陕西")) {
			if (country.startsWith("陕西省西安")) {
				ret = 2301;
			} else if (country.startsWith("陕西省铜川")) {
				ret = 2302;
			} else if (country.startsWith("陕西省宝鸡")) {
				ret = 2303;
			} else if (country.startsWith("陕西省咸阳")) {
				ret = 2304;
			} else if (country.startsWith("陕西省渭南")) {
				ret = 2305;
			} else if (country.startsWith("陕西省延安")) {
				ret = 2306;
			} else if (country.startsWith("陕西省汉中")) {
				ret = 2307;
			} else if (country.startsWith("陕西省榆林")) {
				ret = 2308;
			} else if (country.startsWith("陕西省安康")) {
				ret = 2309;
			} else if (country.startsWith("陕西省商洛")) {
				ret = 2310;
			} else if (country.startsWith("陕西省-")) {
				ret = 2312;
			} else {
				ret = 2311;
			}
			// ret=23;

		} else if (country.startsWith("甘肃省")) {
			ret = Province.GANSU;
		} else if (country.startsWith("青海省")) {
			ret = Province.QINGHAI;
		} else if (country.startsWith("广东省")) {
			if (country.startsWith("广东省广州市")) {
				ret = 1801;
			} else if (country.startsWith("广东省韶关市")) {
				ret = 1802;
			} else if (country.startsWith("广东省深圳市")) {
				ret = 1803;
			} else if (country.startsWith("广东省珠海市")) {
				ret = 1804;
			} else if (country.startsWith("广东省汕头市")) {
				ret = 1805;
			} else if (country.startsWith("广东省佛山市")) {
				ret = 1806;
			} else if (country.startsWith("广东省湛江市")) {
				ret = 1807;
			} else if (country.startsWith("广东省江门市")) {
				ret = 1808;
			} else if (country.startsWith("广东省茂名市")) {
				ret = 1809;
			} else if (country.startsWith("广东省肇庆市")) {
				ret = 1810;
			} else if (country.startsWith("广东省惠州市")) {
				ret = 1811;
			} else if (country.startsWith("广东省梅州市")) {
				ret = 1812;
			} else if (country.startsWith("广东省汕尾市")) {
				ret = 1813;
			} else if (country.startsWith("广东省河源市")) {
				ret = 1814;
			} else if (country.startsWith("广东省阳江市")) {
				ret = 1815;
			} else if (country.startsWith("广东省清远市")) {
				ret = 1816;
			} else if (country.startsWith("广东省东莞市")) {
				ret = 1817;
			} else if (country.startsWith("广东省中山市")) {
				ret = 1818;
			} else if (country.startsWith("广东省潮州市")) {
				ret = 1819;
			} else if (country.startsWith("广东省揭阳市")) {
				ret = 1820;
			} else if (country.startsWith("广东省云浮市")) {
				ret = 1821;
			} else if (country.startsWith("广东省-")) {
				ret = 1823;
			} else {
				ret = 1822;
			}
		} else if (country.startsWith("浙江省")) {
			if (country.startsWith("浙江省杭州市")) {
				ret = 1001;
			} else if (country.startsWith("浙江省宁波市")) {
				ret = 1002;
			} else if (country.startsWith("浙江省温州市")) {
				ret = 1003;
			} else if (country.startsWith("浙江省嘉兴市")) {
				ret = 1004;
			} else if (country.startsWith("浙江省湖州市")) {
				ret = 1005;
			} else if (country.startsWith("浙江省绍兴市")) {
				ret = 1006;
			} else if (country.startsWith("浙江省金华市")) {
				ret = 1007;
			} else if (country.startsWith("浙江省舟山市")) {
				ret = 1008;
			} else if (country.startsWith("浙江省台州市")) {
				ret = 1009;
			} else if (country.startsWith("浙江省丽水市")) {
				ret = 1010;
			} else if (country.startsWith("浙江省衢州市")) {
				ret = 1011;
			} else if (country.startsWith("浙江省-")) {
				ret = 1013;
			} else {
				ret = 1012;
			}
		} else if (country.startsWith("江苏省")) {
			if (country.startsWith("江苏省南京市")) {
				ret = 4901;
			} else if (country.startsWith("江苏省无锡市")) {
				ret = 4902;
			} else if (country.startsWith("江苏省徐州市")) {
				ret = 4903;
			} else if (country.startsWith("江苏省常州市")) {
				ret = 4904;
			} else if (country.startsWith("江苏省苏州市")) {
				ret = 4905;
			} else if (country.startsWith("江苏省南通市")) {
				ret = 4906;
			} else if (country.startsWith("江苏省连云港市")) {
				ret = 4907;
			} else if (country.startsWith("江苏省淮安市")) {
				ret = 4908;
			} else if (country.startsWith("江苏省盐城市")) {
				ret = 4909;
			} else if (country.startsWith("江苏省扬州市")) {
				ret = 4910;
			} else if (country.startsWith("江苏省镇江市")) {
				ret = 4911;
			} else if (country.startsWith("江苏省泰州市")) {
				ret = 4912;
			} else if (country.startsWith("江苏省宿迁市")) {
				ret = 4913;
			} else if (country.startsWith("江苏省-")) {
				ret = 4915;
			} else {
				ret = 4914;
			}
		} else if (country.startsWith("山东省")) {
			if (country.startsWith("山东省青岛市")) {
				ret = 1401;
			} else if (country.startsWith("山东省菏泽市")) {
				ret = 1402;
			} else if (country.startsWith("山东省莱芜市")) {
				ret = 1403;
			} else if (country.startsWith("山东省聊城市")) {
				ret = 1404;
			} else if (country.startsWith("山东省烟台市")) {
				ret = 1405;
			} else if (country.startsWith("山东省潍坊市")) {
				ret = 1406;
			} else if (country.startsWith("山东省滨州市")) {
				ret = 1407;
			} else if (country.startsWith("山东省淄博市")) {
				ret = 1408;
			} else if (country.startsWith("山东省济宁市")) {
				ret = 1409;
			} else if (country.startsWith("山东省济南市")) {
				ret = 1410;
			} else if (country.startsWith("山东省泰安市")) {
				ret = 1411;
			} else if (country.startsWith("山东省枣庄市")) {
				ret = 1412;
			} else if (country.startsWith("山东省日照市")) {
				ret = 1413;
			} else if (country.startsWith("山东省德州市")) {
				ret = 1414;
			} else if (country.startsWith("山东省威海市")) {
				ret = 1415;
			} else if (country.startsWith("山东省临沂市")) {
				ret = 1416;
			} else if (country.startsWith("山东省东营市")) {
				ret = 1417;
			} else if (country.startsWith("山东省-")) {
				ret = 1419;
			} else {
				ret = 1418;
			}
			// ret = 14;
		} else if (country.startsWith("北京")) {
			ret = Province.BEIJING;
		} else if (country.startsWith("上海")) {
			ret = Province.SHANGHAI;
		} else if (country.startsWith("天津")) {
			ret = Province.TIANJIN;
		} else if (country.startsWith("重庆")) {
			ret = Province.CHONGQING;
		} else if (country.startsWith("台湾")) {
			ret = Province.TAIWAN;
		} else if (country.startsWith("广西")) {
			if (country.startsWith("广西南宁市")) {
				ret = 2701;
			} else if (country.startsWith("广西柳州市")) {
				ret = 2702;
			} else if (country.startsWith("广西桂林市")) {
				ret = 2703;
			} else if (country.startsWith("广西梧州市")) {
				ret = 2704;
			} else if (country.startsWith("广西北海市")) {
				ret = 2705;
			} else if (country.startsWith("广西防城港市")) {
				ret = 2706;
			} else if (country.startsWith("广西钦州市")) {
				ret = 2707;
			} else if (country.startsWith("广西贵港市")) {
				ret = 2708;
			} else if (country.startsWith("广西玉林市")) {
				ret = 2709;
			} else if (country.startsWith("广西白色市")) {
				ret = 2710;
			} else if (country.startsWith("广西贺州市")) {
				ret = 2711;
			} else if (country.startsWith("广西河池市")) {
				ret = 2712;
			} else if (country.startsWith("广西来宾市")) {
				ret = 2713;
			} else if (country.startsWith("广西崇左市")) {
				ret = 2714;
			} else {
				ret = 2715;
			}
			// ret=27;
		} else if (country.startsWith("内蒙古")) {
			ret = Province.NEIMENGGU;
		} else if (country.startsWith("西藏")) {
			ret = Province.XIZHANG;
		} else if (country.startsWith("宁夏")) {
			ret = Province.NINGXIA;
		} else if (country.startsWith("新疆")) {
			ret = Province.XINJIANG;
		} else if (country.startsWith("香港")) {
			ret = Province.HONGKONG;
		} else if (country.startsWith("澳门")) {
			ret = Province.MACAU;
		} else if (country.startsWith("未知")) {
			ret = Province.OTHER;
		} else if (country.indexOf("大学") != -1 || country.indexOf("学院") != -1) {
			ret = Province.College;

		}else if(country.indexOf("CZ88.NET")!=-1){
			ret = Province.CZ88;
		}else if(country.startsWith("亚太地区")){
			ret = Province.YATAI;
		}

		/*
		 * StringBuffer sb=new StringBuffer();
		 * sb.append(country).append("^").append(area);
		 */
		return ret;
		// IPLocation ipcopy=getCopy();
		// return sb.toString();
	}

	public static String getProvinceName(int code) {
		String area = "其他";
		switch (code) {
		case 40:
			area = "北京市";
			break;
		case 41:
			area = "天津市";
			break;
		case 42:
			area = "上海市";
			break;
		case 43:
			area = "重庆市";
			break;
		case 44:
			area = "河北省";
			break;
		case 45:
			area = "山西省";
			break;
		case 46:
			area = "辽宁省";
			break;
		case 47:
			area = "吉林省";
			break;
		case 48:
			area = "黑龙江省";
			break;
		case 49:
			area = "江苏省";
			break;
		case 10:
			area = "浙江省";
			break;
		case 11:
			area = "安徽省";
			break;
		case 12:
			area = "福建省";
			break;
		case 13:
			area = "江西省";
			break;
		case 14:
			area = "山东省";
			break;
		case 15:
			area = "河南省";
			break;
		case 16:
			area = "湖北省";
			break;
		case 17:
			area = "湖南省";
			break;

		case 18:
			area = "广东省";
			break;

		case 19:
			area = "海南省";
			break;

		case 20:
			area = "四川省";
			break;

		case 21:
			area = "贵州省";
			break;

		case 22:
			area = "云南省";
			break;

		case 23:
			area = "陕西省";
			break;

		case 24:
			area = "甘肃省";
			break;

		case 25:
			area = "青海省";
			break;

		case 26:
			area = "台湾";
			break;

		case 27:
			area = "广西";
			break;

		case 28:
			area = "内蒙古";
			break;

		case 29:
			area = "西藏";
			break;

		case 30:
			area = "宁夏";
			break;

		case 31:
			area = "新疆";
			break;

		case 32:
			area = "香港";
			break;
		case 33:
			area = "澳门";
			break;	
		case 34:
			area = "未知国家";
			break;
		case 1801:
			area = "广东省广州市";
			break;	
		case 1802:
			area = "广东省韶关市";
			break;	
		case 1803:
			area = "广东省深圳市";
			break;	
		case 1804:
			area = "广东省珠海市";
			break;	
		case 1805:
			area = "广东省汕头市";
			break;	
		case 1806:
			area = "广东省佛山市";
			break;	
		case 1807:
			area = "广东省湛江市";
			break;	
		case 1808:
			area = "广东省江门市";
			break;	
		case 1809:
			area = "广东省茂名市";
			break;	
		case 1810:
			area = "广东省肇庆市";
			break;	
		case 1811:
			area = "广东省惠州市";
			break;	
		case 1812:
			area = "广东省梅州市";
			break;	
		case 1813:
			area = "广东省汕尾市";
			break;	
		case 1814:
			area = "广东省河源市";
			break;	
		case 1815:
			area = "广东省阳江市";
			break;
		case 1816:
			area = "广东省清远市";
			break;	
		case 1817:
			area = "广东省东莞市";
			break;	
		case 1818:
			area = "广东省中山市";
			break;	
		case 1819:
			area = "广东省潮州市";
			break;	
		case 1820:
			area = "广东省揭阳市";
			break;	
		case 1821:
			area = "广东省云浮市";
			break;	
		case 1822:
			area = "广东省-其他";
			break;
		case 1823:
			area = "广东省-各大学";
			break;
		case 1001:
			area = "浙江省杭州市";
			break;	
		case 1002:
			area = "浙江省宁波市";
			break;
		case 1003:
			area = "浙江省温州市";
			break;
		case 1004:
			area = "浙江省嘉兴市";
			break;
		case 1005:
			area = "浙江省湖州市";
			break;
		case 1006:
			area = "浙江省绍兴市";
			break;
		case 1007:
			area = "浙江省金华市";
			break;
		case 1008:
			area = "浙江省舟山市";
			break;
		case 1009:
			area = "浙江省台州市";
			break;	
		case 1010:
			area = "浙江省丽水市";
			break;
		case 1011:
			area = "浙江省衢州市";
			break;
		case 1012:
			area = "浙江省-其他";
			break;
		case 1013:
			area = "浙江省-各大学";
			break;
		case 4901:
			area = "江苏省南京市";
			break;
		case 4902:
			area = "江苏省无锡市";
			break;
		case 4903:
			area = "江苏省徐州市";
			break;
		case 4904:
			area = "江苏省常州市";
			break;
		case 4905:
			area = "江苏省苏州市";
			break;
		case 4906:
			area = "江苏省南通市";
			break;
		case 4907:
			area = "江苏省连云港市";
			break;
		case 4908:
			area = "江苏省淮安市";
			break;
		case 4909:
			area = "江苏省盐城市";
			break;
		case 4910:
			area = "江苏省扬州市";
			break;
		case 4911:
			area = "江苏省镇江市";
			break;
		case 4912:
			area = "江苏省泰州市";
			break;
		case 4913:
			area = "江苏省宿迁市";
			break;
		case 4914:
			area = "江苏省-其他";
			break;	
		case 4915:
			area = "江苏省-各大学";
			break;	
		case 1401:
			area = "山东省青岛市";
			break;	
		case 1402:
			area = "山东省菏泽市";
			break;	
		case 1403:
			area = "山东省莱芜市";
			break;	
		case 1404:
			area = "山东省聊城市";
			break;	
		case 1405:
			area = "山东省烟台市";
			break;	
		case 1406:
			area = "山东省潍坊市";
			break;	
		case 1407:
			area = "山东省滨州市";
			break;	
		case 1408:
			area = "山东省淄博市";
			break;	
		case 1409:
			area = "山东省济宁市";
			break;	
		case 1410:
			area = "山东省济南市";
			break;	
		case 1411:
			area = "山东省泰安市";
			break;	
		case 1412:
			area = "山东省枣庄市";
			break;	
		case 1413:
			area = "山东省日照市";
			break;	
		case 1414:
			area = "山东省德州市";
			break;	
		case 1415:
			area = "山东省威海市";
			break;	
		case 1416:
			area = "山东省临沂市";
			break;
		case 1417:
			area = "山东省东营市";
			break;
		case 1418:
			area = "山东省-其他";
			break;
		case 1419:
			area = "山东省-各大学";
			break;
		case 4801:
			area = "黑龙江省哈尔滨市";
			break;
		case 4802:
			area = "黑龙江省齐齐哈尔市";
			break;
		case 4803:
			area = "黑龙江省鸡西市";
			break;
		case 4813:
			area = "黑龙江省鹤岗市";
			break;
		case 4804:
			area = "黑龙江省双鸭山市";
			break;
		case 4805:
			area = "黑龙江省大庆市";
			break;
		case 4806:
			area = "黑龙江省伊春市";
			break;
		case 4807:
			area = "黑龙江省佳木斯市";
			break;
		case 4808:
			area = "黑龙江省七台河市";
			break;
		case 4809:
			area = "黑龙江省牡丹江市";
			break;
		case 4810:
			area = "黑龙江省黑河市";
			break;
		case 4811:
			area = "黑龙江省绥化市";
			break;
		case 4812:
			area = "黑龙江省大兴安岭";
			break;
		case 4814:
			area = "黑龙江省-其他";
			break;
		case 4815:
			area = "黑龙江省-各大学";
			break;
		case 1601:
			area = "湖北省武汉市";
			break;
		case 1602:
			area = "湖北省黄石市";
			break;
		case 1603:
			area = "湖北省襄樊市";
			break;
		case 1604:
			area = "湖北省十堰市";
			break;
		case 1605:
			area = "湖北省荆州市";
			break;
		case 1606:
			area = "湖北省宜昌市";
			break;
		case 1607:
			area = "湖北省荆门市";
			break;
		case 1608:
			area = "湖北省鄂州市";
			break;
		case 1609:
			area = "湖北省孝感市";
			break;
		case 1610:
			area = "湖北省黄冈市";
			break;
		case 1611:
			area = "湖北省咸宁市";
			break;
		case 1612:
			area = "湖北省随州市";
			break;
		case 1613:
			area = "湖北省恩施市";
			break;
		case 1614:
			area = "湖北省-其他";
			break;
		case 1615:
			area = "湖北省-各大学";
			break;
		case 1701:
			area = "湖南省长沙市";
			break;
		case 1702:
			area = "湖南省株洲市";
			break;
		case 1703:
			area = "湖南省湘潭市";
			break;
		case 1704:
			area = "湖南省衡阳市";
			break;
		case 1705:
			area = "湖南省邵阳市";
			break;
		case 1706:
			area = "湖南省岳阳市";
			break;
		case 1707:
			area = "湖南省常德市";
			break;
		case 1708:
			area = "湖南省张家界市";
			break;
		case 1709:
			area = "湖南省益阳市";
			break;
		case 1710:
			area = "湖南省郴州市";
			break;
		case 1711:
			area = "湖南省永州市";
			break;
		case 1712:
			area = "湖南省怀化市";
			break;
		case 1713:
			area = "湖南省娄底市";
			break;
		case 1714:
			area = "湖南省湘西市";
			break;
		case 1715:
			area = "湖南省其他市";
			break;
		case 1716:
			area = "湖南省-各大学";
			break;
		case 1501:
			area = "河南省郑州市";
			break;
		case 1502:
			area = "河南省开封市";
			break;
		case 1503:
			area = "河南省洛阳市";
			break;
		case 1504:
			area = "河南省平顶山市";
			break;
		case 1505:
			area = "河南省焦作市";
			break;
		case 1506:
			area = "河南省鹤壁市";
			break;
		case 1507:
			area = "河南省新乡市";
			break;
		case 1508:
			area = "河南省安阳市";
			break;
		case 1509:
			area = "河南省濮阳市";
			break;
		case 1510:
			area = "河南省许昌市";
			break;
		case 1511:
			area = "河南省漯河市";
			break;
		case 1512:
			area = "河南省三门峡市";
			break;
		case 1513:
			area = "河南省南阳市";
			break;
		case 1514:
			area = "河南省商丘市";
			break;
		case 1515:
			area = "河南省信阳市";
			break;
		case 1516:
			area = "河南省周口市";
			break;
		case 1517:
			area = "河南省驻马店市";
			break;
		case 1518:
			area = "河南省-其他市";
			break;
		case 1519:
			area = "河南省-各大学";
			break;
		case 4401:
			area = "河北省石家庄市";
			break;
		case 4402:
			area = "河北省唐山市";
			break;
		case 4403:
			area = "河北省秦皇岛市";
			break;
		case 4404:
			area = "河北省邯郸市";
			break;
		case 4405:
			area = "河北省邢台市";
			break;
		case 4406:
			area = "河北省保定市";
			break;
		case 4407:
			area = "河北省张家口市";
			break;
		case 4408:
			area = "河北省承德市";
			break;
		case 4411:
			area = "河北省沧州市";
			break;
		case 4409:
			area = "河北省廊坊市";
			break;
		case 4410:
			area = "河北省衡水市";
			break;
		case 4412:
			area = "河北省其他市";
			break;
		case 4413:
			area = "河北省-各大学";
			break;
		case 2001:
			area = "四川省成都市";
			break;
		case 2002:
			area = "四川省自贡市";
			break;
		case 2003:
			area = "四川省攀枝花市";
			break;
		case 2004:
			area = "四川省泸州市";
			break;
		case 2005:
			area = "四川省德阳市";
			break;
		case 2006:
			area = "四川省绵阳市";
			break;
		case 2007:
			area = "四川省广元市";
			break;
		case 2008:
			area = "四川省遂宁市";
			break;
		case 2009:
			area = "四川省内江市";
			break;
		case 2010:
			area = "四川省乐山市";
			break;
		case 2011:
			area = "四川省南充市";
			break;
		case 2012:
			area = "四川省宜宾市";
			break;
		case 2013:
			area = "四川省广安市";
			break;
		case 2014:
			area = "四川省达州市";
			break;
		case 2015:
			area = "四川省眉山市";
			break;
		case 2016:
			area = "四川省雅安市";
			break;
		case 2017:
			area = "四川省巴山市";
			break;
		case 2018:
			area = "四川省资阳市";
			break;
		case 2019:
			area = "四川省阿坝市";
			break;
		case 2020:
			area = "四川省甘孜市";
			break;
		case 2021:
			area = "四川省凉山市";
			break;
		case 2022:
			area = "四川省巴中市";
			break;
		case 2023:
			area = "四川省其他市";
			break;
		case 2024:
			area = "四川省-各大学";
			break;
		case 4601:
			area = "辽宁省沈阳市";
			break;
		case 4602:
			area = "辽宁省大连市";
			break;
		case 4603:
			area = "辽宁省鞍山市";
			break;
		case 4604:
			area = "辽宁省抚顺市";
			break;
		case 4605:
			area = "辽宁省本溪市";
			break;
		case 4606:
			area = "辽宁省丹东市";
			break;
		case 4607:
			area = "辽宁省锦州市";
			break;
		case 4608:
			area = "辽宁省营口市";
			break;
		case 4609:
			area = "辽宁省阜新市";
			break;
		case 4610:
			area = "辽宁省辽阳市";
			break;
		case 4611:
			area = "辽宁省盘锦市";
			break;
		case 4612:
			area = "辽宁省铁岭市";
			break;
		case 4613:
			area = "辽宁省朝阳市";
			break;
		case 4614:
			area = "辽宁省葫芦岛市";
			break;
		case 4615:
			area = "辽宁省-其他市";
			break;
		case 4616:
			area = "辽宁省-各大学";
			break;
		case 1201:
			area = "福建省福州市";
			break;
		case 1202:
			area = "福建省厦门市";
			break;
		case 1203:
			area = "福建省莆田市";
			break;
		case 1204:
			area = "福建省三明市";
			break;
		case 1205:
			area = "福建省泉州市";
			break;
		case 1206:
			area = "福建省漳州市";
			break;
		case 1207:
			area = "福建省南平市";
			break;
		case 1208:
			area = "福建省龙岩市";
			break;
		case 1209:
			area = "福建省宁德市";
			break;
		case 1210:
			area = "福建省-其他市";
			break;
		case 1211:
			area = "福建省-各大学";
			break;
		case 4701:
			area = "吉林省长春市";
			break;
		case 4702:
			area = "吉林省吉林市";
			break;
		case 4703:
			area = "吉林省四平市";
			break;
		case 4704:
			area = "吉林省辽源市";
			break;
		case 4705:
			area = "吉林省通化市";
			break;
		case 4706:
			area = "吉林省白山市";
			break;
		case 4707:
			area = "吉林省松原市";
			break;
		case 4708:
			area = "吉林省白城市";
			break;
		case 4709:
			area = "吉林省延边市";
			break;
		case 4710:
			area = "吉林省-其他市";
			break;
		case 4711:
			area = "吉林省-各大学";
			break;
		case 1101:
			area = "安徽省合肥市";
			break;
		case 1102:
			area = "安徽省芜湖市";
			break;
		case 1103:
			area = "安徽省蚌埠市";
			break;
		case 1104:
			area = "安徽省淮南市";
			break;
		case 1105:
			area = "安徽省马鞍山市";
			break;
		case 1106:
			area = "安徽省淮北市";
			break;
		case 1107:
			area = "安徽省铜陵市";
			break;
		case 1108:
			area = "安徽省安庆市";
			break;
		case 1109:
			area = "安徽省黄山市";
			break;
		case 1110:
			area = "安徽省滁州市";
			break;
		case 1111:
			area = "安徽省阜阳市";
			break;
		case 1112:
			area = "安徽省宿州市";
			break;
		case 1113:
			area = "安徽省巢湖市";
			break;
		case 1114:
			area = "安徽省六安市";
			break;
		case 1115:
			area = "安徽省毫州市";
			break;
		case 1116:
			area = "安徽省池州市";
			break;
		case 1117:
			area = "安徽省宜城市";
			break;
		case 1118:
			area = "安徽省-其他市";
			break;
		case 1119:
			area = "安徽省-各大学";
			break;
		case 1301:
			area = "江西省南昌市";
			break;
		case 1302:
			area = "江西省景德镇市";
			break;
		case 1303:
			area = "江西省萍乡市";
			break;
		case 1304:
			area = "江西省九江市";
			break;
		case 1305:
			area = "江西省新余市";
			break;
		case 1306:
			area = "江西省鹰潭市";
			break;
		case 1307:
			area = "江西省赣州市";
			break;
		case 1308:
			area = "江西省吉安市";
			break;
		case 1309:
			area = "江西省宜春市";
			break;
		case 1310:
			area = "江西省抚州市";
			break;
		case 1311:
			area = "江西省上饶市";
			break;
		case 1312:
			area = "江西省-其他市";
			break;
		case 1313:
			area = "江西省-各大学";
			break;
		case 501:
			area = "山西省太原市";
			break;
		case 502:
			area = "山西省大同市";
			break;
		case 4503:
			area = "山西省长治市";
			break;
		case 4504:
			area = "山西省晋城市";
			break;
		case 4505:
			area = "山西省朔州市";
			break;
		case 4506:
			area = "山西省晋中市";
			break;
		case 4507:
			area = "山西省运城市";
			break;
		case 4508:
			area = "山西省忻州市";
			break;
		case 4509:
			area = "山西省临汾市";
			break;
		case 4510:
			area = "山西省吕梁市";
			break;
		case 4511:
			area = "山西省阳泉市";
			break;
		case 4512:
			area = "山西省-其他市";
			break;
		case 4513:
			area = "山西省-各大学";
			break;
		case 2301:
			area = "陕西省西安市";
			break;
		case 2302:
			area = "陕西省铜川市";
			break;
		case 2303:
			area = "陕西省宝鸡市";
			break;
		case 2304:
			area = "陕西省咸阳市";
			break;
		case 2305:
			area = "陕西省渭南市";
			break;
		case 2306:
			area = "陕西省延安市";
			break;
		case 2307:
			area = "陕西省汉中市";
			break;
		case 2308:
			area = "陕西省榆林市";
			break;
		case 2309:
			area = "陕西省安康市";
			break;
		case 2310:
			area = "陕西省商洛市";
		case 2311:
			area = "陕西省-其他市";
		case 2312:
			area = "陕西省-各大学";
			break;
		case 2701:
			area = "广西省南宁市";
			break;
		case 2702:
			area = "广西省柳州市";
			break;
		case 2703:
			area = "广西省桂林市";
			break;
		case 2704:
			area = "广西省梧州市";
			break;
		case 2705:
			area = "广西省北海市";
			break;
		case 2706:
			area = "广西省防城港市";
			break;
		case 2707:
			area = "广西省钦州市";
			break;
		case 2708:
			area = "广西省贵港市";
			break;
		case 2709:
			area = "广西省玉林市";
			break;
		case 2710:
			area = "广西省白色市";
			break;
		case 2711:
			area = "广西省贺州市";
			break;
		case 2712:
			area = "广西省河池市";
			break;
		case 2713:
			area = "广西省来宾市";
			break;
		case 2714:
			area = "广西省崇左市";
			break;
		case 2715:
			area = "广西省其他市";
			break;
		case 35:
			area = "中国";
			break;
		}
		
		return area;

	}
	     
	public static String adaptProvinceName(String province) {
		if ("广西省".equals(province)) {
			province = "广西壮族自治区";
		} else if ("新疆".equals(province)) {
			province = "新疆维吾尔自治区";
		} else if ("澳门".equals(province)) {
			province = "澳门特别行政区";
		} else if ("香港".equals(province)) {
			province = "香港特别行政区";
		} else if ("宁夏".equals(province)) {
			province = "宁夏回族自治区";
		} else if ("内蒙古".equals(province)) {
			province = "内蒙古自治区";
		} else if ("abroad".equals(province)) {
			province = "未知";
		}
		return province;
	}
	
	
}

   

