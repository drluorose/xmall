package cn.exrick.manager.web.controller;

import cn.exrick.common.enums.IndexTypeEnum;
import cn.exrick.common.pojo.DataTablesResult;
import cn.exrick.common.pojo.Result;
import cn.exrick.common.utils.ResultUtil;
import cn.exrick.content.service.ContentService;
import cn.exrick.content.service.PanelService;
import cn.exrick.manager.pojo.TbPanel;
import cn.exrick.manager.web.controller.req.UpdateContentCategoryReq;
import cn.exrick.manager.web.controller.res.PanelDto;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Exrickx
 */
@RestController
@Api(description = "板块列表")
public class PanelController {

    private final static Logger log = LoggerFactory.getLogger(PanelController.class);

    @Reference
    private PanelService panelService;

    @Reference
    private ContentService contentService;

    @RequestMapping(value = "/panel/indexAll/list", method = RequestMethod.GET)
    @ApiOperation(value = "获得首页板块列表含轮播")
    public Result<List<PanelDto>> getAllIndexPanel() {

        List<TbPanel> list = panelService.getPanelList(IndexTypeEnum.ALL);
        if (CollectionUtils.isEmpty(list)) {
            list = Lists.newArrayList();
        }
        List<PanelDto> result = list.stream().map(PanelDto::new).collect(Collectors.toList());
        return new ResultUtil<List<PanelDto>>().setData(result);
    }

    @RequestMapping(value = "/panel/indexBanner/list", method = RequestMethod.GET)
    @ApiOperation(value = "获得首页轮播板块列表")
    public Result<List<PanelDto>> getIndexBannerPanel() {
        return getPanelByIndexType(IndexTypeEnum.BANNER);
    }

    @RequestMapping(value = "/panel/indexOther/list", method = RequestMethod.GET)
    @ApiOperation(value = "获得其它添加板块")
    public Result<List<PanelDto>> getRecommendPanel() {
        return getPanelByIndexType(IndexTypeEnum.OTHER);
    }

    private Result<List<PanelDto>> getPanelByIndexType(IndexTypeEnum indexTypeEnum) {
        List<TbPanel> list = panelService.getPanelList(indexTypeEnum);
        if (CollectionUtils.isEmpty(list)) {
            list = Lists.newArrayList();
        }
        List<PanelDto> result = list.stream().map(PanelDto::new).collect(Collectors.toList());
        for (PanelDto panelDto : result) {
            DataTablesResult dataTablesResult = this.contentService.getPanelContentListByPanelId(panelDto.getId());
            panelDto.setDataTablesResult(dataTablesResult);
        }
        return new ResultUtil<List<PanelDto>>().setData(result);
    }

    @RequestMapping(value = "/panel/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加板块")
    public Result<Object> addContentCategory(@ModelAttribute TbPanel tbPanel) {

        panelService.addPanel(tbPanel);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/panel/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑内容分类")
    public Result<Object> updateContentCategory(@RequestBody UpdateContentCategoryReq updateContentCategoryReq) {
        if (Objects.isNull(updateContentCategoryReq) || Objects.isNull(updateContentCategoryReq.getId()) ||
            updateContentCategoryReq.getId() <= 0) {
            throw new IllegalArgumentException("提交的参数错误");
        }
        TbPanel tbPanel = new TbPanel();
        tbPanel.setId(updateContentCategoryReq.getId());
        if (Objects.nonNull(updateContentCategoryReq.getLimitNum())) {
            tbPanel.setLimitNum(updateContentCategoryReq.getLimitNum());
        }
        if (Objects.nonNull(updateContentCategoryReq.getSortOrder())) {
            tbPanel.setLimitNum(updateContentCategoryReq.getSortOrder());
        }
        if (Objects.nonNull(updateContentCategoryReq.getName())) {
            tbPanel.setName(updateContentCategoryReq.getName());
        }
        panelService.updatePanel(tbPanel);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/panel/index/del/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除内容分类")
    public Result<Object> deleteContentCategory(@PathVariable int id) {
        panelService.deletePanel(id);
        return new ResultUtil<Object>().setData(null);
    }
}
