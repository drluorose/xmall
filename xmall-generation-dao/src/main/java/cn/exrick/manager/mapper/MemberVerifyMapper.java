package cn.exrick.manager.mapper;

import cn.exrick.manager.pojo.MemberVerify;
import cn.exrick.manager.pojo.MemberVerifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemberVerifyMapper {
    long countByExample(MemberVerifyExample example);

    int deleteByExample(MemberVerifyExample example);

    int deleteByPrimaryKey(String id);

    int insert(MemberVerify record);

    int insertSelective(MemberVerify record);

    MemberVerify selectOneByExample(MemberVerifyExample example);

    List<MemberVerify> selectByExample(MemberVerifyExample example);

    MemberVerify selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MemberVerify record, @Param("example") MemberVerifyExample example);

    int updateByExample(@Param("record") MemberVerify record, @Param("example") MemberVerifyExample example);

    int updateByPrimaryKeySelective(MemberVerify record);

    int updateByPrimaryKey(MemberVerify record);

    int insertBatch(List<MemberVerify> records);
}