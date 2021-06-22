package com.jn.shelltools.core.artifact;

import com.jn.langx.factory.Factory;
import com.jn.shelltools.core.IGAV;

public interface ArtifactFactory<GAV extends IGAV, A extends Artifact> extends Factory<GAV,A> {
    @Override
    A get(GAV input);
}
