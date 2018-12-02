package cn.exrick.common.sso;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Slf4j
public class ImageBucket {

    private String endPoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    public ImageBucket(String endPoint, String accessKeyId, String accessKeySecret, String bucketName) {
        this.endPoint = endPoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;
    }

    private OSSClient ossClient;

    /**
     * init oss client
     */
    public void initOSSClient() {
        if (StringUtils.isBlank(endPoint) || StringUtils.isBlank(accessKeyId) ||
            StringUtils.isBlank(accessKeySecret) || StringUtils.isBlank(bucketName)) {
            throw new IllegalArgumentException("oss client param is error");
        }
        ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        if (!ossClient.doesBucketExist(bucketName)) {
            ossClient.createBucket(bucketName);
        }
    }

    /**
     * save image bytes
     *
     * @param keyName    with file suffix
     * @param imageBytes file bytes
     *
     * @return
     */
    public OssPutObjectResponse saveImage(String keyName, byte[] imageBytes) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(imageBytes);
        return saveImage(keyName, inputStream);
    }

    /**
     * 通过流的方式上传
     *
     * @param keyName
     * @param imageInputStream
     *
     * @return
     *
     * @throws IOException
     */
    public OssPutObjectResponse saveImage(String keyName, InputStream imageInputStream) throws IOException {
        PutObjectResult putObjectResult = ossClient.putObject(bucketName, keyName, imageInputStream);
        OssPutObjectResponse ossPutObjectResponse = new OssPutObjectResponse();
        ossPutObjectResponse.setClientCRC(putObjectResult.getClientCRC());
        ossPutObjectResponse.setServerCRC(putObjectResult.getServerCRC());
        ossPutObjectResponse.setETag(putObjectResult.getETag());
        ossPutObjectResponse.setRequestId(putObjectResult.getETag());
        if (Objects.nonNull(putObjectResult.getResponse())) {
            String responseStr = EntityUtils.toString(putObjectResult.getResponse().getHttpResponse().getEntity());
            ossPutObjectResponse.setResponseStr(responseStr);
        }
        return ossPutObjectResponse;
    }

    /**
     * 获取BucketInfo
     *
     * @return
     */
    public BucketInfo getBucketInfo() {
        return ossClient.getBucketInfo(bucketName);
    }

    /**
     * shutdown oss client
     */
    public void close() {
        if (Objects.isNull(ossClient)) {
            ossClient.shutdown();
        }
    }
}
