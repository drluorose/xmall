package cn.exrick.manager.mapper.ext;

import cn.exrick.manager.pojo.TbMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbMemberExtMapper {

    List<TbMember> selectByMemberInfo(@Param("search") String search, @Param("minDate") String minDate,
                                      @Param("maxDate") String maxDate, @Param("orderCol") String orderCol,
                                      @Param("orderDir") String orderDir);

    List<TbMember> selectByRemoveMemberInfo(@Param("search") String search, @Param("minDate") String minDate,
                                            @Param("maxDate") String maxDate, @Param("orderCol") String orderCol,
                                            @Param("orderDir") String orderDir);
}