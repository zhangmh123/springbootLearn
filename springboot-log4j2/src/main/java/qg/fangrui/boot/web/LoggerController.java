package qg.fangrui.boot.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Created by zggdczfr on 2017/1/24.
 */
@Controller
public class LoggerController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/mylog")
    public String index(ModelMap map){
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
        map.addAttribute("host", "Welcome to index page.");
        return "index";
    }
}