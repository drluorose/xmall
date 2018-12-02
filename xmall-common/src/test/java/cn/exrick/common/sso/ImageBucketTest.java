package cn.exrick.common.sso;

import com.aliyun.oss.model.BucketInfo;
import com.google.common.io.ByteStreams;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Slf4j
public class ImageBucketTest {

    private static ImageBucket imageBucket;

    @BeforeClass
    public static void init() {
        imageBucket = new ImageBucket("oss-cn-beijing.aliyuncs.com", "LTAIcklXsm0lpGak", "XsFwUNziSWH1tqnER5j7fAXK3WZbkf", "xmall-img");
        imageBucket.initOSSClient();
    }

    @Test
    public void ossClientTest() {

        BucketInfo info = imageBucket.getBucketInfo();
        log.info("数据中心：{}", info.getBucket().getLocation());
        log.info("创建时间：{}", info.getBucket().getCreationDate());
        log.info("用户标志：{}", info.getBucket().getOwner());
    }

    @Test
    public void ossClientTestSaveImg() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("/test.jpeg");
        InputStream inputStream = classPathResource.getInputStream();
        byte[] bytes = ByteStreams.toByteArray(inputStream);
        OssPutObjectResponse result = imageBucket.saveImage("test.jpeg", bytes);
        log.info("Response:{}", result);
    }
}
