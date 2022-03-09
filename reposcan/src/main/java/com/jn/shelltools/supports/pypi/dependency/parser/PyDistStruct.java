package com.jn.shelltools.supports.pypi.dependency.parser;

import com.jn.langx.annotation.NotEmpty;

/**
 * pydist.json
 * sdist, bdist中都可以有这个文件
 */
public class PyDistStruct {

    @NotEmpty
    private String metadata_version;

    @NotEmpty
    private String generator;

    // ^([A-Z0-9]|[A-Z0-9][A-Z0-9._-]*[A-Z0-9])$
    @NotEmpty
    private String name;
    @NotEmpty
    private String version;
    @NotEmpty
    private String summary;

    /**
     * fields in source code (sdist)
     */
    // ^([A-Z0-9]|[A-Z0-9][A-Z0-9._-+]*[A-Z0-9])$
    private String source_label;

    private String source_url;
}
