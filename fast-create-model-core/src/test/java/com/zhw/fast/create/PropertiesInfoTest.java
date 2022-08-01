package com.zhw.fast.create;

import java.util.Arrays;
import java.util.List;

import com.zhw.fast.create.PropertiesInfo.CommonInfo;
import com.zhw.fast.create.PropertiesInfo.ModelInfo;
import com.zhw.fast.create.PropertiesInfo.DatabaseInfo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: zhw
 * @create: 2022-08-01 22:11
 **/
@DisplayName("测试读取properties文件")
public class PropertiesInfoTest {

    @Test
    @DisplayName("验证文件读取是否正常")
    public void getPropertiesInfo() throws IOException {
        PropertiesInfo info = PropertiesUtil.getInstances().getPropertiesInfo("fast-create-model.properties");

        DatabaseInfo databaseInfo = info.getDatabaseInfo();
        Assertions.assertNotNull(databaseInfo);
        Assertions.assertEquals(databaseInfo.getDriver(), "com.mysql.cj.jdbc.Driver");
        Assertions.assertEquals(databaseInfo.getUsername(), "zhw");
        Assertions.assertEquals(databaseInfo.getPassword(), "hello-world");
        Assertions.assertEquals(databaseInfo.getUrl(), "mysql://localhost:3306/hello");

        CommonInfo commonInfo = info.getCommonInfo();
        Assertions.assertNotNull(commonInfo);
        Assertions.assertEquals(commonInfo.getService(), Boolean.TRUE);
        Assertions.assertEquals(commonInfo.getInnerService(), Boolean.TRUE);
        Assertions.assertEquals(commonInfo.getMapperDir(), "dao");
        Assertions.assertEquals(commonInfo.getExtendMapper(), Boolean.TRUE);
        Assertions.assertEquals(commonInfo.getBasePath(), "com.zhw.fast.create");
        Assertions.assertEquals(commonInfo.getLombok(), Boolean.TRUE);

        List<ModelInfo> modelInfoList = info.getModelInfoList();
        Assertions.assertNotNull(modelInfoList);

        Map<String, ModelInfo> map = modelInfoList.stream()
                .collect(Collectors.toMap(v -> v.getModelName(), Function.identity()));
        ModelInfo hello = map.get("hello");
        Assertions.assertEquals(hello.getDoCreate(), Boolean.FALSE);
        Assertions.assertEquals(hello.getTables(), Arrays.asList("aaa", "bbb", "ccc"));

        ModelInfo hello1 = map.get("hello1");
        Assertions.assertEquals(hello1.getDoCreate(), Boolean.TRUE);
        Assertions.assertEquals(hello1.getTables(), Arrays.asList("aaa1", "bbb1", "ccc1"));

        ModelInfo hello2 = map.get("hello2");
        Assertions.assertEquals(hello2.getDoCreate(), Boolean.FALSE);
        Assertions.assertEquals(hello2.getTables(), Arrays.asList("aaa2", "bbb2", "ccc2"));
    }

}
