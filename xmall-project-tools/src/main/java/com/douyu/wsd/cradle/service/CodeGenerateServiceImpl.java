package com.douyu.wsd.cradle.service;

import com.douyu.wsd.cradle.common.ProjectConstants;
import com.douyu.wsd.cradle.common.TemporaryFileAutoCleanInterceptor;
import com.douyu.wsd.cradle.domain.MavenModule;
import com.douyu.wsd.cradle.utils.Underline2Camel;
import com.douyu.wsd.cradle.utils.ZipUtils;
import com.douyu.wsd.cradle.vo.GenerateReq;
import com.douyu.wsd.framework.common.io.FileUtils;
import com.douyu.wsd.framework.common.json.JsonUtils;
import com.douyu.wsd.framework.common.lang.IdGenerator;
import com.douyu.wsd.framework.common.lang.StringUtils;
import com.douyu.wsd.framework.common.ops.SystemUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

import com.google.common.base.Charsets;
import com.mysql.jdbc.Driver;
import lombok.extern.slf4j.Slf4j;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
@Slf4j
public class CodeGenerateServiceImpl implements CodeGenerateService {


    private TemplateParser parser;


    @PostConstruct
    public void init() throws IOException {
        parser = new TemplateParser();
    }

    @Override
    public File generate(GenerateReq req) throws IOException {

        File outputDir = createRequestLevelDir("request_" + IdGenerator.uuid());

        TemporaryFileAutoCleanInterceptor.registerForDelete(outputDir);

        Map<String, Object> context = prepareContext(req, outputDir);

        resolveDynamicDir(outputDir, context);

        parser.parse(outputDir, context);

        if (req.isUseLocalModel()) {
            return outputDir;
        } else {
            File zipFile = buildZip(outputDir);

            TemporaryFileAutoCleanInterceptor.registerForDelete(zipFile);

            return zipFile;
        }
    }

    private File createRequestLevelDir(String dirName) throws IOException {
        File dir = new File(ProjectConstants.WORK_BASE_DIR, dirName);
        if (!dir.exists() || !dir.isDirectory()) {
            FileUtils.forceMkdir(dir);
        }
        return dir;
    }

    private Map<String, Object> prepareContext(GenerateReq req, File outputDir) throws FileNotFoundException {
        MavenModule commonModule = req.isUseCommon() ? MavenModule.builder()
                .name(req.getCommonArtifactId())
                .artifactId(req.getCommonArtifactId())
                .packageName(req.getCommonPackageName())
                .useMybatis(req.isCommonUseMybatis())
                .useDubbo(req.isCommonUseDubbo())
                .useRestTemplate(req.isCommonUseRestTemplate())
                .databaseIp(req.getCommonDatabaseIp())
                .databasePort(req.getCommonDatabasePort())
                .databaseName(req.getCommonDatabaseName())
                .databaseUsername(req.getCommonDatabaseUsername())
                .databasePassword(req.getCommonDatabasePassword())
                .build() : null;

        if (commonModule != null && req.isCommonUseMybatis()) {
            commonModule.setTableMapping(queryTableMapping(commonModule));
        }

        List<MavenModule> exeModules = new LinkedList<>();
        if (req.isUseExe1()) {
            MavenModule exeModule1 = MavenModule.builder()
                    .name(req.getExeArtifactId1())
                    .artifactId(req.getExeArtifactId1())
                    .packageName(req.getExePackageName1())
                    .useMybatis(req.isExeUseMybatis1())
                    .useDubbo(req.isExeUseDubbo1())
                    .useRestTemplate(req.isExeUseRestTemplate1() || req.isExeUseOa1())
                    .databaseIp(req.getExeDatabaseIp1())
                    .databasePort(req.getExeDatabasePort1())
                    .databaseName(req.getExeDatabaseName1())
                    .databaseUsername(req.getExeDatabaseUsername1())
                    .databasePassword(req.getExeDatabasePassword1())
                    .useOa(req.isExeUseOa1())
                    .build();
            if (req.isExeUseMybatis1()) {
                exeModule1.setTableMapping(queryTableMapping(exeModule1));
            }
            exeModules.add(exeModule1);
        }
        if (req.isUseExe2()) {
            MavenModule exeModule2 = MavenModule.builder()
                    .name(req.getExeArtifactId2())
                    .artifactId(req.getExeArtifactId2())
                    .packageName(req.getExePackageName2())
                    .useMybatis(req.isExeUseMybatis2())
                    .useDubbo(req.isExeUseDubbo2())
                    .useRestTemplate(req.isExeUseRestTemplate2() || req.isExeUseOa2())
                    .databaseIp(req.getExeDatabaseIp2())
                    .databasePort(req.getExeDatabasePort2())
                    .databaseName(req.getExeDatabaseName2())
                    .databaseUsername(req.getExeDatabaseUsername2())
                    .databasePassword(req.getExeDatabasePassword2())
                    .useOa(req.isExeUseOa2())
                    .build();
            if (req.isExeUseMybatis2()) {
                exeModule2.setTableMapping(queryTableMapping(exeModule2));
            }
            exeModules.add(exeModule2);
        }
        if (req.isUseExe3()) {
            MavenModule exeModule3 = MavenModule.builder()
                    .name(req.getExeArtifactId3())
                    .artifactId(req.getExeArtifactId3())
                    .packageName(req.getExePackageName3())
                    .useMybatis(req.isExeUseMybatis3())
                    .useDubbo(req.isExeUseDubbo3())
                    .useRestTemplate(req.isExeUseRestTemplate3() || req.isExeUseOa3())
                    .databaseIp(req.getExeDatabaseIp3())
                    .databasePort(req.getExeDatabasePort3())
                    .databaseName(req.getExeDatabaseName3())
                    .databaseUsername(req.getExeDatabaseUsername3())
                    .databasePassword(req.getExeDatabasePassword3())
                    .useOa(req.isExeUseOa3())
                    .build();
            if (req.isExeUseMybatis3()) {
                exeModule3.setTableMapping(queryTableMapping(exeModule3));
            }
            exeModules.add(exeModule3);
        }

        String sourcePath = SystemUtils.IS_OS_LINUX ?
                "/opt/wsd-java-service/bin/source" : ResourceUtils.getFile("classpath:source").getAbsolutePath();
        Map<String, Object> map = new HashMap<>();
        map.put("projectName", req.getProjectName());
        map.put("description", "暂无");
        map.put("packageNameTag", "@packageName@");
        map.put("artifactIdTag", "@artifactId@");
        map.put("useMybatisTag", "@useMybatis@");
        map.put("useRestTemplateTag", "@useRestTemplate@");
        map.put("useDubboTag", "@useDubbo@");
        map.put("databaseIpTag", "@databaseIp@");
        map.put("databasePortTag", "@databasePort@");
        map.put("databaseNameTag", "@databaseName@");
        map.put("tableMappingTag", "@tableMapping@");
        map.put("databaseUsernameTag", "@databaseUsername@");
        map.put("databasePasswordTag", "@databasePassword@");
        map.put("useOaTag", "@useOa@");
        map.put("groupId", req.getGroupId());
        map.put("artifactId", req.getArtifactId());
        map.put("version", req.getVersion());
        map.put("inputBaseDir", sourcePath);
        map.put("outputBaseDir", outputDir.getAbsolutePath());
        map.put("commonModule", commonModule);
        map.put("exeModules", exeModules);
        return map;
    }

