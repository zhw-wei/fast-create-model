package com.zhw.fast.create;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 读取和解析properties
 *
 * @author: zhw
 * @create: 2022-07-31 22:46
 **/
public class PropertiesUtil {

    private static final PropertiesUtil instance = new PropertiesUtil();

    public static PropertiesUtil getInstances() {
        return instance;
    }

    private PropertiesUtil() {
    }

    public PropertiesInfo getPropertiesInfo(String filePath) throws IOException {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);

        Properties properties = new Properties();
        properties.load(inputStream);

        // database
        PropertiesInfo.DatabaseInfo databaseInfo = new PropertiesInfo.DatabaseInfo();
        databaseInfo.setDriver(properties.getProperty("fast.create.database.driver"));
        databaseInfo.setUsername(properties.getProperty("fast.create.database.username"));
        databaseInfo.setPassword(properties.getProperty("fast.create.database.password"));
        databaseInfo.setUrl(properties.getProperty("fast.create.database.url"));

        // common
        PropertiesInfo.CommonInfo commonInfo = new PropertiesInfo.CommonInfo();
        commonInfo.setService(Boolean.valueOf(properties.getProperty("fast.create.common.service")));
        commonInfo.setInnerService(Boolean.valueOf(properties.getProperty("fast.create.common.innerService")));
        commonInfo.setMapperDir(properties.getProperty("fast.create.common.mapperDir"));
        commonInfo.setExtendMapper(Boolean.valueOf(properties.getProperty("fast.create.common.extendMapper")));

        // models
        // fast.create.model.modelName
        Set<String> modelNameSet = new HashSet<>();
        final String prefix = "fast.create.model";
        for (Object obj : properties.keySet()) {
            String key = String.valueOf(obj);
            if (!key.contains(prefix)) continue;

            String modelName = key.split("\\.")[3];
            modelNameSet.add(modelName);
        }

        List<PropertiesInfo.ModelInfo> modelInfoList = new ArrayList<>();

        String doCreate = "fast.create.model.%s.doCreate";
        String tables = "fast.create.model.%s.tables";
        for (String modelName : modelNameSet) {
            PropertiesInfo.ModelInfo modelInfo = new PropertiesInfo.ModelInfo();
            modelInfo.setModelName(modelName);
            modelInfo.setDoCreate(Boolean.valueOf(properties.getProperty(String.format(doCreate, modelName))));
            modelInfo.setTables(
                    Stream.of(properties.getProperty(String.format(tables, modelName)).split(","))
                            .map(t -> t.trim()).collect(Collectors.toList())
            );
            modelInfoList.add(modelInfo);
        }

        PropertiesInfo propertiesInfo = new PropertiesInfo();
        propertiesInfo.setDatabaseInfo(databaseInfo);
        propertiesInfo.setCommonInfo(commonInfo);
        propertiesInfo.setModelInfoList(modelInfoList);

        return propertiesInfo;
    }

}
