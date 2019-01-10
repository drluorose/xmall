package cn.exrick.manager.web.controller.itemcat;

import cn.exrick.common.pojo.Result;
import cn.exrick.common.utils.ResultUtil;
import cn.exrick.manager.pojo.TbItemCat;
import cn.exrick.manager.service.ItemCatService;
import cn.exrick.manager.web.controller.itemcat.req.ItemListReq;
import cn.exrick.manager.web.controller.itemcat.res.ItemCatDto;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Exrick
 * @date 2017/8/2
 */
@RestController
@Api(description = "商品分类信息")
public class ItemCatController {

    @Reference
    private ItemCatService itemCatService;

    @RequestMapping(value = "/item/cat/list", method = RequestMethod.POST)
    @ApiOperation(value = "通过父ID获取商品分类列表")
    public Result<List<ItemCatDto>> getItemCatList(@RequestBody ItemListReq itemListReq) {
        long parentId = itemListReq.getId();
        List<ItemCatDto> result = null;
        List<TbItemCat> list = itemCatService.getItemCatList(parentId, itemListReq.getStatus());
        if (CollectionUtils.isEmpty(list)) {
            result = Lists.newArrayList();
        } else {
            result = list.stream().map(ItemCatDto::new).collect(Collectors.toList());
        }
        return new ResultUtil<List<ItemCatDto>>().setData(result);
    }

    @RequestMapping(value = "/item/cat/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加商品分类")
    public Result<Object> addItemCategory(@ModelAttribute TbItemCat tbItemCat) {

        itemCatService.addItemCat(tbItemCat);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/item/cat/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑商品分类")
    public Result<Object> updateItemCategory(@ModelAttribute TbItemCat tbItemCat) {

        itemCatService.updateItemCat(tbItemCat);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/item/cat/del/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除商品分类")
    public Result<Object> deleteItemCategory(@PathVariable Long id) {

        itemCatService.deleteItemCat(id);
        return new ResultUtil<Object>().setData(null);
    }
}
