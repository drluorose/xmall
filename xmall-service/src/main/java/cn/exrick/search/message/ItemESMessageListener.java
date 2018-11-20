package cn.exrick.search.message;

import cn.exrick.manager.mapper.ext.TbItemExtMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * 监听商品添加消息，接收消息后，将对应的商品信息同步到索引库
 */
@Slf4j
@Component
public class ItemESMessageListener implements MessageListener {

    @Autowired
    private TbItemExtMapper tbItemExtMapper;


    @Override
    public void onMessage(Message message) {
//        try {
//            //从消息中取商品id
//            TextMessage textMessage = (TextMessage) message;
//
//            log.info("得到消息：" + textMessage.getText());
//
//            String[] text = textMessage.getText().split(",");
//            Long itemId = new Long(text[1]);
//            //等待事务提交
//            Thread.sleep(1000);
//
//            //更新索引
//            Settings settings = Settings.builder()
//                .put("cluster.name", clusterName).build();
//            TransportClient client = new PreBuiltTransportClient(settings)
//                .addTransportAddress(new TransportAddress(InetAddress.getByName(connectIp), 9300));
//
//            if ("add".equals(text[0])) {
//                //根据商品id查询商品信息
//                SearchItem searchItem = tbItemExtMapper.getItemById(itemId);
//                String image = searchItem.getProductImageBig();
//                if (image != null && !"".equals(image)) {
//                    String[] strings = image.split(",");
//                    image = strings[0];
//                } else {
//                    image = "";
//                }
//                searchItem.setProductImageBig(image);
//                IndexResponse indexResponse = client.prepareIndex(itemIndex, itemType, String.valueOf(searchItem.getProductId()))
//                    .setSource(jsonBuilder()
//                        .startObject()
//                        .field("productId", searchItem.getProductId())
//                        .field("salePrice", searchItem.getSalePrice())
//                        .field("productName", searchItem.getProductName())
//                        .field("subTitle", searchItem.getSubTitle())
//                        .field("productImageBig", searchItem.getProductImageBig())
//                        .field("categoryName", searchItem.getCategoryName())
//                        .field("cid", searchItem.getCid())
//                        .endObject()
//                    ).get();
//            } else if ("delete".equals(text[0])) {
//                DeleteResponse deleteResponse = client.prepareDelete(itemIndex, itemType, String.valueOf(itemId)).get();
//            }
//
//            log.info("处理消息成功");
//
//            client.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

}
