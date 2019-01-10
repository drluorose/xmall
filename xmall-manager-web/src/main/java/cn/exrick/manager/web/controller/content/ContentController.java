package cn.exrick.manager.web.controller.content;

import cn.exrick.common.enums.ValidStatusEnum;
import cn.exrick.common.pojo.Result;
import cn.exrick.common.utils.ResultUtil;
import cn.exrick.content.service.ContentService;
import cn.exrick.manager.pojo.TbPanelContent;
import cn.exrick.manager.web.controller.content.req.AddPanelContentReq;
import com.alibaba.dubbo.config.annotation.Reference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author Exrickx
 */
@RestController
@Api(description = "板块内容管理")
public class ContentController {

    final static Logger log = LoggerFactory.getLogger(ContentController.class);

    @Reference
    private ContentService contentService;

    @RequestMapping(value = "/content/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加板块内容")
    public Result<Object> addContent(@RequestBody AddPanelContentReq req) {
        TbPanelContent tbPanelContent = new TbPanelContent();
        tbPanelContent.setPanelId(req.getPanelId());
        tbPanelContent.setFullUrl(req.getFullUrl());
        tbPanelContent.setPicUrl(req.getPicUrl());
        tbPanelContent.setPicUrl2(req.getPicUrl2());
        tbPanelContent.setPicUrl3(req.getPicUrl3());
        tbPanelContent.setProductId(req.getProductId());
        tbPanelContent.setSortOrder(req.getSortOrder());
        tbPanelContent.setType(req.getType());
        tbPanelContent.setValid(ValidStatusEnum.VALID);
        tbPanelContent.setCreated(new Date());
        tbPanelContent.setUpdated(new Date());

        contentService.addPanelContent(tbPanelContent);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/content/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑板块内容")
    public Result<Object> updateContent(@ModelAttribute TbPanelContent tbPanelContent) {

        contentService.updateContent(tbPanelContent);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/content/del/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除板块内容")
    public Result<Object> addContent(@PathVariable int[] ids) {

        for (int id : ids) {
            contentService.deletePanelContent(id);
        }
        return new ResultUtil<Object>().setData(null);
    }
}
