package com.jn.shelltools.core.pypi.dependency;

import com.jn.langx.factory.Factory;

import java.util.List;

public interface DependenciesFinder<I> extends Factory<I, List<String>> {
    @Override
    List<String> get(I input);
}
