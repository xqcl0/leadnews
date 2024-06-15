package com.heima.minio.test;

import com.heima.file.service.FileStorageService;
import com.heima.minio.MinIOApplication;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootTest(classes = MinIOApplication.class)
@RunWith(SpringRunner.class)
public class MinIOTest {
    @Autowired
    private FileStorageService fileStorageService;

    @Test
    public void test() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\26949\\Downloads");
        String path = fileStorageService.uploadHtmlFile("", "hello.html", fileInputStream);
        System.out.println(path);
    }

//    public static void main(String[] args) {
//        try {
//            FileInputStream fileInputStream = new FileInputStream("D:\\start.html");
//            MinioClient minioClient = MinioClient.builder().credentials("minio", "minio123")
//                    .endpoint("http://192.168.118.128:9000")
//                    .build();
//            PutObjectArgs putObjectArgs = PutObjectArgs
//                    .builder().bucket("leadnews")
//                    .object("start.html")
//                    .contentType("text/html")
//                    .stream(fileInputStream, fileInputStream.available(), -1).build();
//            minioClient.putObject(putObjectArgs);
//
//            System.out.println("http://192.168.118.128:9000/leadnews/start.html");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//    }
}
