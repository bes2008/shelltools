package com.jn.shelltools.supports.gradle.parse;

import com.jn.shelltools.supports.gradle.generated.GradleBaseListener;
import com.jn.shelltools.supports.maven.model.DependencyModel;

import java.util.List;

class GradleElementListener extends GradleBaseListener {

    List<DependencyModel> getDependencies(){
        return null;
    }
}
