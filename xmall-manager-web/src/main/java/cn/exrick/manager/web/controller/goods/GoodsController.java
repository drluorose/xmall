package cn.exrick.manager.web.controller.goods;

import cn.exrick.common.pojo.DataTablesResult;
import cn.exrick.common.pojo.PageResult;
import cn.exrick.common.pojo.Result;
import cn.exrick.common.utils.ResultUtil;
import cn.exrick.manager.dto.ItemDto;
import cn.exrick.manager.pojo.TbItem;
import cn.exrick.manager.service.ItemService;
import cn.exrick.manager.service.req.ItemSearchQuery;
import cn.exrick.manager.web.controller.goods.req.AddGoodsReq;
import cn.exrick.manager.web.controller.goods.req.GoodsSearchReq;
import cn.exrick.search.service.SearchItemService;
import com.alibaba.dubbo.config.annotation.Reference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author Exrick
 * @date 2017/7/29
 */
@RestController
@Api(description = "商品列表信息")
public class GoodsController {

    private final static Logger log = LoggerFactory.getLogger(GoodsController.class);

    @Reference
    private ItemService itemService;

    @Reference
    private SearchItemService searchItemService;

    @RequestMapping(value = "/item/{itemId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过ID获取商品")
    public Result<ItemDto> getItemById(@PathVariable Long itemId) {

        ItemDto itemDto = itemService.getItemById(itemId);
        return new ResultUtil<ItemDto>().setData(itemDto);
    }

    @RequestMapping(value = "/item/list", method = RequestMethod.POST)
    @ApiOperation(value = "分页搜索排序获取商品列表")
    public Result<PageResult> getItemList(@RequestBody GoodsSearchReq goodsSearchReq) {

        //获取客户端需要排序的列
        ItemSearchQuery itemSearchQuery = new ItemSearchQuery();
        itemSearchQuery.setCategory(goodsSearchReq.getThirdCategory());
        itemSearchQuery.setId(goodsSearchReq.getId());
        itemSearchQuery.setName(goodsSearchReq.getName());
        itemSearchQuery.setSku(goodsSearchReq.getSku());
        itemSearchQuery.setPageNo(goodsSearchReq.getPageNo());
        itemSearchQuery.setPageSize(goodsSearchReq.getPageSize());
        PageResult result = itemService.getItemList(itemSearchQuery);
        return new ResultUtil<PageResult>().setData(result);
    }

    @RequestMapping(value = "/item/listSearch", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页搜索排序获取商品列表")
    public DataTablesResult getItemSearchList(int draw, int start, int length, int cid, String searchKey, String minDate, String maxDate,
                                              @RequestParam("search[value]") String search, @RequestParam("order[0][column]") int orderCol,
                                              @RequestParam("order[0][dir]") String orderDir) {

        //获取客户端需要排序的列
        String[] cols = {"checkbox", "id", "image", "title", "sell_point", "price", "created", "updated", "status"};
        //默认排序列
        String orderColumn = cols[orderCol];
        if (orderColumn == null) {
            orderColumn = "created";
        }
        //获取排序方式 默认为desc(asc)
        if (orderDir == null) {
            orderDir = "desc";
        }
        if (!search.isEmpty()) {
            searchKey = search;
        }
        DataTablesResult result = itemService.getItemSearchList(draw, start, length, cid, searchKey, minDate, maxDate, orderColumn, orderDir);
        return result;
    }

    @RequestMapping(value = "/item/count", method = RequestMethod.GET)
    @ApiOperation(value = "获得商品总数目")
    public DataTablesResult getAllItemCount() {

        DataTablesResult result = itemService.getAllItemCount();
        return result;
    }

    @RequestMapping(value = "/item/stop/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "下架商品")
    public Result<TbItem> stopItem(@PathVariable Long id) {

        TbItem tbItem = itemService.alertItemState(id, 0);
        return new ResultUtil<TbItem>().setData(tbItem);
    }

    @RequestMapping(value = "/item/start/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "发布商品")
    public Result<TbItem> startItem(@PathVariable Long id) {

        TbItem tbItem = itemService.alertItemState(id, 1);
        return new ResultUtil<TbItem>().setData(tbItem);
    }

    @RequestMapping(value = "/item/del/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除商品")
    public Result<TbItem> deleteItem(@PathVariable Long[] ids) {

        for (Long id : ids) {
            itemService.deleteItem(id);
        }
        return new ResultUtil<TbItem>().setData(null);
    }

    @RequestMapping(value = "/item/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加商品")
    public Result<TbItem> addItem(@RequestBody AddGoodsReq addGoodsReq) throws IOException {
        ItemDto itemDto = new ItemDto();
        itemDto.setCid(addGoodsReq.getCid());
        itemDto.setImage(addGoodsReq.getImage());
        itemDto.setLimitNum(10);
        itemDto.setNum(10);
        itemDto.setTitle(addGoodsReq.getTitle());
        itemDto.setPrice(addGoodsReq.getPrice());
        itemDto.setSellPoint(addGoodsReq.getSellPoint());
        itemDto.setDetail(addGoodsReq.getItemDesc());
        TbItem tbItem = itemService.addItem(itemDto);
        return new ResultUtil<TbItem>().setData(tbItem);
    }

    @RequestMapping(value = "/item/update/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "编辑商品")
    public Result<TbItem> updateItem(@PathVariable Long id, ItemDto itemDto) {

        TbItem tbItem = itemService.updateItem(id, itemDto);
        return new ResultUtil<TbItem>().setData(tbItem);
    }

    @RequestMapping(value = "/item/importIndex", method = RequestMethod.GET)
    @ApiOperation(value = "导入商品索引至ES")
    public Result<Object> importIndex() throws IOException {

        searchItemService.importAllItems();
        return new ResultUtil<Object>().setData(null);
    }

}
