package com.jn.shelltools.command;

import com.jn.easyjson.core.JSON;
import com.jn.easyjson.core.JSONBuilderProvider;
import com.jn.langx.io.resource.Resource;
import com.jn.langx.io.resource.Resources;
import com.jn.langx.util.Strings;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.collection.DistinctLinkedBlockingQueue;
import com.jn.langx.util.function.Consumer;
import com.jn.langx.util.function.Functions;
import com.jn.langx.util.function.Predicate;
import com.jn.shelltools.supports.pypi.PypiArtifact;
import com.jn.shelltools.supports.pypi.PypiPackageManager;
import com.jn.shelltools.supports.pypi.PypiPackageMetadataManager;
import com.jn.shelltools.supports.pypi.dependency.RequirementsManager;
import com.jn.shelltools.supports.pypi.filter.SourceArtifactPredicate;
import com.jn.shelltools.supports.pypi.filter.TagsExclusionPredicate;
import com.jn.shelltools.supports.pypi.packagemetadata.PipPackageMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@ShellComponent
@ShellCommandGroup("pip")
public class PypiCommands {
    private static final Logger logger = LoggerFactory.getLogger(PypiCommands.class);
    @Autowired
    private PypiPackageManager pypiPackageManager;
    private PypiPackageMetadataManager pypiPackageMetadataManager;

    @ShellMethod(key = "pip show", value = "show the metadata for a python package")
    public void showMetadata(
            @ShellOption(value = "--package") String packageName) {

        PipPackageMetadata metadata = pypiPackageMetadataManager.getOfficialMetadata(packageName);
        if (metadata != null) {
            JSON json = JSONBuilderProvider.create().prettyFormat(true).serializeNulls(true).build();
            System.out.println(json.toJson(metadata));
        } else {
            System.out.printf("package not exist");
        }
    }

    @ShellMethod(key = "pip download", value = "download a package (and its dependencies) to the out directory")
    public void downloadPackage(
            @ShellOption(value = "--package", help = "the package name or the requirements file path") String packageName,
            @ShellOption(value = "--with-deps", defaultValue = "true", help = "with its dependencies ?") boolean withDeps,
            @ShellOption(value = "--source", defaultValue = "true", help = "whether download the source package or not") boolean downloadSource,
            @ShellOption(value = "--exclusion-tags", defaultValue = "armv7,armv71,armv7s,s390,s390x,ia64,win32,macosx,i686,cp26,cp27,cp30,cp31,cp32,cp33,cp34,cp35,cp36,cp37,cp38,cp39,cp26m,cp27m,cp30m,cp31m,cp32m,cp33m,cp34m,cp35m,cp36m,cp37m,cp38m,cp39m,py24,py2.4,py25,py2.5", help = "exclusion packages, use comma") String exclusionTags) {

        List<Predicate<PypiArtifact>> predicates = new ArrayList<>();

        if (downloadSource) {
            predicates.add(new SourceArtifactPredicate());
        }
        if (Strings.isNotBlank(exclusionTags)) {
            TagsExclusionPredicate predicate = new TagsExclusionPredicate();
            predicate.setTags(Collects.asList(Strings.split(exclusionTags, ",")));
            predicates.add(predicate);
        }

        Predicate<PypiArtifact> artifactPredicate = Functions.anyPredicate(predicates);

        File file = new File(packageName);
        if (!file.exists()) {
            pypiPackageManager.downloadPackage(packageName, withDeps, artifactPredicate);
        } else {
            Resource resource = Resources.loadFileResource(file);
            List<String> lines = RequirementsManager.readRequirements(resource);
            DistinctLinkedBlockingQueue<String> queue = new DistinctLinkedBlockingQueue<>();
            queue.addAll(lines);
            pypiPackageManager.downloadPackages(queue, withDeps, artifactPredicate);
        }
    }
}
