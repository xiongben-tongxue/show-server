package one.show.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  地域和网络类型字典数据<br>
 * CreateTime:2014-12-18 16:54<br>
 * @author muweitao@cyou-inc.com
 */
public final class AreaNetCache extends java.util.concurrent.ConcurrentHashMap<Integer, String> {

    private static final long serialVersionUID = 5907745389172910771L;

    static {
        init();
    }

    public static ConcurrentHashMap<Integer, String> areaCache;
    public static ConcurrentHashMap<Integer, String> netCache;
    public static ConcurrentHashMap<String, Object> cityMap;
    public static ConcurrentHashMap<String, Object> city;
    public static ConcurrentHashMap<String, Object> provinceMap;

    private static void init() {
        String AREA = "10=浙江;11=安徽;12=福建;13=江西;14=山东;15=河南;16=湖北;17=湖南;18=广东;19=海南;1901=海口;20=四川;21=贵州;22=云南;23=陕西;24=甘肃;25=青海;26=台湾;27=广西壮族自治区;28=内蒙古;29=西藏自治区;30=宁夏回族自治区;31=新疆维吾尔自治区;32=香港特别行政区;33=澳门特别行政区;34=其它;35=中国;40=北京;4099=北京;41=天津;42=上海;4299=上海;43=重庆;44=河北;45=山西;46=辽宁;47=吉林;48=黑龙江;49=江苏;4401=石家庄;4402=唐山;4403=秦皇岛;4404=邯郸;4405=邢台;4406=保定;4407=张家口;4408=承德;4409=廊坊;4410=衡水;4411=沧州;4413=大学;4412=其它;4501=太原;4502=大同;4503=长治;4504=晋城;4505=朔州;4506=晋中;4507=运城;4508=忻州;4509=临汾;4510=吕梁;4511=阳泉;4512=其它;4513=大学;4601=沈阳;4602=大连;4603=鞍山;4604=抚顺;4605=本溪;4606=丹东;4607=锦州;4608=营口;4609=阜新;4610=辽阳;4611=盘锦;4612=铁岭;4613=朝阳;4614=葫芦岛;4615=其它;4616=大学;4701=长春;4702=吉林;4703=四平;4704=辽源;4705=通化;4706=白山;4707=松原;4708=白城;4709=延边;4710=其它;4801=哈尔滨;4802=齐齐哈尔;4803=鸡西;4804=双鸭山;4805=大庆;4806=伊春;4807=佳木斯;4808=七台河;4809=牡丹江;4810=黑河;4811=绥化;4812=大兴安岭;4813=鹤岗;4814=其它;4815=大学;1101=合肥;1102=芜湖;1103=蚌埠;1104=淮南;1105=马鞍山;1106=淮北;1107=铜陵;1108=安庆;1109=黄山;1110=滁州;1111=阜阳;1112=宿州;1113=巢湖;1114=六安;1115=毫州;1116=池州;1117=宜城;1118=其它;1119=大学;1201=福州;1202=厦门;1203=莆田;1204=三明;1205=泉州;1206=漳州;1207=南平;1208=龙岩;1209=宁德;1210=其它;1211=大学;1301=南昌;1302=景德镇;1303=萍乡;1304=九江;1305=新余;1306=鹰潭;1307=赣州;1308=吉安;1309=宜春;1310=抚州;1311=上饶;1312=其它;1501=郑州;1502=开封;1503=洛阳;1504=平顶山;1505=焦作;1506=鹤壁;1507=新乡;1508=安阳;1509=濮阳;1510=许昌;1511=漯河;1512=三门峡;1513=南阳;1514=商丘;1515=信阳;1516=周口;1517=驻马店;1518=其它;1519=大学;1601=武汉;1602=黄石;1603=襄樊;1604=十堰;1605=荆州;1606=宜昌;1607=荆门;1608=鄂州;1609=孝感;1610=黄冈;1611=咸宁;1612=随州;1613=恩施;1614=其它;1615=大学;1701=长沙;1702=株洲;1703=湘潭;1704=衡阳;1705=邵阳;1706=岳阳;1707=常德;1708=张家界;1709=益阳;1710=郴州;1711=永州;1712=怀化;1713=娄底;1714=湘西;1715=其它;1716=大学;2001=成都;2002=自贡;2003=攀枝花;2004=泸州;2005=德阳;2006=绵阳;2007=广元;2008=遂宁;2009=内江;2010=乐山;2011=南充;2012=宜宾;2013=广安;2014=达州;2015=眉山;2016=雅安;2017=巴山;2018=资阳;2019=阿坝;2020=甘孜;2021=凉山;2022=巴中;2023=其它;2024=大学;2301=西安;2302=铜川;2303=宝鸡;2304=咸阳;2305=渭南;2306=延安;2307=汉中;2308=榆林;2309=安康;2310=商洛;2311=其它;2312=大学;1801=广州;1802=韶关;1803=深圳;1804=珠海;1805=汕头;1806=佛山;1807=湛江;1808=江门;1809=茂名;1810=肇庆;1811=惠州;1812=梅州;1813=汕尾;1814=河源;1815=阳江;1816=清远;1817=东莞;1818=中山;1819=潮州;1820=揭阳;1821=云浮;1822=其它;1823=大学;1001=杭州;1002=宁波;1003=温州;1004=嘉兴;1005=湖州;1006=绍兴;1007=金华;1008=舟山;1009=台州;1010=丽水;1011=衢州;1012=其它;1013=大学;4901=南京;4902=无锡;4903=徐州;4904=常州;4905=苏州;4906=南通;4907=连云港;4908=淮安;4909=盐城;4910=扬州;4911=镇江;4912=泰州;4913=宿迁;4914=其它;4915=大学;1401=青岛;1402=菏泽;1403=莱芜;1404=聊城;1405=烟台;1406=潍坊;1407=滨州;1408=淄博;1409=济宁;1410=济南;1411=泰安;1412=枣庄;1413=日照;1414=德州;1415=威海;1416=临沂;1417=东营;1418=其它;1419=大学;2701=南宁;2702=柳州;2703=桂林;2704=梧州;2705=北海;2706=防城港;2707=钦州;2708=贵港;2709=玉林;2710=白色;2711=贺州;2712=河池;2713=来宾;2714=崇左;2715=其它;2801=呼和浩特;2802=包头;2803=乌海;2804=赤峰;2805=通辽;2806=鄂尔多斯;2807=呼伦贝尔;2808=巴彦淖尔;2809=乌兰察布;2810=锡林郭勒盟;2811=阿拉善盟;2812=兴安盟;2813=其它;39=搜狐公司;4001=海淀区;4002=朝阳区;4003=宣武区;4004=崇文区;4005=东城区;4006=西城区;4007=石景山区;4008=丰台区;4009=通州区;4010=昌平区;4011=大兴区;4012=怀柔区;4013=顺义区;4014=延庆县;4015=平谷区;4016=密云县;4017=房山区;4018=门头沟区;4019=未知;3101=乌鲁木齐市;3102=克拉玛依市;3103=吐鲁番市;3104=哈密地区;3105=昌吉市;3106=博乐市;3107=库尔勒市;3108=阿克苏市;3109=阿勒泰市;3110=塔城市;3111=伊宁市;3112=石河子市;3113=阜康市;3114=乌苏市;3115=奎屯市;3116=和田市;3117=喀什市;3118=伊犁州;3119=阿拉尔市;3120=巴音郭楞州;3121=五家渠市;3122=阿图什市;3123=博尔塔拉蒙古自治州;3124=鄯善市;3125=其它;2101=贵阳市;2102=六盘水市;2103=遵义市;2104=毕节地区;2105=铜仁地区;2106=安顺市;2107=黔东南苗族侗族自治州;2108=黔南布依族苗族自治州;2109=黔西南布依族苗族自治州;2110=其它;2201=昆明市;2202=曲靖市;2203=玉溪市;2204=丽江市;2205=昭通市;2206=普洱市;2207=临沧市;2208=保山市;2209=德宏傣族景颇族自治州;2210=怒江傈傈族自治州;2211=迪庆藏族自治州;2212=大理白族自治州;2213=楚雄彝族自治州;2214=红河哈尼族彝族自治州;2215=文山壮族苗族自治州;2216=西双版纳傣族自治州;2217=其它;2401=兰州市;2402=嘉峪关市;2403=金昌市;2404=白银市;2405=天水市;2406=酒泉市;2407=张掖市;2408=武威市;2409=庆阳市;2410=平凉市;2411=定西市;2412=陇南地区;2413=临夏回族自治州;2414=甘南藏族自治州;2415=玉门市;2416=敦煌市;2417=其它;3001=银川市;3002=石嘴山市;3003=吴忠市;3004=固原市;3005=中卫市;3006=其它;2501=西宁市;2502=海东地区;2503=海北藏族自治州;2504=海南藏族自治州;2505=黄南藏族自治州;2506=果洛藏族自治州;2507=玉树藏族自治州;2508=海西蒙古族藏族自治州;2509=其它;2901=拉萨市;2902=那曲地区;2903=昌都地区;2904=林芝地区;2905=山南地区;2906=日喀则地区;2907=阿里地区;2908=其它;50=美国;51=加拿大;52=澳大利亚;53=日本;54=韩国;55=菲律宾;56=印度;57=越南;58=马来西亚;59=新加坡;60=泰国;61=英国;62=法国;63=德国;64=俄罗斯;65=意大利;4020=北京前景;90=测试区域;4101=天津龙驰;4021=北京联通测试1;4022=北京中信NAT;9001=北京其它;9002=艾普-武汉;4050=清华大学;4051=北京大学;4052=北京邮电;4150=天津大学;4450=河北师范大学;4451=河北工业大学;4250=上海交通大学;4251=上海复旦大学;4252=华东师范大学;4253=上海大学;4254=同济大学;1850=华南理工大学;1851=华南师范大学;1852=暨南大学;1853=中山大学;2750=广西大学;2150=贵州大学;2350=西安电子科技大学;2351=西安交通大学;1650=武汉大学;1651=华中科技大学;1652=武汉理工大学;2050=西南交通大学;2051=电子科技大学;1250=福州大学;2450=兰州大学;1251=厦门大学;2250=云南大学;1050=浙江大学;4350=西南大学;4351=重庆大学;4650=辽宁大学;4651=大连理工大学;4652=东北大学;2850=内蒙古大学;1450=中国石油大学华东;1451=中国海洋大学;1452=山东大学;4550=太原理工大学;4750=吉林大学;1150=合肥工业大学;1151=中国科学技术大学;1152=安徽大学;4053=中国传媒大学;4054=华北电力大学;4055=中国石油大学;4056=北京林业大学;4057=中国农业大学;4058=中央民族大学;4059=北京外国语大学;4060=北京中医药大学;4061=对外经济贸易大学;4062=北京化工大学;4063=北京理工大学;4064=中国人民大学;4065=北京工业大学;4066=北京体育大学;4067=北京交通大学;4068=中国矿业大学北京;4069=北京航空航天大学;4070=北京科技大学;4071=中央财经大学;4072=北京师范大学;4073=中国地质大学(北京);4074=中国政法大学;4075=中央音乐学院;1854=广州中医药大学;2151=CERNET西南地区贵州主节点;4452=华北电力大学保定;1550=郑州大学;4850=哈尔滨工业大学;4851=东北林业大学;4852=东北农业大学;4853=哈尔滨工程大学;4751=东北师范大学;4752=延边大学;4950=河海大学;4951=南京大学;4952=南京航空航天大学;4953=南京理工大学;4954=南京农业大学;4955=南京师范大学;4956=中国药科大学;4957=苏州大学;4958=江南大学;4959=中国矿业大学;4960=东南大学;1350=南昌大学;4653=大连海事大学;2352=陕西师范大学;2353=西北农林科技大学;2354=第四军医大学;2355=长安大学;2356=西北大学;2357=西北工业大学;4255=东华大学;4256=华东理工大学;4257=上海财经大学;4258=上海外国语大学;4259=第二军医大学;2052=第二军医大学;2053=四川大学;2054=西南财经大学;4151=南开大学;4152=天津医科大学;3150=新疆大学;1750=国防科学技术大学;1751=湖南大学;1752=湖南师范大学;1753=中南大学;1754=长沙理工大学;1755=湖南农业大学;1756=湖南师范大学;1757=湖南文理学院;1758=南华大学;1759=中南林业科技大学;1760=湖南第一师范;1761=湖南理工学院;1762=湖南科技大学;1763=长沙大学;1653=华中师范大学;1654=中南财经政法大学;1655=中国地质大学(武汉);1656=华中农业大学;1657=中南财经政法大学;1658=湖北经济学院;1659=湖北工业大学;1660=湖北大学;4040=蓝宽小区;3301=澳门1;9003=广东联通测试1;9004=山西联通测试1;";
        String[] area_s = AREA.split(";");
        areaCache = new ConcurrentHashMap<Integer, String>(area_s.length);
        String[] _a = null;
        cityMap= new ConcurrentHashMap<String, Object>();
        provinceMap = new ConcurrentHashMap<String, Object>();
        for (String _area : area_s) {
            _a = _area.split("=");
            areaCache.put(Integer.valueOf(_a[0]), _a[1]);
            if(Integer.valueOf(_a[0]).intValue()>100){
            	if(_a[1].contains("其他")||_a[1].contains("大学")||_a[1].contains("未知")||_a[1].contains("测试")||_a[1].contains("地区")||_a[1].contains("NAT")){
            		continue;
            	}
            	if("上海市".equals(_a[1])){
            		System.out.println(_a[0]);
            	}
            	cityMap.put(_a[1], Integer.valueOf(_a[0]));
            }else if(Integer.valueOf(_a[0]).intValue()>=10&&Integer.valueOf(_a[0]).intValue()<=49&&Integer.valueOf(_a[0]).intValue()!=34&&Integer.valueOf(_a[0]).intValue()!=39&&Integer.valueOf(_a[0]).intValue()!=35){
            	provinceMap.put(_a[0], _a[1]);
            }
        }

        String NET_TYPE = "1=电信;2=联通;3=铁通;4=广电;5=歌华;6=电信通;7=教育;8=移动;9=网吧;10=其它;11=有线通;13=长城宽带;";
        String[] net_s = NET_TYPE.split(";");
        netCache = new ConcurrentHashMap<Integer, String>(net_s.length);
        String[] _n = null;
        for (String _net : net_s) {
            _n = _net.split("=");
            netCache.put(Integer.valueOf(_n[0]), _n[1]);
        }
        
        List<ConcurrentHashMap<String,Object>> cityList = new ArrayList<ConcurrentHashMap<String,Object>>();
        for(ConcurrentHashMap.Entry<String, Object> entry : provinceMap.entrySet()){
        	ConcurrentHashMap<String,Object> cityListMap= new ConcurrentHashMap<String,Object>();
        	cityListMap.put("province", entry.getValue());
        	List<String> list = new ArrayList<String>();
        	for(ConcurrentHashMap.Entry<String, Object> cityEntry : cityMap.entrySet()){
        		int cityNum = (int)cityEntry.getValue()/100;
        		if(cityNum==Integer.valueOf(entry.getKey()).intValue()){
        			list.add(cityEntry.getKey());
        		}
        	}
        	cityListMap.put("city", list);
        	cityList.add(cityListMap);
        }
        city= new ConcurrentHashMap<String, Object>();
        city.put("cityList", cityList);
    }
}

