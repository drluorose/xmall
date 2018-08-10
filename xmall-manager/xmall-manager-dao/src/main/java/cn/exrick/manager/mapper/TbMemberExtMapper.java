package cn.exrick.manager.mapper;

import java.util.List;

import cn.exrick.manager.pojo.TbMember;
import org.apache.ibatis.annotations.Param;

public interface TbMemberExtMapper extends TbMemberMapper {

    List<TbMember> selectByMemberInfo(@Param("search") String search, @Param("minDate") String minDate,
                                      @Param("maxDate") String maxDate, @Param("orderCol") String orderCol,
                                      @Param("orderDir") String orderDir);

    List<TbMember> selectByRemoveMemberInfo(@Param("search") String search, @Param("minDate") String minDate,
                                            @Param("maxDate") String maxDate, @Param("orderCol") String orderCol,
                                            @Param("orderDir") String orderDir);
}