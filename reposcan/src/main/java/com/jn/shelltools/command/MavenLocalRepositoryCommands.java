package com.jn.shelltools.command;

import com.jn.langx.Filter;
import com.jn.langx.util.Dates;
import com.jn.langx.util.Preconditions;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Consumer2;
import com.jn.langx.util.io.file.Files;
import com.jn.shelltools.supports.maven.MavenLocalRepositoryUpdatedScanner;
import com.jn.shelltools.supports.maven.model.MavenGAV;
import com.jn.shelltools.supports.maven.model.MavenPackageArtifact;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;
import java.util.Date;
import java.util.Map;

@ShellComponent
public class MavenLocalRepositoryCommands {
    private static final Logger logger = LoggerFactory.getLogger(MavenLocalRepositoryCommands.class);

    /**
     * <pre>
     *     maven_local_repo_updated_scan --repository-location D:/mvn_repo_download_test --date 2020-07-16_00:00
     * </pre>
     * @param repositoryLocation
     * @param date
     */
    @ShellMethod(key = "maven_local_repo_updated_scan", value = "scan all dependencies after the specified time")
    public void scanUpdated(@ShellOption(defaultValue = ".") String repositoryLocation,
                            @ShellOption(value = "--date", defaultValue = ".", help = "format: yyyy-MM-dd_HH:mm, the default is the day of you execute it") String date) {
        Map<MavenGAV, MavenPackageArtifact> map = scanUpdated0(repositoryLocation, date);
        Collects.forEach(map, new Consumer2<MavenGAV, MavenPackageArtifact>() {
            @Override
            public void accept(MavenGAV key, MavenPackageArtifact value) {
                System.out.println(key);
            }
        });
    }

    private Map<MavenGAV, MavenPackageArtifact> scanUpdated0(String repositoryLocation, String date){
        Date d = null;
        if (ShellOption.NONE.equals(date) || ShellOption.NULL.equals(date) || ".".equals(date)) {
            date = null;
        }
        if (Strings.isBlank(date)) {
            d = new Date();
            d = Dates.setHours(d, 0);
            d = Dates.setMinutes(d, 0);
            d = Dates.setSeconds(d, 0);
            d = Dates.setMilliseconds(d, 0);
        } else {
            d = Dates.parse(date, "yyyy-MM-dd_HH:mm");
        }
        final long baselineTime = d.getTime();
        MavenLocalRepositoryUpdatedScanner scanner = new MavenLocalRepositoryUpdatedScanner();
        Map<MavenGAV, MavenPackageArtifact> map = scanner.scan(new File(repositoryLocation), new Filter<MavenPackageArtifact>() {
            @Override
            public boolean accept(MavenPackageArtifact mavenArtifact) {
                return mavenArtifact.getLastModifiedTime() >= baselineTime;
            }
        });
        return map;
    }

    @ShellMethod(key = "maven_local_repo_copyUpdated", value = "scan all dependencies after the specified time")
    public void copyUpdated(@ShellOption(defaultValue = ".") String repositoryLocation,
                            @ShellOption(value = "--date", defaultValue = ".", help = "format: yyyy-MM-dd_HH:mm, the default is the day of you execute it") String date,
                            @ShellOption(defaultValue = ".") String destLocation
                            ){
        File destRootDirectory = new File(destLocation);
        if(!destRootDirectory.exists()){
            Files.makeDirs(destRootDirectory);
        }
        Preconditions.checkTrue(destRootDirectory.exists());
        Preconditions.checkTrue(destRootDirectory.isDirectory());
        Map<MavenGAV, MavenPackageArtifact> map = scanUpdated0(repositoryLocation, date);
        Collects.forEach(map, new Consumer2<MavenGAV, MavenPackageArtifact>() {
            @Override
            public void accept(MavenGAV gav, MavenPackageArtifact mavenArtifact) {
                System.out.println(gav);
                File srcDirectory = new File(mavenArtifact.getLocalPath());
                File destDirectory = new File(destRootDirectory, gav.getLocation());
                try {
                    Files.copyDirectory(srcDirectory, destDirectory);
                }catch (Throwable ex){
                    logger.error(ex.getMessage(),ex);
                }
            }
        });
    }
}
