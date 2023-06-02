package com.jn.shelltools.supports.maven;

import com.jn.langx.Factory;
import com.jn.shelltools.supports.maven.model.PomModel;

public interface PomGenerator extends Factory<PomModel, String> {
    @Override
    String get(PomModel pomModel);
}
