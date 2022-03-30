package com.jn.shelltools.supports.pypi;

import com.jn.agileway.vfs.utils.FileObjects;
import com.jn.easyjson.core.JSONBuilderProvider;
import com.jn.langx.cache.Cache;
import com.jn.langx.cache.CacheBuilder;
import com.jn.langx.util.Strings;
import com.jn.langx.util.Throwables;
import com.jn.langx.util.collection.Pipeline;
import com.jn.langx.util.concurrent.ConcurrentHashSet;
import com.jn.langx.util.function.Function;
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
import java.util.List;

public class PypiPackageMetadataManager extends RequirementsManager {
    private static final Logger logger = Loggers.getLogger(PypiPackageMetadataManager.class);
    private PypiRestApi restApi;
    private Cache<String, Holder<PipPackageMetadata>> cache;
    /**
     * 正在获取的package的集合
     */
    private ConcurrentHashSet loading = new ConcurrentHashSet<>();

    public PypiPackageMetadataManager() {
        cache = CacheBuilder.<String, Holder<PipPackageMetadata>>newBuilder()
                .expireAfterRead(10 * 60)
                .evictExpiredInterval(20 * 1000)
                .timer(WheelTimers.newHashedWheelTimer())
                .build();
    }

    public void setRestApi(PypiRestApi restApi) {
        this.restApi = restApi;
    }

    public boolean isPackageInRepository(String packageName) {
        try {
            PipPackageMetadataArtifact artifact = new PipPackageMetadataArtifact(packageName);
            FileObject fileObject = artifactManager.getArtifactFile(artifact);
            return FileObjects.isExists(fileObject);
        } catch (Throwable ex) {
            throw Throwables.wrapAsRuntimeException(ex);
        }
    }

    public PipPackageMetadata getOfficialMetadata(String packageName) {
        return getOfficialMetadata(packageName, false,  0);
    }

    public PipPackageMetadata getOfficialMetadata(String packageName, boolean storeIfAbsent, long ttl) {
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
            if (loading.contains(packageName)) {
                synchronized (this) {
                    while (loading.contains(packageName)) {
                        this.wait(10);
                    }
                }
            }
        } catch (Throwable ex) {
            // ignore it
        }
        loading.add(packageName);
        // 先从本地仓库获取
        PipPackageMetadataArtifact artifact = new PipPackageMetadataArtifact(packageName);


        FileObject fileObject = null;
        try {
            fileObject = artifactManager.getArtifactFile(artifact);
            if (FileObjects.isExists(fileObject)) {
                synchronized (this) {
                    // 查看 last modified 时间
                    long lastModified = fileObject.getContent().getLastModifiedTime();
                    boolean filterWithLastModified= ttl>=60 * 1000;
                    boolean metadataFileIsValid = !filterWithLastModified;
                    if(filterWithLastModified){
                        metadataFileIsValid = lastModified + ttl >= System.currentTimeMillis();
                    }
                    if (metadataFileIsValid) {
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
                if (metadata != null && storeIfAbsent) {
                    // 写到本地仓库
                    String str = JSONBuilderProvider.create().prettyFormat(true).build().toJson(metadata);
                    if (!fileObject.exists()) {
                        fileObject.getParent().createFolder();
                        fileObject.createFile();
                    }
                    OutputStream out = null;
                    try {
                        out = fileObject.getContent().getOutputStream(false);
                        out.write(str.getBytes(Charsets.UTF_8));
                        out.flush();
                    } finally {
                        IOs.close(out);
                    }

                }
            }

        } catch (Throwable ex) {
            logger.error("error get metadata for {} from official, {},", packageName, ex.getMessage(), ex);
        } finally {
            loading.remove(packageName);
            synchronized (this) {
                this.notify();
            }
        }
        cache.set(packageName, new Holder<PipPackageMetadata>(metadata));
        return metadata;
    }

    public List<PipPackageMetadata> findMetadatas(List<String> packageNames) {
        return Pipeline.of(packageNames).map(new Function<String, PipPackageMetadata>() {
            @Override
            public PipPackageMetadata apply(String packageName) {
                return getOfficialMetadata(packageName);
            }
        }).clearNulls().asList();
    }

}
