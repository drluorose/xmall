package cn.exrick.manager.web.controller;

import cn.exrick.common.pojo.KindEditorResult;
import cn.exrick.common.pojo.Result;
import cn.exrick.common.sso.ImageBucket;
import cn.exrick.common.utils.ObjectId;
import cn.exrick.common.utils.ResultUtil;
import cn.exrick.manager.web.config.AliyunConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author Exrickx
 */
@RestController
@Api(description = "图片上传统一接口")
public class ImageController {

    @Autowired
    private AliyunConfig aliyunConfig;

    private ImageBucket imageBucket;

    @PostConstruct
    public void initImageBucket() {
        imageBucket = new ImageBucket(aliyunConfig.getOss().getEndPoint(),
            aliyunConfig.getOss().getAccessKeyId(),
            aliyunConfig.getOss().getAccessKeySecret(),
            aliyunConfig.getOss().getImgBuckName());
        imageBucket.initOSSClient();
    }

    @PreDestroy
    public void close() {
        if (Objects.isNull(imageBucket)) {
            return;
        }
        imageBucket.close();
    }

    @RequestMapping(value = "/image/imageUpload", method = RequestMethod.POST)
    @ApiOperation(value = "WebUploader图片上传")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile files,
                                     HttpServletRequest request) throws Exception {
        String filename = upload(files);
        return new ResultUtil<String>().setData(filename);
    }

    @RequestMapping(value = "/kindeditor/imageUpload", method = RequestMethod.POST)
    @ApiOperation(value = "KindEditor图片上传")
    public KindEditorResult kindeditor(@RequestParam("imgFile") MultipartFile files, HttpServletRequest request) throws Exception {
        String filename = upload(files);
        KindEditorResult kindEditorResult = new KindEditorResult();
        kindEditorResult.setUrl(filename);
        kindEditorResult.setError(1);
        kindEditorResult.setMessage("上传失败");
        return kindEditorResult;
    }

    private String upload(MultipartFile file) throws Exception {
        ObjectId objectId = new ObjectId();
        InputStream fileInputStream = file.getInputStream();
        String originalFilename = file.getOriginalFilename();
        String fileName = objectId.toHexString() + "." + getPicSuffix(originalFilename);
        imageBucket.saveImage(fileName, fileInputStream);
        return aliyunConfig.getOss().getImgBuckOrigin() + fileName;
    }

    public static String getPicSuffix(String img_path) {
        //如果图片地址为null或者地址中没有"."就返回""
        if (img_path == null || img_path.indexOf(".") == -1) {
            return "";
        }
        return img_path.substring(img_path.lastIndexOf(".") + 1).
            trim().toLowerCase();
    }
}
