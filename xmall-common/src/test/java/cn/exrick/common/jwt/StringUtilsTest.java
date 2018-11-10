package cn.exrick.common.jwt;

import cn.exrick.common.utils.CurrencyStringUtils;
import cn.exrick.common.utils.CurrencyStringUtils.CurrencyFormat;
import cn.exrick.common.utils.ObjectId;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
public class StringUtilsTest {

    @Test
    public void test() {
        Assert.assertEquals("343434.09", CurrencyStringUtils.toString(new BigDecimal("343434.09090934"), CurrencyFormat.Money));
    }

    @Test
    public void testObjectId() {
        ObjectId objectId = ObjectId.get();
        System.out.println(objectId.toDigitString());
        System.out.println(objectId.toHexString());
    }
}
