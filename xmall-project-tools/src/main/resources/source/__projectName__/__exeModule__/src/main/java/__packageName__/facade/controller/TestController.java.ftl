package @packageName@.facade.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/hello")
    public Map<String, String> hello(@RequestHeader("host") String hostName) {
        Map<String, String> res = new HashMap<>();
        res.put("code", "SUCCESS");
        return res;
    }

}