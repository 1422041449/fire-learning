package cn.jlw.firelearning.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.jlw.firelearning.config.MinioProperties;
import cn.jlw.firelearning.model.LeResponse;
import cn.jlw.firelearning.model.enums.UploadFlagEnum;
import cn.jlw.firelearning.model.interfaces.PassToken;
import cn.jlw.firelearning.utils.MinioTemplate;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 操作文件
 */
@Slf4j
@PassToken
@RestController
@AllArgsConstructor
@RequestMapping("/file")
public class FileController {
    private final MinioProperties minioProperties;
    private final MinioTemplate minioTemplate;

    /**
     * 上传文件
     *
     * @param file       文件
     * @param uploadFlag 上传类型标识
     * @param content    一些其他信息
     * @return 返回文件url地址
     */
    @SneakyThrows
    @PostMapping("/upload")
    public LeResponse<String> upload(@RequestParam MultipartFile file, @RequestParam Integer uploadFlag, @RequestParam String content) {
        //构建文件系统体系
        //头像
        String fileName = file.getOriginalFilename();
        Long size = file.getSize();
        String resultUrl = "";
        if (uploadFlag == UploadFlagEnum.AVATAR.getKey()) {
            String username = "avatar";
            if (!content.equals("avatar")) {
                JSONObject jsonObject = new JSONObject(content);
                username = jsonObject.getStr("username");
                if (StrUtil.isBlank(username)) {
                    username = "avatar";
                }
            }
            String filePath = "avatar/" + username + "/" + fileName;
            minioTemplate.putObject(filePath, file.getInputStream(), size);
            resultUrl = minioProperties.getEndpoint() +"/"+ minioProperties.getBucketName()+"/" + filePath;
        }
        return LeResponse.succ(resultUrl);
    }
}
