package com.lcwd.electronicstore2.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    String uploadFile(MultipartFile file,String path) throws IOException;

    InputStream getResource(String path,String name) throws FileNotFoundException;

    Boolean deleteFile(String path,String productImageName) throws  FileNotFoundException;

    Boolean updateFIle(MultipartFile file,String path,String productId) throws FileNotFoundException;
}
