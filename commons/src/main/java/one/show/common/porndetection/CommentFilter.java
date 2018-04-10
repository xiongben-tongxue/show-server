package one.show.common.porndetection;

import org.apache.commons.lang3.StringUtils;

public class CommentFilter {

	static final String[] WECHAT_KEYWORDS ={ "微信", "v信", "weixin", "加微", "加wx", "v心", "v芯",
			                                 "懂的微", "威信", "vxin", "vx：", "薇芯", "微亻言", "v❤️"};

	static final String[] QQ_KEYWORDS = {"qq", "加q", "扣扣", "q群", "加扣"};

	// 色情关键字列表
	static final String[] PORN_KEYWORDS =
		{ "真人", "妹妹", "哥哥", "老公", "福利", "摸奶", "大奶", "奶子", "大乳", "大波",
		  "直播", "大尺度", "大尺码", "露脸", "咪咪", "裸", "诱惑", "叫床", "全倮", "a片", "色情",
		   "自慰", "喷水", "啪啪", "抠逼", "逼", "阴道", "射", "无码", "女同", "激情","高潮", 
		   "流水", "夫妻", "做爱", "爱爱", "车震", "开房", "干爹", "野战","群p", "騷情", 
		   "私密视频", "红人自拍", "想看的都有", "漏点", "av小电影"};
    // 包含硬色情关键字
	static final String[] HARDCORE_PORN_WORDS =
		{ "阴茎", "阴道", "鸡鸡", "射精", "骚逼","礻果耳 卯", "礻果聊", "裸聊"};
	//应该删除的群group
	static final String[] FORBIDDEN_GROUP_WORDS =
		{ "3458850", "q群 3458850", "q群218631627", "q群 218631627",
		  "wppp889", "fpw4385", "278881213", "xxoo58kk","he814578957", 
		  "armb9898", "caidao7777", "gaoduan77","zazaling94", 
		  "zaxaling94", "2376608382", "814884317","fuliweixin9",
		  "2875718820", "wenrounanjie", "xx66256","xxoo5201kk", 
		  "puk118", "papa666", "dabobo1024","piaoliangdameimei", "fuli5152"};
	
	// 广告关键字
	static final String[] HARDCORE_AD_WORDS =
		{ "全自动评论",
		"江诗丹顿。劳力士。百达翡丽", "厂家直销。手表在五六百", "厂家直销复刻品牌手表",
		"御灵清祛痘液，纯中药研", "御灵清祛痘液，专业祛痘", "经过鉴定机构鉴定。安全有效",
		"微拍热门视频下面评论推广", "信用卡提额度工商银行信用", "这条广告不是招兼职的",
		"固定一个广告词", "改变从千丽媛开始", "营业要求进行返现，有意者",
		"微拍热门广场代发广告", "微拍热门广场评论推广", "招加盟 就是你自己做一手",
		"出售微拍vip会员视频",
		"大尺度视频直播加", "妹妹直播群速度报名", "自抠爱爱走私会员视频", "赶紧报名妹妹直播",
		"真人表演，听指挥情趣内衣", "各种道具，呻吟.加qq", "诱惑，制服，丝袜，",
		"全世界20万电影全部在线", "10几万电影永久在线", "tb大小的av视频",
		"推广需要收费，不试用", "改善硬度粗大治疗阳wei",
		"阿玛尼 巴宝莉等。用料", "代.理的加总.代娅娅咨.",
		"用价格和服务态度打销路", "居家就可以工作，薪资日结", }; 
	
	// 判断评论是否应该过滤的函数
	public static Boolean IsNeedToFilter(final String strToCheck){
		if(IsPornWechat(strToCheck) || IsPornQQ(strToCheck) || IsHardCorePorn(strToCheck) || IsAds(strToCheck) || IsForbiddenGroup(strToCheck)){
			return true;
		}
		return false;
	}
	
	// 包含该删除的群组关键词
		protected static Boolean IsForbiddenGroup(final String strToCheck) {
			String str = StringUtils.trim(strToCheck).toLowerCase();

			for (int i = 0; i < FORBIDDEN_GROUP_WORDS.length; i++) {
				if (str.indexOf(FORBIDDEN_GROUP_WORDS[i]) != -1) {
					return true;
				}
			}
			return false;
		}
		
	// 判断字符串是否包含微信以及色情关键词
	public static Boolean IsPornWechat(final String strToCheck) {
		String str = StringUtils.trim(strToCheck).toLowerCase();
		// 检查是否字符串是否出现"微信"
		for (int i = 0; i < WECHAT_KEYWORDS.length; i++) {
			//如果出现了WECHAT_KEYWORDS，则继续判断是否有PORN_KEYWORDS
			if (str.indexOf(WECHAT_KEYWORDS[i]) != -1) {
				// 检查是否字符串出现色情关键字
				for (int j = 0; j < PORN_KEYWORDS.length; j++) {
					// 找到色情关键字
					if (str.indexOf(PORN_KEYWORDS[j]) != -1) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// 判断字符串是否包含硬色情关键词
	protected static Boolean IsHardCorePorn(final String strToCheck) {
		String str = StringUtils.trim(strToCheck).toLowerCase();

		for (int i = 0; i < HARDCORE_PORN_WORDS.length; i++) {
			if (str.indexOf(HARDCORE_PORN_WORDS[i]) != -1) {
				return true;
			}
		}
		return false;
	}

	// 判断字符串是否包含QQ以及色情关键词
	protected static Boolean IsPornQQ(final String strToCheck) {
		String str = StringUtils.trim(strToCheck).toLowerCase();

		// 检查是否字符串是否出现"QQ"
		for (int i = 0; i < QQ_KEYWORDS.length; i++) {
			if (str.indexOf(QQ_KEYWORDS[i]) != -1) {
				// 检查是否字符串出现色情关键字
				for (int j = 0; j < PORN_KEYWORDS.length; j++) {
					// 找到色情关键字
					if (str.indexOf(PORN_KEYWORDS[j]) != -1) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//判断是否是硬广告
	protected static Boolean IsAds(final String strToCheck) {
		String str = StringUtils.trim(strToCheck).toLowerCase();

		for (int i = 0; i < HARDCORE_AD_WORDS.length; i++) {
			if (str.indexOf(HARDCORE_AD_WORDS[i]) != -1) {
				return true;
			}
		}
		return false;
	}
	
}
