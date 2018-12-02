package cn.exrick.search.service.impl;

import cn.exrick.manager.dto.front.SearchItem;
import cn.exrick.manager.mapper.ext.TbItemExtMapper;
import cn.exrick.search.biz.ElasticSearchBiz;
import cn.exrick.search.service.SearchItemService;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author Exrickx
 */
@Slf4j
@Component
@Service(interfaceClass = SearchItemService.class)
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private TbItemExtMapper tbItemExtMapper;

    @Autowired
    private ElasticSearchBiz elasticSearchBiz;

    @PostConstruct
    private void initIndex() throws IOException {
        int itemImportSize = importAllItems();
        log.info("Import {} Items into index", itemImportSize);
    }

    @Override
    public int importAllItems() throws IOException {
        if (!elasticSearchBiz.existProductIndex()) {
            elasticSearchBiz.createProductIndex();
        }
        List<SearchItem> allProducts = getAllSearchItem();
        if (CollectionUtils.isEmpty(allProducts)) {
            return 0;
        }
        saveOrUpdateProductIndex(allProducts);
        return allProducts.size();
    }

    private List<SearchItem> getAllSearchItem() {
        //查询商品列表
        List<SearchItem> itemList = tbItemExtMapper.getItemList();

        //遍历商品列表
        for (SearchItem searchItem : itemList) {
            String image = searchItem.getProductImageBig();
            if (image != null && !"".equals(image)) {
                String[] strings = image.split(",");
                image = strings[0];
            } else {
                image = "";
            }
            searchItem.setProductImageBig(image);

        }
        return itemList;
    }

    private void saveOrUpdateProductIndex(List<SearchItem> allProducts) throws IOException {
        for (SearchItem searchItem : allProducts) {
            boolean existDoc = Objects.nonNull(elasticSearchBiz.getSearchIndexDoc(searchItem.getProductId()));
            if (existDoc) {
                elasticSearchBiz.updateSearchIndexDoc(searchItem);
            } else {
                elasticSearchBiz.createSearchIndexDoc(searchItem);
            }
        }
    }
}
