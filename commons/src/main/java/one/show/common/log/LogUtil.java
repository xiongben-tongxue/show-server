package one.show.common.log;

import com.google.common.base.Joiner;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;

public class LogUtil {
    private static final String BIZPARAM = "bizParam";

    /**
     * 设置日志中的业务参数
     *
     * @param bizId 业务ID
     */
    public static void setBizId(String... bizId) {
        String bizParam = MDC.get(BIZPARAM);
        if (StringUtils.isBlank(bizParam)) {
            bizParam = Joiner.on(",").skipNulls().join(bizId);
            MDC.put(BIZPARAM, bizParam);
        } else {
            StringBuilder sbd = new StringBuilder()
                    .append(bizParam)
                    .append(",")
                    .append(Joiner.on(",").skipNulls().join(bizId));

            MDC.put(BIZPARAM, sbd.toString());
        }
    }

    /**
     * 日志打点,加入清除原有数据的代码
     *
     * @param bizId 业务ID
     */
    public static void setBizIdWithClear(String... bizId) {
        MDC.clear();
        setBizId(bizId);
    }

    /**
     * 获取日志中的业务参数
     */
    public static String getBizId() {
        String bizParam = MDC.get(BIZPARAM);
        return StringUtils.isBlank(bizParam) ? "" : bizParam;
    }
}
