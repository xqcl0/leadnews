package com.heima.minio.test;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MinIOTest {
    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream("D:\\start.html");
            MinioClient minioClient = MinioClient.builder().credentials("minio", "minio123")
                    .endpoint("http://192.168.118.128:9000")
                    .build();
            PutObjectArgs putObjectArgs = PutObjectArgs
                    .builder().bucket("leadnews")
                    .object("start.html")
                    .contentType("text/html")
                    .stream(fileInputStream, fileInputStream.available(), -1).build();
            minioClient.putObject(putObjectArgs);

            System.out.println("http://192.168.118.128:9000/leadnews/start.html");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
