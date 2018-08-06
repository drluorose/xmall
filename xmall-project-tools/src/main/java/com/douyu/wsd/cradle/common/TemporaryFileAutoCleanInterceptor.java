package com.douyu.wsd.cradle.common;

import com.douyu.wsd.framework.common.collection.CollectionUtils;
import com.douyu.wsd.framework.common.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Slf4j
public class TemporaryFileAutoCleanInterceptor extends HandlerInterceptorAdapter {

    private static final String DOUYU_TEMPORARY_FILE_KEY = "com.douyu.request.temporary.file";

    /**
     * 将临时文件注册到request中，以便在拦截器的finally阶段清理文件
     *
     * @param file    临时文件
     */
    public static void registerForDelete(File file) {
        HttpServletRequest request = SpringMvcUtils.getRequest();
        Object tmpFiles = request.getAttribute(DOUYU_TEMPORARY_FILE_KEY);
        if (tmpFiles != null && tmpFiles instanceof Vector) {
            Vector<File> vector = (Vector<File>) tmpFiles;
            vector.add(file);
        }

    }

    /**
     * 读取该请求中注册的临时文件
     *
     * @param request 请求
     * @return 临时文件列表
     */
    private static Iterable<File> getTemporaryFiles(HttpServletRequest request) {
        Object tmpFiles = request.getAttribute(DOUYU_TEMPORARY_FILE_KEY);
        Vector<File> vector;
        if (tmpFiles != null && tmpFiles instanceof Vector) {
            vector = (Vector<File>) tmpFiles;
        } else {
            vector = null;
        }
        return vector;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        boolean result = super.preHandle(request, response, handler);
        request.setAttribute(DOUYU_TEMPORARY_FILE_KEY, new Vector());
        return result;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
        Iterable<File> files = getTemporaryFiles(request);
        if (CollectionUtils.isNotEmpty(files)) {
            try {
                for (File file : files) {
                    if (file == null) {
                        continue;
                    }
                    if (file.isDirectory()) {
                        FileUtils.deleteDirectory(file);
                    } else {
                        boolean success = file.delete();
                        if (!success) {
                            log.warn("delete file {} failed", file);
                        }
                    }
                }
            } catch (IOException e) {
                log.error("file delete failed: {}", files, e);
            }
        }
    }
}
