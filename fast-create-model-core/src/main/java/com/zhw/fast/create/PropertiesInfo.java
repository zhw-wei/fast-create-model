package com.zhw.fast.create;

import lombok.Data;

import java.util.List;

/**
 * 解析properties文件后的结果
 * @author: zhw
 * @create: 2022-07-31 22:49
 **/
@Data
public class PropertiesInfo {
    private DatabaseInfo databaseInfo;
    private CommonInfo commonInfo;
    private List<ModelInfo> modelInfoList;

    /**
     * 数据库连接信息
     */
    @Data
    public static class DatabaseInfo{
        private String driver;
        private String username;
        private String password;
        private String url;
    }

    @Data
    public static class CommonInfo{
        private Boolean service;
        private Boolean innerService;
        private String mapperDir;
        private Boolean extendMapper;
    }

    @Data
    public static class ModelInfo{
        private String modelName;
        private Boolean doCreate;
        private List<String> tables;

    }
}
