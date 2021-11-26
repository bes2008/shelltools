package com.jn.shelltools.supports.pypi.dependency;


import com.jn.langx.Factory;

import java.util.List;

/**
 * python 库的依赖查找
 * @param <I>
 */
public interface DependenciesFinder<I> extends Factory<I, List<String>> {
    @Override
    List<String> get(I input);
}
