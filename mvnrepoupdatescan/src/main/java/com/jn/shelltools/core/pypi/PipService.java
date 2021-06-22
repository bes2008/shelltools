package com.jn.shelltools.core.pypi;

import com.jn.shelltools.core.pypi.model.summary.PipPackageSummary;
import feign.Param;
import feign.RequestLine;

public interface PipService {
    @RequestLine("GET /pypi/{packageName}/json")
    PipPackageSummary packageSummary(@Param("packageName") String packageName);
}
