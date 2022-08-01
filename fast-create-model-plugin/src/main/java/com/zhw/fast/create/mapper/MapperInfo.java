package com.zhw.fast.create.mapper;

import java.util.List;

/**
 * @author: zhw
 * @create: 2022-08-01 23:21
 **/
public class MapperInfo {

    private String packageInfo;

    private List<String> importList;

    private String className;

    private List<String> methodList;
}

/*
package xxx

import aa;
import bb

public interface className{
    int update(Aa aa);

    int insert(Aa aa);
}
 */