package com.jn.shelltools.command;

import com.jn.langx.Filter;
import com.jn.langx.util.Dates;
import com.jn.langx.util.collection.Collects;
import com.jn.langx.util.function.Consumer2;
import com.jn.shelltools.core.maven.MavenLocalRepositoryUpdatedScanner;
import com.jn.shelltools.core.maven.model.GAV;
import com.jn.shelltools.core.maven.model.MavenArtifact;
import org.apache.logging.log4j.util.Strings;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;
import java.util.Date;
import java.util.Map;

@ShellComponent
public class MavenLocalRepositoryUpdatedScanCommands {

    /**
     * <pre>
     *     scan --repository-location D:/mvn_repo_download_test --date 2020-07-16_00:00
     * </pre>
     * @param repositoryLocation
     * @param date
     */
    @ShellMethod(key = "scan", value = "scan all dependencies after the specified time")
    public void scanUpdated(@ShellOption(defaultValue = ".") String repositoryLocation,
                            @ShellOption(value = "--date", defaultValue = ".") String date) {
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
        Map<GAV, MavenArtifact> map = scanner.scan(new File(repositoryLocation), new Filter<MavenArtifact>() {
            @Override
            public boolean accept(MavenArtifact mavenArtifact) {
                return mavenArtifact.getLastModifiedTime() >= baselineTime;
            }
        });
        Collects.forEach(map, new Consumer2<GAV, MavenArtifact>() {
            @Override
            public void accept(GAV key, MavenArtifact value) {
                System.out.println(key);
            }
        });
    }
}
