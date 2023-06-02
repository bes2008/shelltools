package com.jn.shelltools.supports.maven;

import com.jn.langx.util.bean.Beans;
import com.jn.shelltools.supports.maven.model.PomModel;

import java.util.LinkedHashMap;
import java.util.Map;

public class PomXmlGenerator implements PomGenerator {

    @Override
    public String get(PomModel pomModel) {
        PomModel formatted = format(pomModel);
        return null;
    }

    private PomModel format(PomModel model) {
        PomModel ret = new PomModel();
        Beans.copyProperties(model, ret);
        // 处理依赖版本
        Map<String, String> properties = new LinkedHashMap<String, String>();
        ret.setProperties(properties);
        return ret;
    }
}
