package cn.exrick.mk.cradle.common;

import com.douyu.wsd.cradle.AppLauncher;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;

public class ProjectConstants {

    public static final String SOURCE_DIR_NAME;

    public static final File SOURCE_DIR;

    public static final String WORK_BASE_DIR_NAME;

    public static final File WORK_BASE_DIR;

    static {
        SOURCE_DIR_NAME = "source/__projectName__";
        String inputBasePath =
            SystemUtils.IS_OS_LINUX ? "/opt/wsd-java-service/bin/source/__projectName__"
                : AppLauncher.class.getClassLoader().getResource(SOURCE_DIR_NAME).getPath();
        Preconditions.checkNotNull(inputBasePath, "inputBaseUrl can not be null");

        SOURCE_DIR = new File(inputBasePath);

        WORK_BASE_DIR_NAME = "output";
        String outputBasePath = SystemUtils.IS_OS_LINUX ? "/opt/wsd-java-service/bin/output"
            : AppLauncher.class.getClassLoader().getResource(SOURCE_DIR_NAME).getPath();
        Preconditions.checkNotNull(outputBasePath, "outputBaseUrl can not be null");

        WORK_BASE_DIR = new File(outputBasePath);
    }
}