    private String queryTableMapping(MavenModule module) {
        JdbcTemplate jdbcTemplate = createJdbcTemplate(module);
        String sql = "SHOW TABLES";
        List<String> tables = jdbcTemplate.queryForList(sql, String.class);
        Map<String, String> map = tables.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(t -> t, CodeGenerateServiceImpl::convertToClassName, (t1, t2) -> t1));
        String json = JsonUtils.toJson(map);
        return json;
//        return StringUtils.replace(json, "\"", "\\\"");
    }

    private static String convertToClassName(String t) {
        return StringUtils.capitalize(Underline2Camel.underline2Camel(t, false));
    }

    private JdbcTemplate createJdbcTemplate(MavenModule module) {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        String jdbcUrl = "jdbc:mysql://" + module.getDatabaseIp() + ":" + module.getDatabasePort() + "/" + module
                .getDatabaseName();
        dataSource.setDriverClass(Driver.class);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(module.getDatabaseUsername());
        dataSource.setPassword(module.getDatabasePassword());
        return new JdbcTemplate(dataSource);
    }

    private void resolveDynamicDir(File outputDir, Map<String, Object> context) throws IOException {
        File buildFile = new File(outputDir, "build.xml");
        parser.parseFile("build.xml.ftl", buildFile, context);

        if (log.isDebugEnabled()) {
            log.debug("build xml: \n{}", FileUtils.readFileToString(buildFile, Charsets.UTF_8));
        }

        org.apache.tools.ant.DirectoryScanner.removeDefaultExclude("**/.gitignore");
        Project project = new Project();
        ProjectHelper.configureProject(project, buildFile);
        DefaultLogger consoleLogger = new DefaultLogger();
        consoleLogger.setErrorPrintStream(System.err);
        consoleLogger.setOutputPrintStream(System.out);
        consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
        project.addBuildListener(consoleLogger);
        project.init();
        project.executeTarget(project.getDefaultTarget());
    }

    private File buildZip(File inputDir) {
        File outputFile = new File(ProjectConstants.WORK_BASE_DIR, IdGenerator.uuid() + ".zip");
        ZipUtils.zip(inputDir, outputFile);
        return outputFile;
    }


}
