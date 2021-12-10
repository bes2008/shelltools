package com.jn.shelltools.supports.pypi.dependency;

import com.jn.agileway.vfs.utils.FileObjects;
import com.jn.agileway.vfs.artifact.ArtifactManager;
import com.jn.agileway.zip.archive.AutowiredArchiveSuiteFactory;
import com.jn.agileway.zip.archive.Expander;
import com.jn.langx.io.resource.FileResource;
import com.jn.langx.io.resource.Resources;
import com.jn.langx.util.Strings;
import com.jn.langx.util.SystemPropertys;
import com.jn.langx.util.io.IOs;
import com.jn.langx.util.io.file.Files;
import com.jn.langx.util.logging.Loggers;
import com.jn.shelltools.supports.pypi.PypiArtifact;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.Selectors;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public abstract class AbstractArtifactDependenciesFinder implements ArtifactDependenciesFinder {
    private ArtifactManager artifactManager;

    @Override
    public void setArtifactManager(ArtifactManager artifactManager) {
        this.artifactManager = artifactManager;
    }

    @Override
    public ArtifactManager getArtifactManager() {
        return this.artifactManager;
    }

    @Override
    public List<String> get(PypiArtifact pypiArtifact) {
        if (isArchive(pypiArtifact)) {
            try {
                String tmpExpandDir = expandArtifact(pypiArtifact);
                if (Strings.isNotEmpty(tmpExpandDir)) {
                    // 查找 setup.cfg, setup.py, metadata 等文件，然后解析
                    List<String> dependencies = parseDependencies(pypiArtifact, tmpExpandDir);
                    Files.deleteDirectory(new File(tmpExpandDir));
                    return dependencies;
                }
            } catch (Throwable ex) {
                LoggerFactory.getLogger(getClass()).error(ex.getMessage(), ex);
            }
        }
        return null;
    }

    protected boolean isArchive(PypiArtifact pypiArtifact) {
        return true;
    }

    protected String expandArtifact(PypiArtifact pypiArtifact) throws Throwable {
        FileObject fileObject = null;
        fileObject = getArtifactManager().getArtifactFile(pypiArtifact);
        if (fileObject == null) {
            return null;
        }
        if (fileObject.exists()) {

            // 将 依赖包copy到 tmp 目录下
            String tmpdir = SystemPropertys.getJavaIOTmpDir();
            String filename = FileObjects.getFileName(fileObject);
            String destFilepath = tmpdir + "/" + filename;
            FileObject destFile = getArtifactManager().getFileSystemManager().resolveFile(destFilepath);
            destFile.copyFrom(fileObject, Selectors.SELECT_SELF);
            String tmpArtifactDir = null;
            if (destFile.getContent().getSize() > 0) {
                // 解压依赖，返回解压后的目录
                tmpArtifactDir = expandArtifact(pypiArtifact, destFile);
            }
            // 删除临时文件
            FileObjects.delete(destFile);
            return tmpArtifactDir;
        }
        return null;
    }

    /**
     * 解压文件
     *
     * @param pypiArtifact
     * @param tmpFileObject
     * @return 返回解压后的目录
     */
    protected String expandArtifact(PypiArtifact pypiArtifact, FileObject tmpFileObject) {
        Expander expander = null;
        File localTempFile = null;
        InputStream inputStream = null;
        try {
            localTempFile = Files.toFile(new URL(tmpFileObject.getName().getURI()));
            FileResource resource = Resources.loadFileResource(localTempFile);
            Loggers.getLogger(getClass()).info("expd: {}", localTempFile.getName());
            inputStream = resource.getInputStream();
            expander = AutowiredArchiveSuiteFactory.getInstance().get(getExpanderFormat(pypiArtifact), inputStream);
            expander.setFilepath(localTempFile.getAbsolutePath());
            expander.setOverwriteExistsFiles(true);

            String dirname = localTempFile.getName().replace('.', '_');
            File tmpExpandDir = new File(localTempFile.getParentFile(), dirname);
            expander.expandTo(tmpExpandDir);

            return tmpExpandDir.getAbsolutePath();
        } catch (Throwable ex) {
            Loggers.getLogger(getClass()).error("expand file {} fail, {}", localTempFile, ex.getMessage());
        } finally {
            IOs.close(inputStream);
            IOs.close(expander);
        }
        return null;
    }

    protected String getExpanderFormat(PypiArtifact artifact) {
        return artifact.getExtension();
    }


    protected abstract List<String> parseDependencies(PypiArtifact pypiArtifact, String tmpExpandDir);
}
