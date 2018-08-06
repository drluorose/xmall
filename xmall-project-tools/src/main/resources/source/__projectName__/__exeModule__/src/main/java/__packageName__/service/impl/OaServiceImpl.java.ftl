package @packageName@.service.impl;

import com.douyu.wsd.framework.oa.OaException;
import com.douyu.wsd.framework.oa.bean.OaGetUIdPo;
import com.douyu.wsd.framework.oa.bean.OaGetUIdReq;
import com.douyu.wsd.framework.oa.bean.OaUserInfoPo;
import com.douyu.wsd.framework.oa.config.OaConfig;
import com.douyu.wsd.framework.oa.service.AbstractOaService;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class OaServiceImpl extends AbstractOaService {

    @Resource
    private OaConfig oaConfig;

    @Resource
    private RestTemplate restTemplate;

    @Override
    public OaConfig getOaConfig() {
        return oaConfig;
    }

    @Override
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public OaUserInfoPo getUserInfoFromOA(String code) throws OaException {
        OaGetUIdPo oaGetUIdPo = getAccessToken(code);
        if (oaGetUIdPo != null) {
            return getUserInfoByAccessToken(oaGetUIdPo);
        }

        return null;
    }

    private OaGetUIdPo getAccessToken(String code)
            throws OaException {
        OaGetUIdPo res;
        OaGetUIdReq oaGetUIdReq = new OaGetUIdReq(code, getOaConfig().getClientId(), getOaConfig().getClientSecret(),
                getOaConfig().getGrantType(), getOaConfig().getRedirectUrl());
        log.info("getUid|调用OA获取UID|req:{}", oaGetUIdReq);
        try {
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add("Content-Type", "application/x-www-form-urlencoded");
            HttpEntity entity = new HttpEntity(oaGetUIdReq.buildFormData(), requestHeaders);
            ResponseEntity responseEntity = getRestTemplate()
                    .exchange(getOaConfig().getAccessTokenUrl(), HttpMethod.POST, entity, OaGetUIdPo.class,
                            new Object[0]);

            res = (OaGetUIdPo) responseEntity.getBody();
            log.info("getUid|调用OA获取UID|res:{}", res);

            if ((StringUtils.isEmpty(res.getAccess_token()))) {
                return null;
            }
        } catch (Exception e) {
            log.error("getUid|调用OA获取UID异常|{}", oaGetUIdReq, e);
            throw new OaException(Integer.valueOf(-2), "调用OA获取UID异常", e);
        }
        return res;
    }

}
