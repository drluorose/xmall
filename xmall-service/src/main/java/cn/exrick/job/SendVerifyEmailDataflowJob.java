package cn.exrick.job;

import cn.exrick.common.enums.DoStatusEnum;
import cn.exrick.manager.pojo.MemberVerify;
import cn.exrick.sso.biz.RegisterBiz;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.elasticjob.lite.annotation.ElasticDataflowJob;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Slf4j
@ElasticDataflowJob("0/10 * * * * ?")
@Component
public class SendVerifyEmailDataflowJob implements DataflowJob<MemberVerify> {

    @Autowired
    private RegisterBiz registerBiz;

    @Override
    public List<MemberVerify> fetchData(ShardingContext shardingContext) {
        List<MemberVerify> memberVerify = registerBiz.getMemberVerify(DoStatusEnum.UNDO);
        log.info("SendVerifyEmailDataflowJob execute {}", Joiner.on(",").join(memberVerify.stream().map(MemberVerify::getId).collect(Collectors.toList())));
        return memberVerify;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<MemberVerify> list) {
        Map<String, Boolean> result = Maps.newHashMap();
        for (MemberVerify memberVerify : list) {
            boolean sendVerifyEmailResult = registerBiz.sendVerifyEmail(memberVerify);
            result.put(memberVerify.getId(), sendVerifyEmailResult);
        }
        log.info("MemberResult :{}", Joiner.on(",").join(result.entrySet()));
    }
}
