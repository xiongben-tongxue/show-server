package one.show.common;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import one.show.common.exception.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Haliaeetus leucocephalus on 18/1/13.
 */
public class AntiHotlinking {

    private static final Logger log = LoggerFactory.getLogger(AntiHotlinking.class);

    /**
     *
     * @param list
     * @param key
     */
    public static void signResources ( List<Map> list, String key ) throws ServiceException {
        for (Map m : list ) {
            m.put(key, signResource((String) m.get(key)));
        }
    }

    public static String signResource(String url) throws ServiceException {
        if (url == null || "".equals(url))
            return null;

        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            log.error("invalid url {}", url, e);
            return url;
        }
        switch (uri.getHost()) {
            case "aliv.weipai.cn" :
                return signResourceWith51Cache(uri.getPath(), "BV4eMrU3Rbq86LAA", "video.weipai.cn");
            case "v.weipai.cn" :
                return signResourceWith51Cache(uri.getPath(), "KQN6Gw08jVi50nf", "v2.weipai.cn");
            default:
                return url;
        }
    }

    private static String signResourceWith51Cache(String path, String key, String new_domain) {
        //格式化时间为YYYYMMddHHmm 例如 "201506291630"
        String date = new SimpleDateFormat("YYYYMMddHHmm").format(new Date());
        String token = MD5.md5(key + date + path);
        return String.format("http://%s/%s/%s%s", new_domain, date, token, path);
    }
}
