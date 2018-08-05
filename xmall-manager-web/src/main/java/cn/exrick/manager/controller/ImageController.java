package cn.exrick.manager.controller;

import cn.exrick.common.pojo.KindEditorResult;
import cn.exrick.common.pojo.Result;
import cn.exrick.common.utils.ResultUtil;
import com.google.common.base.Splitter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.mikesu.fastdfs.FastdfsClient;
import net.mikesu.fastdfs.FastdfsClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Exrickx
 */
@RestController
@Api(description = "图片上传统一接口")
public class ImageController {

    @Value("${fadf.trackers}")
    private String fdfsTrackers;

    private FastdfsClient fastdfsClient;

    @PostConstruct
    public void initFastDfsClient() {
        if (StringUtils.isEmpty(fdfsTrackers)) {
            throw new IllegalArgumentException("input fdfsTrackers is null,value is :" + fdfsTrackers);
        }
        Splitter splitter = Splitter.on(",").omitEmptyStrings().trimResults();
        List<String> trackers = splitter.splitToList(fdfsTrackers);
        fastdfsClient = FastdfsClientFactory.getFastdfsClient(trackers);
    }

    @RequestMapping(value = "/image/imageUpload", method = RequestMethod.POST)
    @ApiOperation(value = "WebUploader图片上传")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile files,
                                     HttpServletRequest request) throws Exception {
        String filename = upload2Fdfs(files);
        return new ResultUtil<String>().setData(filename);
    }

    @RequestMapping(value = "/kindeditor/imageUpload", method = RequestMethod.POST)
    @ApiOperation(value = "KindEditor图片上传")
    public KindEditorResult kindeditor(@RequestParam("imgFile") MultipartFile files, HttpServletRequest request) throws Exception {
        String filename = upload2Fdfs(files);
        KindEditorResult kindEditorResult = new KindEditorResult();
        kindEditorResult.setUrl(filename);
        kindEditorResult.setError(1);
        kindEditorResult.setMessage("上传失败");
        return kindEditorResult;
    }

    private String upload2Fdfs(MultipartFile file) throws Exception {
        byte[] fileBytes = file.getBytes();
        String originalFilename = file.getOriginalFilename();
        String fileId = fastdfsClient.upload(fileBytes, originalFilename);
        return fastdfsClient.getUrl(fileId);
    }
}
