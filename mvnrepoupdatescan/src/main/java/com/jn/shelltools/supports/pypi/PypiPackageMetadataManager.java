package com.jn.shelltools.supports.pypi;

import com.jn.easyjson.core.JSONBuilderProvider;
import com.jn.langx.cache.Cache;
import com.jn.langx.cache.CacheBuilder;
import com.jn.langx.util.Strings;
import com.jn.langx.util.collection.ConcurrentHashSet;
import com.jn.langx.util.io.Charsets;
import com.jn.langx.util.io.IOs;
import com.jn.langx.util.logging.Loggers;
import com.jn.langx.util.struct.Holder;
import com.jn.langx.util.timing.timer.WheelTimers;
import com.jn.shelltools.supports.pypi.dependency.RequirementsManager;
import com.jn.shelltools.supports.pypi.packagemetadata.PipPackageMetadata;
import com.jn.shelltools.supports.pypi.repository.PipPackageMetadataArtifact;
import org.apache.commons.vfs2.FileObject;
import org.slf4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public class PypiPackageMetadataManager extends RequirementsManager {
    private static final Logger logger = Loggers.getLogger(PypiPackageMetadataManager.class);
    private PypiRestApi restApi;


    public void setRestApi(PypiRestApi restApi) {
        this.restApi = restApi;
    }

    private Cache<String, Holder<PipPackageMetadata>> cache;

    public PypiPackageMetadataManager() {
        cache = CacheBuilder.<String, Holder<PipPackageMetadata>>newBuilder()
                .expireAfterRead(10 * 60)
                .evictExpiredInterval(20 * 1000)
                .timer(WheelTimers.newHashedWheelTimer())
                .build();
    }

    /**
     * 正在获取的package的集合
     */
    private ConcurrentHashSet packaging = new ConcurrentHashSet<>();

    public PipPackageMetadata getOfficialMetadata(String packageName) {
        if (Strings.isBlank(packageName)) {
            return null;
        }
        PipPackageMetadata metadata = null;
        Holder<PipPackageMetadata> metadataHolder = cache.get(packageName);
        if (metadataHolder != null) {
            metadata = metadataHolder.get();
            return metadata;
        }
        try {
            if (packaging.contains(packageName)) {
                synchronized (this) {
                    while (packaging.contains(packageName)) {
                        this.wait(10);
                    }
                }
            }
        } catch (Throwable ex) {
            // ignore it
        }
        packaging.add(packageName);
        // 先从本地仓库获取
        PipPackageMetadataArtifact artifact = new PipPackageMetadataArtifact(packageName);


        FileObject fileObject = null;
        try {

            fileObject = artifactManager.getArtifactFile(artifact);
            if (fileObject.exists()) {
                synchronized (this) {
                    // 查看 last modified 时间，若在10分钟之内， 则认为是有效的
                    long lastModified = fileObject.getContent().getLastModifiedTime();
                    if (lastModified + TimeUnit.HOURS.toMillis(48) >= System.currentTimeMillis()) {
                        // 有效
                        InputStream inputStream = fileObject.getContent().getInputStream();
                        InputStreamReader reader = new InputStreamReader(inputStream);
                        metadata = JSONBuilderProvider.simplest().fromJson(reader, PipPackageMetadata.class);
                        IOs.close(reader);
                    }
                }
            }
            if (metadata == null) {

                metadata = this.restApi.packageMetadata(packageName);
                if (metadata != null) {

                    // 写到本地仓库
                    String str = JSONBuilderProvider.create().prettyFormat(true).build().toJson(metadata);
                    if (!fileObject.exists()) {
                        fileObject.getParent().createFolder();
                        fileObject.createFile();
                    }
                    OutputStream out = fileObject.getContent().getOutputStream(false);
                    out.write(str.getBytes(Charsets.UTF_8));
                    out.flush();
                    IOs.close(out);
                }
            }

        } catch (Throwable ex) {
            logger.error("error get metadata for {} from official, {},", packageName, ex.getMessage(), ex);
        } finally {
            packaging.remove(packageName);
            synchronized (this) {
                this.notify();
            }
        }
        cache.set(packageName, new Holder<PipPackageMetadata>(metadata));
        return metadata;
    }
}
