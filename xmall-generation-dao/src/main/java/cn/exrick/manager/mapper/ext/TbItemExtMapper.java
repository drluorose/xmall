package cn.exrick.manager.mapper.ext;

import cn.exrick.common.query.ItemSearchParam;
import cn.exrick.manager.dto.front.SearchItem;
import cn.exrick.manager.pojo.TbItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbItemExtMapper {

    List<TbItem> selectItemByCondition(ItemSearchParam itemSearchParam);

    List<TbItem> selectItemByMultiCondition(@Param("cid") int cid, @Param("search") String search, @Param("minDate") String minDate,
                                            @Param("maxDate") String maxDate, @Param("orderCol") String orderCol,
                                            @Param("orderDir") String orderDir);

    List<TbItem> selectItemFront(@Param("cid") Long cid,
                                 @Param("orderCol") String orderCol, @Param("orderDir") String orderDir,
                                 @Param("priceGt") int priceGt, @Param("priceLte") int priceLte);

    List<SearchItem> getItemList();

    SearchItem getItemById(@Param("id") Long id);
}