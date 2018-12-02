package cn.exrick.sso.service.impl;

import cn.exrick.search.service.SearchHotWordService;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Slf4j
@Component
@Service(interfaceClass = SearchHotWordService.class)
public class SearchHotWordServiceImpl implements SearchHotWordService {
    @Override
    public List<String> searchHotWord() {
        List<String> hotWords =
            Lists.newArrayList("手机", "耳机");
        return hotWords;
    }
}
