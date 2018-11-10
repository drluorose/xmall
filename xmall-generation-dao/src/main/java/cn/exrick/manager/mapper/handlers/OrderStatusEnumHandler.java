package cn.exrick.manager.mapper.handlers;

import cn.exrick.common.enums.OrderStatusEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author dongjiejie
 */
public class OrderStatusEnumHandler extends BaseTypeHandler<OrderStatusEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, OrderStatusEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setByte(i, parameter.getNumber().byteValue());
    }

    @Override
    public OrderStatusEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Byte code = rs.getByte(columnName);
        return OrderStatusEnum.numberOf(code);
    }

    @Override
    public OrderStatusEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Byte code = rs.getByte(columnIndex);
        return OrderStatusEnum.numberOf(code);
    }

    @Override
    public OrderStatusEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Byte code = cs.getByte(columnIndex);
        return OrderStatusEnum.numberOf(code);
    }
}