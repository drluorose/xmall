package cn.exrick.manager.mapper.handlers;

import cn.exrick.common.enums.PaymentEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author dongjiejie
 */
public class PaymentEnumHandler extends BaseTypeHandler<PaymentEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, PaymentEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setByte(i, parameter.getNumber().byteValue());
    }

    @Override
    public PaymentEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Byte code = rs.getByte(columnName);
        return PaymentEnum.numberOf(code);
    }

    @Override
    public PaymentEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Byte code = rs.getByte(columnIndex);
        return PaymentEnum.numberOf(code);
    }

    @Override
    public PaymentEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Byte code = cs.getByte(columnIndex);
        return PaymentEnum.numberOf(code);
    }
}