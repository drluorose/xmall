package com.douyu.wsd.cradle.service;

import com.douyu.wsd.cradle.AppLauncher;
import com.douyu.wsd.cradle.common.ProjectConstants;
import com.douyu.wsd.framework.common.collection.CollectionUtils;
import com.douyu.wsd.framework.common.excption.biz.BusinessException;
import com.douyu.wsd.framework.common.io.FileUtils;
import com.douyu.wsd.framework.common.io.FilenameUtils;
import com.douyu.wsd.framework.common.io.filefilter.IOFileFilter;
import com.douyu.wsd.framework.common.io.filefilter.TrueFileFilter;
import com.douyu.wsd.framework.common.lang.StringUtils;
import com.douyu.wsd.framework.common.ops.SystemUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class TemplateParser {

    private static final IOFileFilter FTL_FILE_FILTER = new IOFileFilter() {
        @Override
        public boolean accept(File file) {
            return FilenameUtils.isExtension(file.getName(), "ftl");
        }

        @Override
        public boolean accept(File dir, String name) {
            return true;
        }
    };

    private final Configuration configuration;

    TemplateParser() throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        if (SystemUtils.IS_OS_LINUX) {
            cfg.setDirectoryForTemplateLoading(new File("/opt/wsd-java-service/bin"));
        } else {
            cfg.setClassForTemplateLoading(AppLauncher.class, "/");
        }
        configuration = cfg;
    }


    void parse(final File outputDir, final Map<String, Object> context) throws IOException {
        Collection<File> ftlFiles = FileUtils.listFiles(outputDir, FTL_FILE_FILTER, TrueFileFilter.INSTANCE);
        if (CollectionUtils.isEmpty(ftlFiles)) {
            return;
        }

        for (File ftlFile : ftlFiles) {
            String templateName = determineTemplateName(ftlFile);
            File outputFile = new File(ftlFile.getParent(), FilenameUtils.getBaseName(ftlFile.getName()));
            parseFile(templateName, outputFile, context);
            FileUtils.deleteQuietly(ftlFile);
        }
    }

    private String determineTemplateName(File ftlFile) {
        String rootPath = ProjectConstants.WORK_BASE_DIR.getAbsolutePath();
        String currentPath = ftlFile.getAbsolutePath();
        return ProjectConstants.WORK_BASE_DIR_NAME + "/" + StringUtils.replace(currentPath
                .substring(rootPath.length() + 1, currentPath.length()), File.separator, "/");
    }

    void parseFile(String templateName, File outputFile, Map<String, Object> context) throws IOException {
        Template template = configuration.getTemplate(templateName);
        try (FileWriter out = new FileWriter(outputFile)) {
            template.process(context, out);
        } catch (TemplateException e) {
            throw new BusinessException("输出模版异常: " + templateName, e);
        }
    }

}
