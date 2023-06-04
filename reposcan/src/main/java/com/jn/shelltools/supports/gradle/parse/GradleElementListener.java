package com.jn.shelltools.supports.gradle.parse;

import com.jn.shelltools.supports.gradle.generated.GradleBaseListener;
import com.jn.shelltools.supports.gradle.generated.GradleParser;
import com.jn.shelltools.supports.maven.model.DependencyModel;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

class GradleElementListener extends GradleBaseListener {
    private List<DependencyModel> dependencies = Lists.newArrayList();

    private DependencyModel currentLibrary = null;

    List<DependencyModel> getDependencies() {
        return null;
    }

    @Override
    public void enterProgram(GradleParser.ProgramContext ctx) {
        super.enterProgram(ctx);
    }

    @Override
    public void exitProgram(GradleParser.ProgramContext ctx) {
        super.exitProgram(ctx);
    }

    @Override
    public void enterFuncInvoke(GradleParser.FuncInvokeContext ctx) {
        super.enterFuncInvoke(ctx);
    }
}
