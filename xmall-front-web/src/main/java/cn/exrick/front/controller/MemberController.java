package cn.exrick.front.controller;

import cn.exrick.common.jedis.JedisClient;
import cn.exrick.common.pojo.GeetInit;
import cn.exrick.common.pojo.Result;
import cn.exrick.common.res.ResultCodeEnum;
import cn.exrick.common.utils.GeetestLib;
import cn.exrick.common.utils.IPInfoUtil;
import cn.exrick.common.utils.ResultResUtil;
import cn.exrick.common.utils.ResultUtil;
import cn.exrick.manager.dto.front.CommonDto;
import cn.exrick.manager.dto.front.Member;
import cn.exrick.manager.dto.front.MemberLoginRegist;
import cn.exrick.sso.service.LoginService;
import cn.exrick.sso.service.RegisterService;
import cn.exrick.sso.service.SsoMemberService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Exrickx
 */
@Slf4j
@RestController
@Api(description = "会员注册登录")
public class MemberController {

    @Reference
    private LoginService loginService;

    @Reference
    private RegisterService registerService;

    @Reference
    private SsoMemberService memberService;

    @Autowired
    private JedisClient jedisClient;

    @RequestMapping(value = "/member/geetestInit", method = RequestMethod.GET)
    @ApiOperation(value = "极验初始化")
    public String geetesrInit(HttpServletRequest request) {

        GeetestLib gtSdk = new GeetestLib(GeetestLib.id, GeetestLib.key, GeetestLib.newfailback);

        String resStr = "{}";

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("client_type", "web");
        param.put("ip_address", IPInfoUtil.getIpAddr(request));

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);

        //将服务器状态设置到redis中
        //request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        String key = UUID.randomUUID().toString();
        jedisClient.set(key, gtServerStatus + "");
        jedisClient.expire(key, 360);

        resStr = gtSdk.getResponseStr();
        GeetInit geetInit = new Gson().fromJson(resStr, GeetInit.class);
        geetInit.setStatusKey(key);
        return new Gson().toJson(geetInit);
    }

    @RequestMapping(value = "/member/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录")
    public Result<Member> login(@RequestBody MemberLoginRegist memberLoginRegist) {

        boolean gtResult = this.gtVerify(memberLoginRegist);
        if (gtResult) {
            // 验证成功
            try {
                Member member = loginService.userLogin(memberLoginRegist.getUserEmail(), memberLoginRegist.getUserPwd());
                return ResultResUtil.successWithData(member);
            } catch (NoSuchAlgorithmException e) {
                log.error("e", e);
                return ResultResUtil.errorWithoutData(ResultCodeEnum.CODE_LOGIN_FAIL);
            }
        } else {
            // 验证失败
            return ResultResUtil.errorWithoutData(ResultCodeEnum.CODE_LOGIN_FAIL);
        }
    }

    @RequestMapping(value = "/member/checkLogin", method = RequestMethod.GET)
    @ApiOperation(value = "判断用户是否登录")
    public Result<Member> checkLogin(@RequestParam(defaultValue = "") String token, HttpSession httpSession) {
        Member member = loginService.getUserByToken(token);

        return new ResultUtil<Member>().setData(member);
    }

    @RequestMapping(value = "/member/loginOut", method = RequestMethod.GET)
    @ApiOperation(value = "退出登录")
    public Result<Object> logout(@RequestParam(defaultValue = "") String token) {

        loginService.logout(token);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/member/register", method = RequestMethod.POST)
    @ApiOperation(value = "用户注册")
    public Result<String> register(@RequestBody MemberLoginRegist memberLoginRegist) {
        if (Objects.isNull(memberLoginRegist) || StringUtils.isBlank(memberLoginRegist.getUserEmail()) || StringUtils.isBlank(memberLoginRegist.getUserPwd())) {
            return ResultResUtil.withResultCodeEnum(ResultCodeEnum.CODE_NORMAL_FAIL_PARAM_ERROR);
        }
        boolean gtResult = this.gtVerify(memberLoginRegist);
        if (gtResult) {
            // 验证成功
            try {
                registerService.register(memberLoginRegist.getUserEmail(), memberLoginRegist.getUserPwd());
                return ResultResUtil.withResultCodeEnum(ResultCodeEnum.CODE_NORMAL_SUCCESS);
            } catch (Exception e) {
                log.error("用户注册|exception", e);
                return ResultResUtil.withResultCodeEnum(ResultCodeEnum.CODE_NORMAL_FAIL_SERVER_ERROR);
            }
        } else {
            // 验证失败
            return ResultResUtil.withResultCodeEnum(ResultCodeEnum.CODE_NORMAL_FAIL_HUMAN_VERIFY_ERROR);
        }
    }

    @RequestMapping(value = "/member/imgaeUpload", method = RequestMethod.POST)
    @ApiOperation(value = "用户头像上传")
    public Result<Object> imgaeUpload(@RequestBody CommonDto common) {

        String imgPath = memberService.imageUpload(common.getUserId(), common.getToken(), common.getImgData());
        return new ResultUtil<Object>().setData(imgPath);
    }

    /**
     * gt 验证
     *
     * @param memberLoginRegist
     *
     * @return
     */
    private boolean gtVerify(MemberLoginRegist memberLoginRegist) {

        //极验验证
        GeetestLib gtSdk = new GeetestLib(GeetestLib.id, GeetestLib.key, GeetestLib.newfailback);

        String challenge = memberLoginRegist.getChallenge();
        String validate = memberLoginRegist.getValidate();
        String seccode = memberLoginRegist.getSeccode();
        int gt_server_status_code = Integer.parseInt(jedisClient.get(memberLoginRegist.getStatusKey()));

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();

        int gtResult = 0;

        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证
            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
            log.info("极验证|enhencedValidateRequest|gtResult:{}", gtResult);
        } else {
            // gt-server非正常情况下，进行failback模式验证
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
            log.info("极验证|failbackValidateRequest|gtResult:{}", gtResult);
        }
        if (gtResult == 1) {
            return true;
        }
        return false;
    }
}
