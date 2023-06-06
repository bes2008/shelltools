package com.jn.shelltools.supports.maven;

import com.jn.langx.Factory;
import com.jn.shelltools.supports.maven.model.MavenPackageArtifact;

public interface PomGenerator extends Factory<MavenPackageArtifact, String> {
    @Override
    String get(MavenPackageArtifact pomModel);
}
