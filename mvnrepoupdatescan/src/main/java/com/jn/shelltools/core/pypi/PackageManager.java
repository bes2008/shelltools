package com.jn.shelltools.core.pypi;

import com.jn.shelltools.core.pypi.model.summary.PipPackageSummary;
import org.springframework.beans.factory.annotation.Autowired;

public class PackageManager {
    private PipService service;

    @Autowired
    public void setService(PipService service) {
        this.service = service;
    }

    public PipPackageSummary getPackageSummary(String packageName) {
        return this.service.packageSummary(packageName);
    }


}
