package com.configme.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    @Autowired
    private AWSS3Service awss3Service;

    public String uploadImage(MultipartFile file, String ENTITY_NAME) throws IOException {
        final String orig_img_name = file.getOriginalFilename();

        /* create file in tmp folder with unique name */
        File convFile = File.createTempFile(RandomStringUtils.random(8), orig_img_name);
        convFile.createNewFile();
        convFile.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();

        /* Do some  verifications before upload here? */
        awss3Service.upload(orig_img_name, convFile);
        /* Get url from AWS S3 bucket */
        final String url = awss3Service.getUrl(orig_img_name).toString();

        if (url.isEmpty()) {
            throw new MalformedURLException("Url is empty:" + ENTITY_NAME + " seems like image upload failed");
        }

        return url;
    }
}
