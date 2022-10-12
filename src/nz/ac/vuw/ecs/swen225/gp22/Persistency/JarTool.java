package nz.ac.vuw.ecs.swen225.gp22.Persistency;


import java.io.*;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 * Create a jar file
 * <p>
 * <b>NOTE:</b> All code below was taken from <a href="https://www.baeldung.com/jar-create-programatically">this tutorial</a>. All comments are my own explanations. This is so I understand the code and its purpose rather than copy and pasting code without reading its purpose.
 *
 * @author Jia Wei Leong (300560651)
 */
public class JarTool {
    private Manifest manifest = new Manifest();

    /**
     * Create the manifest for the JAR file
     */
    public void startManifest() {
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
    }

    /**
     * Opens the JAR file to write files to this file
     * @param jarFile name and path of the JAR file
     * @return a JarOutputStream containing the opened JAR file
     * @throws IOException if something went wrong with the I/O of the file
     */
    public JarOutputStream openJar(String jarFile) throws IOException {
        return new JarOutputStream(new FileOutputStream(jarFile), manifest);
    }

    /**
     * Adds a file to the JAR file
     * <p>
     * This method was taken from <a href="https://www.baeldung.com/jar-create-programatically">here</a>
     * @param target the JAR file to write to
     * @param rootPath the path in the JAR to store the file
     * @param source the path to the file
     * @throws IOException if something went wrong with the I/O of the file
     */
    public void addFile(JarOutputStream target, String rootPath, String source)
            throws IOException {
        String remaining = "";
        if (rootPath.endsWith(File.separator)) {
            remaining = source.substring(rootPath.length());
        } else {
            remaining = source.substring(rootPath.length() + 1);
        }
        String name = remaining.replace("\\","/");
        JarEntry entry = new JarEntry(name);
        entry.setTime(new File(source).lastModified());
        target.putNextEntry(entry);

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(source));
        byte[] buffer = new byte[1024];
        while (true) {
            int count = in.read(buffer);
            if (count == -1) {
                break;
            }
            target.write(buffer, 0, count);
        }
        target.closeEntry();
        in.close();
    }
}
