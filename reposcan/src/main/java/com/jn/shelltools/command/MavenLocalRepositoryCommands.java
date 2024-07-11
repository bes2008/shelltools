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
    public void scanUpdated(@ShellOption(value = "--repository-location", defaultValue = ".") String repositoryLocation,
                            @ShellOption(value = "--date", defaultValue = ".", help = "format: yyyy-MM-dd_HH:mm, the default is the day of you execute it") String date) {
        Map<MavenGAV, MavenPackageArtifact> map = scanUpdated0(repositoryLocation, date,null);
        Collects.forEach(map, new Consumer2<MavenGAV, MavenPackageArtifact>() {
            @Override
            public void accept(MavenGAV key, MavenPackageArtifact value) {
                System.out.println(key);
            }
        });
    }

    private Map<MavenGAV, MavenPackageArtifact> scanUpdated0(String repositoryLocation, String startDate, String endDate){
        Date start = null;
        if (ShellOption.NONE.equals(startDate) || ShellOption.NULL.equals(startDate) || ".".equals(startDate)) {
            startDate = null;
        }
        if (Strings.isBlank(startDate)) {
            start = new Date();
            start = Dates.setHours(start, 0);
            start = Dates.setMinutes(start, 0);
            start = Dates.setSeconds(start, 0);
            start = Dates.setMilliseconds(start, 0);
        } else {
            start = Dates.parse(startDate, "yyyy-MM-dd_HH:mm");
        }

        Date end = null;
        if (ShellOption.NONE.equals(endDate) || ShellOption.NULL.equals(endDate) || ".".equals(endDate)) {
            end = null;
        }
        if (Strings.isBlank(endDate)) {
            end = new Date();
        } else {
            end = Dates.parse(endDate, "yyyy-MM-dd_HH:mm");
        }
        final long baselineTime = start.getTime();
        final long endTime = end.getTime();
        MavenLocalRepositoryUpdatedScanner scanner = new MavenLocalRepositoryUpdatedScanner();
        Map<MavenGAV, MavenPackageArtifact> map = scanner.scan(new File(repositoryLocation), new Filter<MavenPackageArtifact>() {
            @Override
            public boolean accept(MavenPackageArtifact mavenArtifact) {
                return mavenArtifact.getLastModified() >= baselineTime && mavenArtifact.getLastModified()< endTime;
            }
        });
        return map;
    }

    @ShellMethod(key = "maven_local_repo_copyUpdated", value = "scan all dependencies after the specified time")
    public void copyUpdated(@ShellOption(value = "--repository-location", defaultValue = ".")
                                String repositoryLocation,
                            @ShellOption(value = "--start", defaultValue = ".", help = "format: yyyy-MM-dd_HH:mm, the default is the zero hours of a day of you execute it")
                                String start,
                            @ShellOption(value = "--end", defaultValue = ".", help = "format: yyyy-MM-dd_HH:mm, the default is the day of you execute it")
                                String end,
                            @ShellOption(value = "--dest-location" ,defaultValue = ".")
                                String destLocation
                            ){
        File destRootDirectory = new File(destLocation);
        if(!destRootDirectory.exists()){
            Files.makeDirs(destRootDirectory);
        }
        Preconditions.checkTrue(destRootDirectory.exists());
        Preconditions.checkTrue(destRootDirectory.isDirectory());
        Map<MavenGAV, MavenPackageArtifact> map = scanUpdated0(repositoryLocation, start, end);
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
