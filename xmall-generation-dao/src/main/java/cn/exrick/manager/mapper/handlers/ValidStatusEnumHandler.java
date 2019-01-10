package cn.exrick.manager.mapper.handlers;

import cn.exrick.common.enums.ValidStatusEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author dongjiejie
 */
public class ValidStatusEnumHandler extends BaseTypeHandler<ValidStatusEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ValidStatusEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setByte(i, parameter.getNumber().byteValue());
    }

    @Override
    public ValidStatusEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Byte code = rs.getByte(columnName);
        return ValidStatusEnum.numberOf(code);
    }

    @Override
    public ValidStatusEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Byte code = rs.getByte(columnIndex);
        return ValidStatusEnum.numberOf(code);
    }

    @Override
    public ValidStatusEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Byte code = cs.getByte(columnIndex);
        return ValidStatusEnum.numberOf(code);
    }
}