package cn.exrick.search.biz;

import cn.exrick.manager.dto.front.SearchItem;

import java.io.IOException;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
public interface ElasticSearchBiz {

    /**
     * 是否存在商品缓存
     *
     * @return
     *
     * @throws IOException 网络问题
     */
    boolean existProductIndex() throws IOException;

    /**
     * 创建商品的缓存
     *
     * @return
     *
     * @throws IOException 网络问题s
     */
    boolean createProductIndex() throws IOException;

    /**
     * 创建文档
     *
     * @param searchItem
     *
     * @return
     *
     * @throws IOException
     */
    boolean createSearchIndexDoc(SearchItem searchItem) throws IOException;

    /**
     * 获取文档
     *
     * @param id
     *
     * @return
     *
     * @throws IOException
     */
    SearchItem getSearchIndexDoc(Long id);

    /**
     * 更新查询缓存
     *
     * @param searchItem
     */
    boolean updateSearchIndexDoc(SearchItem searchItem) throws IOException;

    /**
     * 删除Search缓存
     *
     * @param searchItem
     */
    boolean removeSearchIndexDoc(SearchItem searchItem);
}
