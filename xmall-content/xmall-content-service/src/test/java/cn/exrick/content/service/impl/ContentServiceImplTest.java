package cn.exrick.content.service.impl;

import cn.exrick.content.service.ContentService;
import cn.exrick.manager.mapper.TbOrderMapper;
import cn.exrick.manager.mapper.TbPanelMapper;
import cn.exrick.manager.mapper.ext.TbOrderExtMapper;
import cn.exrick.manager.pojo.TbOrderExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-dao.xml"})
public class ContentServiceImplTest {

    @Autowired
    private TbPanelMapper tbPanelMapper;

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbOrderExtMapper tbOrderExtMapper;

    @Test
    public void test() {
//        TbPanelExample tbPanelExample = new TbPanelExample();
//        tbPanelMapper.selectByExample(tbPanelExample);
//        tbOrderMapper.countByExample(new TbOrderExample());
        tbOrderMapper.selectByExample(new TbOrderExample());
        tbOrderExtMapper.selectByExample(new TbOrderExample());
    }

}
