package one.show.common;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Constant {
	
	public static final String ZK_PATH_HOME = "/show";
	
	public static final String ZK_LOCK_PATH_HOME = ZK_PATH_HOME + "/locks";
	
	public static final String ZK_ID_PATH_HOME = ZK_PATH_HOME + "/id";
	
	public static final String ZK_PID_PATH_HOME = ZK_PATH_HOME + "/pid";
	
	public static final String ZK_DS_PATH_HOME = ZK_PATH_HOME + "/datasource";
	
	public static final String ZK_SERVICE_PATH_HOME = ZK_PATH_HOME + "/service";
	
	public static final String ZK_GLOBAL_CONFIG_PATH_HOME = ZK_PATH_HOME + "/global_config";

	public static final String REDIS_KEY_FOLLOW_LIST = "follow_list_";
	
	public static final String REDIS_KEY_FANS_LIST = "fans_list_";
	
	public static final String REDIS_KEY_LIKE_LIST = "like_list_";
	
	public static final String REDIS_KEY_FEED_LIST = "feed_list_";
	
	public static final String REDIS_KEY_USER_HEADER = "user_header_";

	public final static String DOMAIN = Loader.getInstance().getProps("domain");
	
	public static final List<String> HOT_CITIES = Arrays.asList("全部", "北京市", "上海市", "广州市", "深圳市");
	public static final List<String> CITIES = Arrays.asList("北京市", "东莞市", "广州市", "海口市", "秦皇岛市", "石家庄市", "西安市", "烟台市");
	public static final String NEW_PUSHED_VIDEO = "video_new_pushed";


	
	//人民币兑换ShowCoin比率
	public static final BigDecimal  RMB_TO_SHOWCOIN_RATE = new BigDecimal(0);
	
	public static Set<String> ALL_CITIES = new HashSet<>();

	static  {
		ALL_CITIES.addAll(HOT_CITIES);
		ALL_CITIES.addAll(CITIES);
	}

	/* square releated end */
	public static enum UPLOAD_USE_TYPE{
		WORK,VERIFY,PHOTOS,TEST,NORMAL,PROFILE
	}

	/* flip video releated */
	public static final String NEW_PUBLISHED_VIDEO = "video_new_publish";
	public static final String FLIP_CACHE_KEY_PATTERN = "flip:page:%d";
	public static final String FLIP_LATEST_PAGE_KEY = "flip:latest_page";
	public static final String FLIP_USER_PAGE_CACHE_KEY_PATTERN = "flip:user:%s";

	/* other rank */
	public static final String RANK_PAY_CACHE_KEY_PATTERN = "rank:%s:%s:%d:%d";
	public static final String RANK_PAY_KEY_PATTERN = "rank:pay:data:%s:%s";
	public static final String RANK_PAY_TOTAL_CACHE_KEY_PATTERN = "rank:%s:%s:%s";
	
	public static final int CACHE_TIME = 1800;
	
	public static final int GUARD_LIST_PAGE_SIZE = 20;

	//视频评论区每次下拉获取都条数
	public static final int COMMENT_LIST_PAGE_SIZE = 30;
	
	//视频展现区显示的三条评论
	public static final int COMMENT_TOP_NUM = 3;
	
	//视频区守护列表展现守护的数量
	public static final int VIDEO_GUARD_NUM = 5;
	
	//视频区守护列表展现守护最大数量
	public static final int VIDEO_DEFENDER_TOTAL = 500;
	
	//系统参数配置主键
	public static final String SYSTEM_CONFIG_ID = "1";
	
	//
	public static String DEFAULT_CURRENT_RANK = "没排上号T_T";
	


	public static final String WX_APP_ID = "";
	public static final String WX_NOTIFY_URL = "";
	
	
	//状态
	public enum FAMILY_TYPE{
		//A类不能提现， B类可以提现
		A,B
	}
	//状态
	public enum FAMILY_MEMBER_ROLE{
		//家族长， 普通成员
		OWNER, MEMBER
	}
		
	//状态
	public enum STATUS{
		//可用，不可用
		ENABLED,DISABLE
	}
	
	public enum USER_AGENT {
		IOS,ANDROID,UNKNOW,WEB
	}
	
	public enum CDN {
		//网宿, 阿里云
		WANGSU, ALIYUN
	}
	
	public enum WANGSU_NGB {
		//关，开
		OFF, ON
	}
	
	//直播状态
	public enum LIVE_STATUS {
		//0结束 , 1直播中. 2准备开播放.  3无效回放
		END, IN, READY, INVALID
	}
	
	//直播流状态
	public enum LIVE_STREAM_STATUS {
		//0等待，1开始直播，2断流，3断流恢复（续播），4结束
		WAIT, IN, BREAKING, RESUME, END
	}
	
	
	// CDN流状态
    public static enum CDN_STREAM_STATUS {
        // 停止、开始、中断、即将开始
        STOP, START, BREAK, READY
    }
	
	public enum LIVE_END_REASON {
		//异常断流，直播流中断超时， 用户主动关闭，审核人员关闭，禁播
		EXCEPTION, BREAK_TIMEOUT, NORMAL, AUDIT, FORBIDDEN
	}
	
	//靓号使用状态
	public enum POPULAR_NO_STATUS {
		UNUSED, USEING;
	}

	public enum OS {
		IOS(0),ANDROID(1),OTHERS(2);

		public int getId() {
			return id;
		}

		private final int id;

		OS(int id) {
			this.id = id;
		}
	}
	
	//性别
	public enum USER_SEX{
		//男，女
		MAN(0),WOMAN(1);

		private final int value;

		USER_SEX(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	
    public static enum FEED_TYPE {
        LIVE, VOD
    }
    
    public static enum ADMIN_FUNC_TYPE {
        OPERATE, FUNCTION
    }
    
    public static enum ADMIN_STATUS {
        ENABLE, DISABLE
    }
    
	//是否被推荐
	public enum USER_RECOMMENDED_STATUS{
		//被推荐，未被推荐
		NO,YES
	}
		
	//是否被推送到广场
	public enum USER_PUSH_STATUS{
		//被推到广场，未被推到广场
		NO,YES
	}
	
	public enum STAT_ACTION {
		LIVE, PLAY,  GIFT, LOGIN, SHARE, API_REQ,
		SERVICE_REQ, REGISTER, RECHARGE, LIKE, LAUNCH, EXIT, ACTION, ONLINE;
	}
	

	public enum LIVE_ACTION {
		START, END, RECORD;
	}
	
	public enum RELATION_ACTION {
		FOLLOW, UNFOLLOW
	}
	
	public enum THIRD_BIND_PUBLIC_STATUS{
		//不公开,公开
		NO,YES
	}
	
	public enum RANK_USER_DAILY_TYPE {
		//消费  收礼  充值
		OUT, IN, PAY, LIKE;
	}
	
	//第三方账号类型
	public enum THIRD_DATA_TYPE{
		T_SINA("sina",1),T_QQ("qq",2),T_WEIXIN("weixin",3),T_PHONE("phone",4),T_EMAIL("email",5);

		private String typeName;
		private int index;

		private THIRD_DATA_TYPE(String typeName,int index){
			this.typeName = typeName;
			this.index = index;
		}

		public String getTypeName() {
			return typeName;
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}
	public enum ORDERS_PAY_TYPE{
		ZHIFUBAO("支付宝",0),WEIXIN("微信",1),APPSTORE("appStore",2),WEIXIN_WEB("微信网页",3);
		private String payDesc;
		private int payCode;
		private ORDERS_PAY_TYPE(String payDesc,int payCode){
			this.payDesc = payDesc;
			this.payCode = payCode;
		}
		public String getPayDesc() {
			return payDesc;
		}
		public void setPayDesc(String payDesc) {
			this.payDesc = payDesc;
		}
		public int getPayCode() {
			return payCode;
		}
		public void setPayCode(int payCode) {
			this.payCode = payCode;
		}
	}
	
	public enum ORDERS_PAY_STATUS{
		FAIL("支付失败/未支付",0),SUCCESS("支付成功",1);
		private String desc;
		private int code;
		private ORDERS_PAY_STATUS(String desc,int code){
			this.desc=desc;
			this.code=code;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		
	}
	//用户权限
	public enum USER_AUTH_FORBID {
		//1:登录,2:直播,3:聊天,4:直播间财富排行 5:关注 6:飘心 7:修改昵称 8:修改头像 9:修改简介 
		LOGIN("登陆",1),LIVE("直播",2),TALK("聊天",3),RANK("上排行榜",4),FOLLOW("关注别人", 5),SUPPORT("直播间点赞", 6),
		CHANGE_NICKNAME("修改昵称", 7), CHANGE_AVATAR("修改头像", 8), CHANGE_DESC("修改简介", 9);

		private static final Map<Integer, String> map = new HashMap<Integer, String>();

		static {
			for (USER_AUTH_FORBID auth : EnumSet.allOf(USER_AUTH_FORBID.class)) {
				map.put(auth.getIndex(), auth.getTypeName());
			}
		}

		public static String findNameByIndex(int index) {
			return map.get(index);
		}

		private USER_AUTH_FORBID(String typeName,int index){
			this.typeName = typeName;
			this.index = index;
		}
		private String typeName;
		private int index;
		public String getTypeName() {
			return typeName;
		}
		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}

	}
	
	//评论操作
	public enum COMMENT_OP_TYPE{
		ADD("add",1),REPLY("reply",2),DELETE("delete",3);
		private String typeName;
		private int index;

		private COMMENT_OP_TYPE(String typeName,int index){
			this.typeName = typeName;
			this.index = index;
		}

		public String getTypeName() {
			return typeName;
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}
	
	//消息类型
	public enum MESSAGE_TYPE{
		FOLLOW("fu",1),LIKE("lv",2),COMMENT("cb",3),PUSHVIDEO("vtp",4),PUSHUSER("utp",5),AIAT("atb",6),FRIEND("rf",7),GIFT("gv",8),SYSTEM("sys",9),ONE("sys",10);
		private String name;
		private int code;
		private MESSAGE_TYPE(String name,int code){
			this.name = name;
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
	}
	public enum MESSAGE_READ_STATUS{
		//未读，已读
		NO,YES
	}
	
	public enum FORMAT{
		FLV,MP4,M3U8
	}
	
	 public static enum ITEM_TYPE {

	        /**
	         * 0 礼物
	         */
	        GIFT(0, "礼物"),

	        /**
	         * 1 道具
	         */
	        PROP(1, "道具"),

	        /**
	         * 2 金钱
	         */
	        MONEY(2, "金钱");


	        private final int id;
	        private final String name;

	        ITEM_TYPE(int id, String name) {
	            this.id = id;
	            this.name = name;
	        }

	        public int getId() {
	            return this.id;
	        }

	        public String getName() {
	            return this.name;
	        }
	 }

	 public static enum ITEM_PROP {

	        /**
	         * 1 vip
	         */
	        VIP(1, "vip"),
	        
	        /**
	         * 2
	         */
	        PIAO(2, "飘屏");
	        
	       

	        private final int id;
	        private final String name;

	        ITEM_PROP(int id, String name) {
	            this.id = id;
	            this.name = name;
	        }

	        public int getId() {
	            return this.id;
	        }

	        public String getName() {
	            return this.name;
	        }

	    }
	 
	 public static enum ITEM_MONEY {

	        /**
	         * 1 秀币
	         */
	        SHOWCOIN(1, "ShowCoin"),


	        /**
	         * 1000 人民币
	         */
	        RMB(1000, "人民币"),
	        
	        /**
	         * 2000 美元
	         */
	        DOLLAR(2000, "美元");

	        private final int id;
	        private final String name;

	        ITEM_MONEY(int id, String name) {
	            this.id = id;
	            this.name = name;
	        }

	        public int getId() {
	            return this.id;
	        }

	        public String getName() {
	            return this.name;
	        }

	    }


	 /**
	     * 用于：orders表中标示支付状态
	     * 订单状态，支付或者未支付
	     */
	    public static enum ORDER_STATUS {

	        /**
	         * 0 未支付
	         */
	        UNPAYED(0, "未支付"),
	        
	        /**
	         * 1 已支付
	         */
	        PAYED(1, "已支付");


	        private final int id;
	        private final String name;

	        ORDER_STATUS(int id, String name) {
	            this.id = id;
	            this.name = name;
	        }

	        public int getId() {
	            return this.id;
	        }

	        public String getName() {
	            return this.name;
	        }

	    }

	    /**
	     * 用于：action_type字段，各种消费动作中的动作类型，就是操作的动作记录，
	     */
	    public static enum ACTION {

	        /**
	         * 0 送礼
	         */
	        SEND_GIFT(0, "送礼"),

	        /**
	         * 1 系统赠送
	         */
	        SYS_GIVE(1, "系统赠送"),

	        /**
	         * 2 人为赠送
	         */
	        MAN_GIVE(2, "人为赠送"),

	     
	        /**
	         * 4 提现
	         */
	        SHOWCOIN_EXCHANGE_RMB(4, "提现"),
	        
	        /**
	         * 5 人民币购买秀币(充值)
	         */
	        RMB_BUY_SHOWCOIN(5, "人民币购买秀币"),
	        
	      
	        /**
	         * 7 系统回收
	         */
	        SYS_RECOVERY(7, "系统回收"),
	        /**
	         * 8 秀币消耗
	         */
	        CONSUMER(8, "秀币消耗"),
	        /**
	         * 9 秀币购买
	         */
	        BUY(9, "秀币购买"),
	        /**
	         * 10 家族结算
	         */
	        FAMILY_CLEARING(10, "家族结算");
	        

	       

	        private final int id;
	        private final String name;

	        ACTION(int id, String name) {
	            this.id = id;
	            this.name = name;
	        }

	        public int getId() {
	            return this.id;
	        }

	        public String getName() {
	            return this.name;
	        }
	        

	        public static ACTION getActionByValue(int id){
	        	ACTION action = null;
	        	switch (id) {
				case 0:
					action =  SEND_GIFT;
					break;
				case 1:
					action =  SYS_GIVE;
					break;
				case 2:
					action =  MAN_GIVE;
					break;
				
				case 5:
					action =  RMB_BUY_SHOWCOIN;
					break;
				
				case 7:
					action =  SYS_RECOVERY;
					break;
				case 8:
					action =  CONSUMER;
					break;
				case 9:
					action =  BUY;
					break;
				case 10:
					action =  FAMILY_CLEARING;
					break;
				default:
					break;
				}
	        	return action;
	        }

	    }


	
	 /**
     * 用于：stock_log表中operate字段，标示增加还是减少，
     * 库存中物品的操作，是增加还是减少。
     */

	 public static enum STOCK_OPERATE {

		 	MINUS(0, "减少"),
	        ADD(1, "增加");


	        private final int id;
	        private final String name;

	        STOCK_OPERATE(int id, String name) {
	            this.id = id;
	            this.name = name;
	        }

	        public int getId() {
	            return this.id;
	        }

	        public String getName() {
	            return this.name;
	        }

	    }
		
		public static enum VIP_PRICE{
			VIP_1(1,"1个月"),VIP_2(30,"3个月"),VIP_3(60,"6个月"),VIP_4(118,"12个月");
			private int code;
			private String desc;
			VIP_PRICE(int code,String desc){
				this.code=code;
				this.desc = desc;
			}
			public int getCode() {
				return code;
			}
			public void setCode(int code) {
				this.code = code;
			}
			public String getDesc() {
				return desc;
			}
			public void setDesc(String desc) {
				this.desc = desc;
			}
			
		}
		public static enum STAR_PRICE{
			PRICE_1(1,"3000"),PRICE_2(50,"5000"),PRICE_3(98,"10000"),PRICE_4(298,"30000"),PRICE_5(488,"50000");
			private int code;
			private String desc;
			STAR_PRICE(int code,String desc){
				this.code = code;
				this.desc=desc;
			}
			public int getCode() {
				return code;
			}
			public void setCode(int code) {
				this.code = code;
			}
			public String getDesc() {
				return desc;
			}
			public void setDesc(String desc) {
				this.desc = desc;
			}
			
		}
		
		
		public static enum NOTIFY_CONFIG{
			NO_DISTURB,NOTICE,COMMENT,FOLLOW,NEWS
		}
		
		public static enum EXTRACT_STATUS{
			APPLY(0,"申请中"),AGREE(1,"已批准"),REFUSE(2,"已拒绝"),CANCEL(3,"取消"),SUCCESS(4,"提现成功"),FAIL(5,"提现失败");
			private int value;
			private String name;
			private EXTRACT_STATUS(int value, String name) {
				this.value = value;
				this.name = name;
			}
			public int getValue() {
				return value;
			}
			public void setValue(int value) {
				this.value = value;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public static EXTRACT_STATUS getStatusByValue(int status) {
				EXTRACT_STATUS s = null;
				switch (status) {
				case 0:
					s = APPLY;
					break;
				case 1:
					s = AGREE;
					break;
				case 2:
					s = REFUSE;
					break;
				case 3:
					s = CANCEL;
					break;
				case 4:
					s = SUCCESS;
					break;
				case 5:
					s = FAIL;
					break;

				default:
					break;
				}
				return s;
			}
		}
		
		public static enum ACTIVITY_ID{
			SHARE(1,"分享活动");
			
			private int id;
			
			private String name;
			
			private ACTIVITY_ID(int id,String name){
				this.id = id;
				this.name = name;
			}

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}
		}
}
