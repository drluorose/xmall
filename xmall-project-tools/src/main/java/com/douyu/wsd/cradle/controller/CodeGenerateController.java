package com.douyu.wsd.cradle.controller;

import com.douyu.wsd.cradle.service.CodeGenerateService;
import com.douyu.wsd.cradle.vo.GenerateReq;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class CodeGenerateController {

    private static final String DEFAULT_DOWNLOAD_FILE_NAME = "x-project";

    @Resource
    private CodeGenerateService codeGenerateService;

    @GetMapping("/generate")
    public ResponseEntity<byte[]> genProject(@Validated GenerateReq req) throws IOException {
        if (req.isUseLocalModel()) {
            File file = codeGenerateService.generate(req);
            File projectDir = new File(req.getLocalPath() + File.separator + req.getProjectName());
            if (!projectDir.exists() || !projectDir.isDirectory()) {
                FileUtils.forceMkdir(projectDir);
            }

            FileUtils.deleteDirectory(projectDir);
            FileUtils.copyDirectory(file, projectDir);

            HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(new byte[0], headers, HttpStatus.OK);
        } else {
            File file = codeGenerateService.generate(req);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //解决文件名中文乱码问题
            String fileName = new String(getDownloadFileName(req).getBytes("UTF-8"), "ISO8859-1");
            headers.setContentDispositionFormData("attachment", fileName);
            byte[] byteArray = FileUtils.readFileToByteArray(file);
            return new ResponseEntity<>(byteArray, headers, HttpStatus.OK);
        }
    }

    private static String getDownloadFileName(GenerateReq req) {
        String defaultProjectName = req.getProjectName() + ".zip";
        return StringUtils.defaultIfBlank(defaultProjectName, DEFAULT_DOWNLOAD_FILE_NAME);
    }

}
