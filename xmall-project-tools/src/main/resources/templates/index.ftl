<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>项目生成器</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="konglz">

    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="/bootstrap/css/bootstrap.css">

    <!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
    <link rel="stylesheet" href="/bootstrap/css/bootstrap-theme.css">

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="/jquery/jquery.min.js"></script>


    <script src="/jquery/jquery.fileDownload.js"></script>

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="/bootstrap/js/bootstrap.js"></script>
</head>

<body>
<div class="container">
    <form id="exportForm" class="form-horizontal">
        <h1>项目生成器</h1>
        <div class="panel panel-default">
            <div class="panel-heading">项目全局配置（*表示必填）</div>
            <div class="panel-body">
                <div class="control-group">

                    <!-- Text input-->
                    <label class="control-label" for="projectName">项目名称 *</label>
                    <div class="controls">
                        <input type="text" name="projectName" id="projectName" placeholder="项目名称，比如venus-admin"
                               value="demo" class="form-control">
                    </div>
                </div>

                <div class="control-group">

                    <!-- Text input-->
                    <label class="control-label" for="groupId">groupId *</label>
                    <div class="controls">
                        <input type="text" name="groupId" id="groupId" placeholder="pom.xml的groupId"
                               value="com.douyu.wsd"
                               class="form-control">
                    </div>
                </div>

                <div class="control-group">
                    <!-- Text input-->
                    <label class="control-label" for="artifactId">artifactId</label>
                    <div class="controls">
                        <input type="text" name="artifactId" id="artifactId" placeholder="pom.xml的artifactId"
                               value="demo-parent" class="form-control">
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="version">version *</label>
                    <div class="controls">
                        <input name="version" id="version" placeholder="pom.xml的version，如：1.0.0-SNAPSHOT"
                               value="1.0.0-SNAPSHOT" class="form-control">
                    </div>
                </div>

                <#--<div class="controls">-->
                    <#--<div class="checkbox">-->
                        <#--<label>-->
                            <#--<input id="useLocalModel" type="checkbox" name="useLocalModel" value="true" checked> 输出到本地-->
                        <#--</label>-->
                    <#--</div>-->
                <#--</div>-->

                <#--<div class="control-group">-->
                    <#--<label class="control-label" for="localPath">本地输出路径</label>-->
                    <#--<div class="controls">-->
                        <#--<input name="localPath" id="localPath" placeholder="输出到本地的路径"-->
                               <#--value="D:/JavaProjects" class="form-control">-->
                    <#--</div>-->
                <#--</div>-->

            </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading">项目子模块（勾选 “启用此模块” 生效）</div>
            <div class="panel-body">
                <div class="panel panel-default">
                    <div class="panel-heading">COMMON模块（可选）</div>
                    <div class="panel-body">
                        <div class="controls">
                            <div class="checkbox">
                                <label>
                                    <input id="useCommon" type="checkbox" name="useCommon" value="true"> 启用此模块
                                </label>
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="commonArtifactId">artifactId</label>
                            <div class="controls">
                                <input id="commonArtifactId" name="commonArtifactId" placeholder="pom.xml的artifactId"
                                       value="wsd-demo-common-java" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label" for="commonPackageName">packageName</label>
                            <div class="controls">
                                <input id="commonPackageName" name="commonPackageName"
                                       placeholder="java包的根目录，如：com.douyu.wsd.demo.admin"
                                       value="com.douyu.wsd.demo.common" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="commonDatabaseIp">数据库IP</label>
                            <div class="controls">
                                <input name="commonDatabaseIp" id="commonDatabaseIp" placeholder="数据库IP" value="localhost" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="commonDatabasePort">数据库端口</label>
                            <div class="controls">
                                <input name="commonDatabasePort" id="commonDatabasePort" placeholder="数据库端口" value="3306" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="commonDatabaseName">数据库名称</label>
                            <div class="controls">
                                <input name="commonDatabaseName" id="commonDatabaseName" placeholder="数据库名称" value="your_database" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="commonDatabaseUsername">数据库用户名</label>
                            <div class="controls">
                                <input name="commonDatabaseUsername" id="commonDatabaseUsername" placeholder="数据库用户名" value="your_username" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="commonDatabasePassword">数据库密码</label>
                            <div class="controls">
                                <input name="commonDatabasePassword" id="commonDatabasePassword" placeholder="数据库密码" value="your_password" class="form-control">
                            </div>
                        </div>


                        <div class="controls">
                            <label class="control-label">功能选择</label>
                            <div class="checkbox">
                                <label>
                                    <input id="commonUseMybatis" name="commonUseMybatis" type="checkbox" name="enabled"> 生成mvc代码
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input id="commonUseDubbo" name="commonUseDubbo" type="checkbox" name="commonUseDubbo"> dubbo
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input id="commonUseRestTemplate" name="commonUseRestTemplate" type="checkbox"
                                           name="commonUseRestTemplate"> rest template
                                </label>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading">可执行模块1（至少选择1个）</div>
                    <div class="panel-body">
                        <div class="controls">
                            <div class="checkbox">
                                <label>
                                    <input id="useExe1" type="checkbox" name="useExe1" checked> 启用此模块
                                </label>
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exeArtifactId1">artifactId</label>
                            <div class="controls">
                                <input name="exeArtifactId1" id="exeArtifactId1" placeholder="pom.xml的artifactId"
                                       value="wsd-demo-admin-java" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exePackageName1">packageName</label>
                            <div class="controls">
                                <input name="exePackageName1" id="exePackageName1"
                                       placeholder="java包的根目录，如：com.douyu.wsd.demo.admin"
                                       value="com.douyu.wsd.demo.admin" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exeDatabaseIp1">数据库IP</label>
                            <div class="controls">
                                <input name="exeDatabaseIp1" id="exeDatabaseIp1" placeholder="数据库IP" value="localhost" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exeDatabasePort1">数据库端口</label>
                            <div class="controls">
                                <input name="exeDatabasePort1" id="exeDatabasePort1" placeholder="数据库端口" value="3306" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exeDatabaseName1">数据库名称</label>
                            <div class="controls">
                                <input name="exeDatabaseName1" id="exeDatabaseName1" placeholder="数据库名称" value="your_database" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exeDatabaseUsername1">数据库用户名</label>
                            <div class="controls">
                                <input name="exeDatabaseUsername1" id="exeDatabaseUsername1" placeholder="数据库用户名" value="your_username" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exeDatabasePassword1">数据库密码</label>
                            <div class="controls">
                                <input name="exeDatabasePassword1" id="exeDatabasePassword1" placeholder="数据库密码" value="your_password" class="form-control">
                            </div>
                        </div>


                        <div class="controls">
                            <label class="control-label">功能选择</label>
                            <div class="checkbox">
                                <label>
                                    <input id="exeUseMybatis1" name="exeUseMybatis1" type="checkbox" name="exeUseMybatis1"> 生成mvc代码
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input id="exeUseDubbo1" name="exeUseDubbo1" type="checkbox" name="exeUseDubbo1"> dubbo
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input id="exeUseRestTemplate1" name="exeUseRestTemplate1" type="checkbox"
                                           name="exeUseRestTemplate1"> rest template
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input id="exeUseOa1" name="exeUseOa1" type="checkbox"
                                           name="exeUseOa1"> oa 和 鉴权
                                </label>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading">可执行模块2（至少选择1个）</div>
                    <div class="panel-body">
                        <div class="controls">
                            <div class="checkbox">
                                <label>
                                    <input id="useExe2" type="checkbox" name="useExe2"> 启用此模块
                                </label>
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exeArtifactId2">artifactId</label>
                            <div class="controls">
                                <input type="text" name="exeArtifactId2" id="exeArtifactId2" placeholder="pom.xml的artifactId"
                                       value="wsd-demo-front-java" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exePackageName2">packageName</label>
                            <div class="controls">
                                <input type="text" name="exePackageName2" id="exePackageName2"
                                       placeholder="java包的根目录，如：com.douyu.wsd.demo.admin"
                                       value="com.douyu.wsd.demo.front" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exeDatabaseIp2">数据库IP</label>
                            <div class="controls">
                                <input name="exeDatabaseIp2" id="exeDatabaseIp2" placeholder="数据库IP" value="localhost" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exeDatabasePort2">数据库端口</label>
                            <div class="controls">
                                <input name="exeDatabasePort2" id="exeDatabasePort2" placeholder="数据库端口" value="3306" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exeDatabaseName2">数据库名称</label>
                            <div class="controls">
                                <input name="exeDatabaseName2" id="exeDatabaseName2" placeholder="数据库名称" value="your_database" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exeDatabaseUsername2">数据库用户名</label>
                            <div class="controls">
                                <input name="exeDatabaseUsername2" id="exeDatabaseUsername2" placeholder="数据库用户名" value="your_username" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exeDatabasePassword2">数据库密码</label>
                            <div class="controls">
                                <input name="exeDatabasePassword2" id="exeDatabasePassword2" placeholder="数据库密码" value="your_password" class="form-control">
                            </div>
                        </div>

                        <div class="controls">
                            <label class="control-label">功能选择</label>
                            <div class="checkbox">
                                <label>
                                    <input id="exeUseMybatis2" name="exeUseMybatis2" type="checkbox" name="exeUseMybatis2"> 生成mvc代码
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input id="exeUseDubbo2" name="exeUseDubbo2" type="checkbox" name="exeUseDubbo2"> dubbo
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input id="exeUseRestTemplate2" name="exeUseRestTemplate2" type="checkbox"
                                           name="exeUseRestTemplate2"> rest template
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input id="exeUseOa2" name="exeUseOa2" type="checkbox"
                                           name="exeUseOa2"> oa 和 鉴权
                                </label>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading">可执行模块3（至少选择1个）</div>
                    <div class="panel-body">
                        <div class="controls">
                            <div class="checkbox">
                                <label>
                                    <input id="useExe3" type="checkbox" name="useExe3"> 启用此模块
                                </label>
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exeArtifactId3">artifactId</label>
                            <div class="controls">
                                <input type="text" name="exeArtifactId3" id="exeArtifactId3" placeholder="pom.xml的artifactId"
                                       value="wsd-demo-task-java" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exePackageName3">packageName</label>
                            <div class="controls">
                                <input name="exePackageName3" id="exePackageName3"
                                       placeholder="java包的根目录，如：com.douyu.wsd.demo.admin"
                                       value="com.douyu.wsd.demo.task" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exeDatabaseIp3">数据库IP</label>
                            <div class="controls">
                                <input name="exeDatabaseIp3" id="exeDatabaseIp3" placeholder="数据库IP" value="localhost" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exeDatabasePort3">数据库端口</label>
                            <div class="controls">
                                <input name="exeDatabasePort3" id="exeDatabasePort3" placeholder="数据库端口" value="3306" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exeDatabaseName3">数据库名称</label>
                            <div class="controls">
                                <input name="exeDatabaseName3" id="exeDatabaseName3" placeholder="数据库名称" value="your_database" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exeDatabaseUsername3">数据库用户名</label>
                            <div class="controls">
                                <input name="exeDatabaseUsername3" id="exeDatabaseUsername3" placeholder="数据库用户名" value="your_username" class="form-control">
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" for="exeDatabasePassword3">数据库密码</label>
                            <div class="controls">
                                <input name="exeDatabasePassword3" id="exeDatabasePassword3" placeholder="数据库密码" value="your_password" class="form-control">
                            </div>
                        </div>

                        <div class="controls">
                            <label class="control-label">功能选择</label>
                            <div class="checkbox">
                                <label>
                                    <input id="exeUseMybatis3" name="exeUseMybatis3" type="checkbox" name="exeUseMybatis3"> 生成mvc代码
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input id="exeUseDubbo3" name="exeUseDubbo3" type="checkbox" name="exeUseDubbo3"> dubbo
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input id="exeUseRestTemplate3" name="exeUseRestTemplate3" type="checkbox"
                                           name="exeUseRestTemplate3"> rest template
                                </label>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input id="exeUseOa3" name="exeUseOa3" type="checkbox"
                                           name="exeUseOa3"> oa 和 鉴权
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label"></label>

            <!-- Button -->
            <div class="controls">
                <button type="button" class="btn btn-lg btn-primary btn-block" id="submit-btn">生成项目</button>
            </div>
        </div>
    </form>
</div>
<script>
    $(function () {
        $("#submit-btn").click(function () {
            $.fileDownload('/api/generate?' + $("#exportForm").serialize());
        });
    });
</script>
</body>
</html>