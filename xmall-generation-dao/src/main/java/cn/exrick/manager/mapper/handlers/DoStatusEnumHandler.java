package cn.exrick.manager.mapper.handlers;

import cn.exrick.common.enums.DoStatusEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author dongjiejie
 */
public class DoStatusEnumHandler extends BaseTypeHandler<DoStatusEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, DoStatusEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setByte(i, parameter.getNumber().byteValue());
    }

    @Override
    public DoStatusEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Byte code = rs.getByte(columnName);
        return DoStatusEnum.numberOf(code);
    }

    @Override
    public DoStatusEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Byte code = rs.getByte(columnIndex);
        return DoStatusEnum.numberOf(code);
    }

    @Override
    public DoStatusEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Byte code = cs.getByte(columnIndex);
        return DoStatusEnum.numberOf(code);
    }
}