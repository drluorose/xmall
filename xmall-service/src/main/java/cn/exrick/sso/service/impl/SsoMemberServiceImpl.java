package cn.exrick.sso.service.impl;

import cn.exrick.common.exception.XmallException;
import cn.exrick.common.jedis.JedisClient;
import cn.exrick.manager.dto.MemberDto;
import cn.exrick.manager.dto.front.Member;
import cn.exrick.manager.mapper.TbMemberMapper;
import cn.exrick.manager.pojo.TbMember;
import cn.exrick.manager.pojo.TbMemberExample;
import cn.exrick.sso.service.LoginService;
import cn.exrick.sso.service.SsoMemberService;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Exrickx
 */
@Slf4j
@Component
@Service(interfaceClass = SsoMemberService.class)
public class SsoMemberServiceImpl implements SsoMemberService {

    @Autowired
    private LoginService loginService;

    @Autowired
    private TbMemberMapper tbMemberMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public String imageUpload(Long userId, String token, String imgData) {

        TbMember tbMember = tbMemberMapper.selectByPrimaryKey(userId);
        if (tbMember == null) {
            throw new XmallException("通过id获取用户失败");
        }
        tbMember.setFile("");
        if (tbMemberMapper.updateByPrimaryKey(tbMember) != 1) {
            throw new XmallException("更新用户头像失败");
        }

        //更新缓存
        Member member = loginService.getUserByToken(token);
        member.setFile("");
        jedisClient.set("SESSION:" + token, new Gson().toJson(member));
        return "";
    }

    @Override
    public MemberDto getByMid(String mid) {
        if (StringUtils.isBlank(mid)) {
            throw new IllegalArgumentException("get by mid");
        }
        TbMemberExample example = new TbMemberExample();
        example.createCriteria().andMidEqualTo(mid);
        TbMember tbMember = tbMemberMapper.selectOneByExample(example);
        if (Objects.isNull(tbMember)) {
            throw new IllegalArgumentException("mid is error :" + mid);
        }
        return new MemberDto(tbMember);
    }
}
