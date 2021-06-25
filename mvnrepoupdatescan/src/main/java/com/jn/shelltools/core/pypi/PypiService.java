package com.jn.shelltools.core.pypi;

import com.jn.shelltools.core.pypi.model.packagemetadata.PipPackageMetadata;
import feign.Param;
import feign.RequestLine;

public interface PypiService {
    @RequestLine("GET /pypi/{packageName}/json")
    PipPackageMetadata packageSummary(@Param("packageName") String packageName);
}
