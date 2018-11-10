package cn.exrick.manager.component;

import cn.exrick.manager.dto.front.OrderPayAction;
import cn.exrick.manager.pojo.TbOrder;
import cn.exrick.manager.pojo.TbOrderItem;
import cn.exrick.manager.pojo.TbOrderShipping;
import cn.exrick.manager.pojo.Transaction;

import java.io.IOException;
import java.util.List;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
public interface PayPalComponent {

    String SELF = "self";

    String APPROVAL_URL = "approval_url";

    String EXECUTE = "execute";

    /**
     * 支付
     *
     * @param tbOrder
     * @param tbItems
     * @param tbOrderShipping
     * @param
     *
     * @return
     */
    OrderPayAction pay(TbOrder tbOrder,
                       List<TbOrderItem> tbItems,
                       TbOrderShipping tbOrderShipping,
                       Transaction transaction) throws IOException;
}
