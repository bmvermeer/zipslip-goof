package io.snyk.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UploadController {

    private static final String UPLOADED_FOLDER = "";
    private static final File publicDir = new File("public/upload");

    @GetMapping("/upload")
    public String index(Model model) {
        model.addAttribute("files", getFiles());
        return "upload";
    }

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:upload";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            if (!publicDir.exists())
                publicDir.mkdirs();


            if (file.getContentType().equals("application/zip")) {
                System.out.println("extracting uploaded zip file");
                Files.newInputStream(path);
                ZipUtil.unpack(Files.newInputStream(path), publicDir);
                Files.delete(path);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/upload";
    }

    private List<String> getFiles() {
        if (!publicDir.exists()) return Collections.emptyList();

        return Arrays.asList(publicDir.listFiles()).stream().map(f -> f.getName()).collect(Collectors.toList());
    }

}
