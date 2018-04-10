package one.show.api;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NotFoundApi extends BaseApi{


    /**
     *
     * @return
     */
    @RequestMapping(value = "/404")
    @ResponseBody
    public Map notFound() {
        return success();
    }
}
