package com.lcwd.electronicstore2.services.impl;
import com.lcwd.electronicstore2.exceptions.BadApiRequest;
import com.lcwd.electronicstore2.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        String originalFileName = file.getOriginalFilename();
        logger.info("FIle Name: {}",originalFileName);
        String filename = UUID.randomUUID().toString();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileNameWithExetension = filename+extension;
        String fullPathWithFilename = path+fileNameWithExetension;

        if(extension.equalsIgnoreCase(".png")||extension.equalsIgnoreCase(".jpg")||extension.equalsIgnoreCase(".jpeg")){
            //saving File
            File folder = new File(path);
            if(!folder.exists()){
                //create folder
                folder.mkdirs();
            }

            //upload file
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFilename));
            return fileNameWithExetension;

        }else {
            throw new BadApiRequest("File With this "+extension+" format not allowe");
        }
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        String fullPath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullPath);

        return inputStream;
    }
}
