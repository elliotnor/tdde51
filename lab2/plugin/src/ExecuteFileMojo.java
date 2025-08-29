package plugin.src;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;

/**
 * A Maven plugin that executes a specified file as a process.
 */
@Mojo(name = "execute-file")
public class ExecuteFileMojo extends AbstractMojo {

    /**
     * The file to be executed.
     */
    @Parameter(property = "filePath", required = true)
    private String filePath;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (filePath == null || filePath.isEmpty()) {
            throw new MojoExecutionException("File path cannot be empty.");
        }

        File file = new File(filePath);
        if (!file.exists() || !file.canExecute()) {
            throw new MojoExecutionException("File does not exist or is not executable: " + filePath);
        }

        try {
            getLog().info("Executing file: " + filePath);
            Process process = new ProcessBuilder(filePath).inheritIO().start();
            int exitCode = process.waitFor();
            getLog().info("Execution finished with exit code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            throw new MojoExecutionException("Error executing file: " + filePath, e);
        }
    }
}
