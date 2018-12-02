package cn.exrick.front.controller;

import cn.exrick.common.pojo.Result;
import cn.exrick.common.utils.ResultUtil;
import cn.exrick.search.service.SearchHotWordService;
import com.alibaba.dubbo.config.annotation.Reference;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Slf4j
@RestController
@Api(description = "搜索热词")
public class SearchHotWordController {

    @Reference
    private SearchHotWordService searchHotWordService;

    @RequestMapping("/search/hotWorld")
    public Result<List<String>> hotWord() {
        List<String> hotWords = searchHotWordService.searchHotWord();
        return new ResultUtil<List<String>>().setData(hotWords);
    }
}
