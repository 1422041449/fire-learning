package cn.jlw.firelearning.utils;

import cn.jlw.firelearning.config.MinioProperties;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * 文件系统
 */
@Slf4j
@Component
@EnableConfigurationProperties(MinioProperties.class)
public class MinioTemplate {
    @Autowired
    MinioProperties minioProperties;

    @Autowired
    MinioClient minioClient;

    /**
     * 上传文件
     * 返回图片访问地址
     *
     * @param object      文件路径及名称
     * @param inputStream 文件流
     * @param size
     * @return
     */
    @SneakyThrows
    public String putObject(String object, InputStream inputStream, Long size) {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(minioProperties.getBucketName())
                        .object(object)
                        .stream(inputStream, size, -1).build());
        StringBuilder sb = new StringBuilder()
                .append(minioProperties.getEndpoint()).append("/").append(minioProperties.getBucketName()).append("/").append(object);
        log.info("文件路径:{}", sb.toString());
        return sb.toString();
    }

    /**
     * 删除文件
     */
    @SneakyThrows
    public void removeObject(String url) {
        int index = url.indexOf("/", 3);
        String object = url.substring(index);
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(minioProperties.getBucketName())
                        .object(object)
                        .build()
        );
    }
}
