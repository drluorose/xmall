package cn.exrick.content.service.impl;

import cn.exrick.manager.dto.TbPanelDto;
import cn.exrick.manager.pojo.TbOrder;
import cn.exrick.manager.pojo.TbPanel;
import cn.exrick.manager.pojo.TbPanelContent;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
public class Test1 {

    @Test
    public void test(){
        TbPanel tbOrder = new TbPanel();
        tbOrder.setId(1000);
        List<TbPanelDto> list = Lists.newArrayList(Lists.transform(Lists.newArrayList(tbOrder), TbPanelDto::new));
        for (TbPanelDto dto:list){
            dto.setId(999);
        }
        System.out.println(list);
    }
}
