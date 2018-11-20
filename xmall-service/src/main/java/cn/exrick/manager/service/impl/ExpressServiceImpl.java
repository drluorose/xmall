package cn.exrick.manager.service.impl;

import cn.exrick.manager.mapper.TbExpressMapper;
import cn.exrick.manager.pojo.TbExpress;
import cn.exrick.manager.pojo.TbExpressExample;
import cn.exrick.manager.service.ExpressService;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author Exrickx
 */
@Slf4j
@Component
@Service(interfaceClass = ExpressService.class)
public class ExpressServiceImpl implements ExpressService {

    @Autowired
    private TbExpressMapper tbExpressMapper;

    @Override
    public List<TbExpress> getExpressList() {

        TbExpressExample example = new TbExpressExample();
        example.setOrderByClause("sort_order asc");
        return tbExpressMapper.selectByExample(example);
    }

    @Override
    public int addExpress(TbExpress tbExpress) {

        tbExpress.setCreated(new Date());
        tbExpressMapper.insert(tbExpress);
        return 1;
    }

    @Override
    public int updateExpress(TbExpress tbExpress) {

        tbExpress.setUpdated(new Date());
        tbExpressMapper.updateByPrimaryKey(tbExpress);
        return 1;
    }

    @Override
    public int delExpress(int id) {

        tbExpressMapper.deleteByPrimaryKey(id);
        return 1;
    }
}
