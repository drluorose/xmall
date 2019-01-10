package cn.exrick.manager.mapper.handlers;

import cn.exrick.common.enums.MemberStateEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author dongjiejie
 */
public class MemberStateEnumHandler extends BaseTypeHandler<MemberStateEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, MemberStateEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setByte(i, parameter.getNumber().byteValue());
    }

    @Override
    public MemberStateEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Byte code = rs.getByte(columnName);
        return MemberStateEnum.numberOf(code);
    }

    @Override
    public MemberStateEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Byte code = rs.getByte(columnIndex);
        return MemberStateEnum.numberOf(code);
    }

    @Override
    public MemberStateEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Byte code = cs.getByte(columnIndex);
        return MemberStateEnum.numberOf(code);
    }
}