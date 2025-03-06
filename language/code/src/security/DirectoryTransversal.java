package security;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class DirectoryTransversal {

    /**
     * Checks if the specified file can be read and written on the system. If
     * the file does not exist, it will be created.
     * @file the file to check.
     * @throws IOException if the file could not be read or written.
     */
    private static void ensureDirectoryExistAndCanReadAndWrite(File file) throws IOException {
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new IOException("Could not create directory " + file.getAbsolutePath());
            }
        }
        if (!file.canRead() || !file.canWrite()) {
            throw new IOException("Cannot read or write directory " + file.getAbsolutePath());
        }
    }

    /**
     * Unpacks the specified NAR file into the specified working directory.
     *
     * @param nar the NAR file to unpack.
     * @param workingDirectory the directory to unpack the NAR file into.
     * @throws IOException if the NAR file could not be unpacked.
     */
    private static void unpack(final File nar, final File workingDirectory) throws IOException {
        Path workingDirectoryPath = workingDirectory.toPath().normalize();
        try (ZipFile zipFile = new ZipFile(nar)) {
            Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
            while (zipEntries.hasMoreElements()) {
                ZipEntry zipEntry = zipEntries.nextElement();
                Path targetFilePath = workingDirectoryPath.resolve(zipEntry.getName()).normalize();

                // Prevent directory traversal attacks
                if (!targetFilePath.startsWith(workingDirectoryPath)) {
                    throw new IOException("Invalid zip file. Aborting unpacking."); 
                }

                File file = targetFilePath.toFile();
                if (zipEntry.isDirectory()) {
                    ensureDirectoryExistAndCanReadAndWrite(file);
                } else {
                    makeFile(zipFile.getInputStream(zipEntry), file);
                }
            }
        }
    }

    /**
     * Creates the specified file, whose contents will come from the
     * <tt>InputStream</tt>.
     *
     * @param inputStream the contents of the file to create.
     * @param file the file to create.
     * @throws IOException if the file could not be created.
     */
    private static void makeFile(final InputStream inputStream, final File file) throws IOException {
        try (final InputStream in = inputStream; final FileOutputStream fos = new FileOutputStream(file)) {
            byte[] bytes = new byte[65536];
            int numRead;
            while ((numRead = in.read(bytes)) != -1) {
                fos.write(bytes, 0, numRead);
            }
        }
    }

}
