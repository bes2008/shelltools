package com.jn.shelltools.supports.pypi;

import com.jn.shelltools.supports.pypi.packagemetadata.PipPackageMetadata;
import feign.Param;
import feign.RequestLine;

public interface PypiRestApi {
    @RequestLine("GET /pypi/{packageName}/json")
    PipPackageMetadata packageMetadata(@Param("packageName") String packageName);
}
