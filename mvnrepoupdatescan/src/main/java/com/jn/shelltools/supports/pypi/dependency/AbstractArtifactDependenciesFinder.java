package com.jn.shelltools.supports.pypi.dependency;

import com.jn.agileway.vfs.utils.FileObjects;
import com.jn.agileway.vfs.artifact.ArtifactManager;
import com.jn.langx.util.Strings;
import com.jn.langx.util.SystemPropertys;
import com.jn.langx.util.io.file.Files;
import com.jn.shelltools.supports.pypi.PypiArtifact;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.Selectors;
import org.slf4j.LoggerFactory;

import java.io.File;
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
                    List<String> dependencies= parseDependencies(pypiArtifact, tmpExpandDir);
                    Files.deleteDirectory(new File(tmpExpandDir));
                    return dependencies;
                }
            }catch (Throwable ex){
                LoggerFactory.getLogger(getClass()).error(ex.getMessage(),ex);
            }
        }
        return null;
    }

    protected boolean isArchive(PypiArtifact pypiArtifact){
        return true;
    }

    protected String expandArtifact(PypiArtifact pypiArtifact) throws Throwable {
        FileObject fileObject = null;
        fileObject = getArtifactManager().getArtifactFile(pypiArtifact);
        if (fileObject.exists()) {

            // 将 依赖包copy到 tmp 目录下
            String tmpdir = SystemPropertys.getJavaIOTmpDir();
            String filename = FileObjects.getFileName(fileObject);
            String destFilepath = tmpdir + "/" + filename;
            FileObject destFile = getArtifactManager().getFileSystemManager().resolveFile(destFilepath);
            destFile.copyFrom(fileObject, Selectors.SELECT_SELF);

            // 解压依赖，返回解压后的目录
            String tmpArtifactDir = expandArtifact(pypiArtifact, destFile);
            // 删除临时文件
            destFile.delete(Selectors.SELECT_SELF);
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
    protected abstract String expandArtifact(PypiArtifact pypiArtifact, FileObject tmpFileObject);


    protected abstract List<String> parseDependencies(PypiArtifact pypiArtifact, String tmpExpandDir);
}
