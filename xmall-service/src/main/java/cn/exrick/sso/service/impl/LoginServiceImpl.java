package cn.exrick.sso.service.impl;

import cn.exrick.common.enums.EnableStatusEnum;
import cn.exrick.common.enums.MemberStateEnum;
import cn.exrick.common.jedis.JedisClient;
import cn.exrick.common.jwt.JwtAlgorithmEnum;
import cn.exrick.common.jwt.JwtKeyInfo;
import cn.exrick.common.utils.ObjectId;
import cn.exrick.manager.dto.DtoUtil;
import cn.exrick.manager.dto.front.Member;
import cn.exrick.manager.mapper.TbMemberJwtKeyMapper;
import cn.exrick.manager.mapper.TbMemberMapper;
import cn.exrick.manager.pojo.TbMember;
import cn.exrick.manager.pojo.TbMemberExample;
import cn.exrick.manager.pojo.TbMemberJwtKey;
import cn.exrick.manager.pojo.TbMemberJwtKeyExample;
import cn.exrick.sso.service.LoginService;
import com.alibaba.dubbo.config.annotation.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Exrickx
 */
@Slf4j
@Component
@Service(interfaceClass = LoginService.class)
public class LoginServiceImpl implements LoginService {

    private static final long valid_time = 1000 * 60 * 60 * 12;

    @Autowired
    private TbMemberMapper tbMemberMapper;

    @Autowired
    private TbMemberJwtKeyMapper tbMemberJwtKeyMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    private Gson gson = new GsonBuilder().create();

    @Override
    public Member userLogin(String email, String password) throws NoSuchAlgorithmException {

        TbMemberExample example = new TbMemberExample();
        TbMemberExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(MemberStateEnum.VERIFIED);
        criteria.andEmailEqualTo(email);
        TbMember tbMember = tbMemberMapper.selectOneByExample(example);
        if (Objects.isNull(tbMember)) {
            Member member = new Member();
            member.setState(0);
            member.setMessage("用户名或密码错误");
            return member;
        }
        //md5加密
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(tbMember.getPassword())) {
            Member member = new Member();
            member.setState(0);
            member.setMessage("用户名或密码错误");
            return member;
        }
        String token = UUID.randomUUID().toString();
        Member member = DtoUtil.TbMemer2Member(tbMember);
        member.setToken(token);
        member.setState(1);
        // 用户信息写入redis：key："SESSION:token" value："user"
        jedisClient.set("SESSION:" + token, new Gson().toJson(member));
        jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
        deleteJwtKey(tbMember.getMid());
        Algorithm algorithm = createJwAlgorithm(tbMember.getMid());
        String signStr = sign(member, algorithm);
        member.setJwtSign(signStr);

        return member;
    }

    @Override
    public Member getUserByToken(String token) {

        String json = jedisClient.get("SESSION:" + token);
        if (json == null) {
            Member member = new Member();
            member.setState(0);
            member.setMessage("用户登录已过期");
            return member;
        }
        //重置过期时间
        jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
        Member member = new Gson().fromJson(json, Member.class);
        return member;
    }

    private void deleteJwtKey(String mid) {
        TbMemberJwtKeyExample example = new
            TbMemberJwtKeyExample();
        example.createCriteria().andMidEqualTo(mid);

        TbMemberJwtKey tbMemberJwtKey = new TbMemberJwtKey();
        tbMemberJwtKey.setStatus(EnableStatusEnum.DISABLED);
        tbMemberJwtKeyMapper.updateByExampleSelective(tbMemberJwtKey, example);
    }

    private Algorithm createJwAlgorithm(String mid) throws NoSuchAlgorithmException {
        Pair<ECPublicKey, ECPrivateKey> pair = JwtAlgorithmEnum.ECDSA256.buildECKey();
        String publicKeyStr = JwtAlgorithmEnum.ECDSA256.getBase64KeyStr(pair.getLeft());
        String privateKeyStr = JwtAlgorithmEnum.ECDSA256.getBase64KeyStr(pair.getRight());
        Algorithm algorithm = JwtAlgorithmEnum.ECDSA256.getAlgorithm(pair);
        TbMemberJwtKey tbMemberJwtKey = new TbMemberJwtKey();
        tbMemberJwtKey.setId(ObjectId.getId());
        tbMemberJwtKey.setMid(mid);
        tbMemberJwtKey.setJwtAlgorithmName(algorithm.getName());
        tbMemberJwtKey.setStatus(EnableStatusEnum.ENABLED);
        tbMemberJwtKey.setPublicKey(publicKeyStr);
        tbMemberJwtKey.setPrivateKey(privateKeyStr);
        tbMemberJwtKeyMapper.insert(tbMemberJwtKey);
        return algorithm;
    }

    private String sign(Member member, Algorithm algorithm) {
        Map<String, Object> header = Maps.newHashMap();
        header.put("alg", algorithm.getName());
        header.put("typ", "jwt");

        String signStr = JWT.create().withHeader(header)
            .withIssuedAt(new Date())
            .withIssuer("http://www.xmall.com")
            .withSubject(gson.toJson(member))
            .withExpiresAt(getExpireDate())
            .withNotBefore(new Date())
            .sign(algorithm);
        return signStr;
    }

    private Date getExpireDate() {
        return new Date(System.currentTimeMillis() + valid_time);
    }

    @Override
    public int logout(String token) {

        jedisClient.del("SESSION:" + token);
        return 1;
    }

    @Override
    public JwtKeyInfo getJwtKeyInfo(String mid) {
        TbMemberJwtKeyExample example = new TbMemberJwtKeyExample();
        example.createCriteria().andMidEqualTo(mid)
            .andStatusEqualTo(EnableStatusEnum.ENABLED);
        List<TbMemberJwtKey> tbMemberJwtKeys = tbMemberJwtKeyMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(tbMemberJwtKeys) || tbMemberJwtKeys.size() > 1) {
            throw new IllegalArgumentException("mid 没有登录或者key很多");
        }
        TbMemberJwtKey tbMemberJwtKey = tbMemberJwtKeys.get(0);
        return new JwtKeyInfo(tbMemberJwtKey.getPublicKey(), tbMemberJwtKey.getPrivateKey());
    }

}
