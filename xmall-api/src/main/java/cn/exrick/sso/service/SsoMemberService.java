package cn.exrick.sso.service;

import cn.exrick.manager.dto.MemberDto;

/**
 * @author Exrickx
 */
public interface SsoMemberService {

    /**
     * 头像上传
     *
     * @param userId
     * @param token
     * @param imgData
     *
     * @return
     */
    String imageUpload(Long userId, String token, String imgData);

    /**
     * get by mid
     *
     * @param mid
     *
     * @return
     */
    MemberDto getByMid(String mid);
}
