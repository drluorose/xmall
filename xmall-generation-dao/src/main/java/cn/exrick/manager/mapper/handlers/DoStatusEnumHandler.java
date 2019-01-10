package cn.exrick.manager.mapper.handlers;

import cn.exrick.common.enums.EnableStatusEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author dongjiejie
 */
public class DoStatusEnumHandler extends BaseTypeHandler<EnableStatusEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, EnableStatusEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setByte(i, parameter.getNumber().byteValue());
    }

    @Override
    public EnableStatusEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Byte code = rs.getByte(columnName);
        return EnableStatusEnum.numberOf(code);
    }

    @Override
    public EnableStatusEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Byte code = rs.getByte(columnIndex);
        return EnableStatusEnum.numberOf(code);
    }

    @Override
    public EnableStatusEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Byte code = cs.getByte(columnIndex);
        return EnableStatusEnum.numberOf(code);
    }
}