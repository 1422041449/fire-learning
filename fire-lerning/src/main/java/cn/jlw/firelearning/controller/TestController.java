package cn.jlw.firelearning.controller;


import cn.jlw.firelearning.config.MinioProperties;
import cn.jlw.firelearning.model.LeResponse;
import cn.jlw.firelearning.model.interfaces.PassToken;
import cn.jlw.firelearning.utils.MinioTemplate;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 测试接口类
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/test")
public class TestController {
    private final MinioTemplate minioTemplate;

    private final MinioProperties minioProperties;

    /**
     * 上传文件测试
     */
    @PassToken
    @SneakyThrows
    @GetMapping("/upload")
    public LeResponse<?> uploadFile(@RequestParam MultipartFile file,
                                    @RequestParam String param) {
        log.info(param);
        String uploadPath = "test/" + file.getOriginalFilename();
        String filePath = minioTemplate.putObject(uploadPath, file.getInputStream(), file.getSize());
        log.info("文件路径:{}", filePath);
        return LeResponse.succ(filePath);
    }


}
