package cn.jlw.firelearning.controller;


import cn.hutool.json.JSONUtil;
import cn.jlw.firelearning.config.MinioProperties;
import cn.jlw.firelearning.entity.UserInfo;
import cn.jlw.firelearning.model.LeRequest;
import cn.jlw.firelearning.model.LeResponse;
import cn.jlw.firelearning.model.interfaces.PassToken;
import cn.jlw.firelearning.utils.MinioTemplate;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 测试接口类
 */
@Slf4j
@PassToken
@RestController
@AllArgsConstructor
@RequestMapping("/test")
public class TestController {
    @Resource(name = "redis-json")
    RedisTemplate<String, Object> redisTemplate;
    @Resource(name = "string-redis-json")
    StringRedisTemplate stringRedisTemplate;

    private final MinioTemplate minioTemplate;
    private final MinioProperties minioProperties;

    /**
     * 上传文件测试
     */
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

    /**
     * 测试redis存储
     */

    @PostMapping("/redis")
    public LeResponse<?> testRedis(@RequestBody LeRequest<UserInfo> leRequest) {
        UserInfo userInfo = leRequest.getContent();
        String testObjKey = "test:json:testObjKey";
        redisTemplate.opsForValue().set(testObjKey, userInfo, 100, TimeUnit.MINUTES);
        String testStrKey = "test:str:testStrKey";
        String testStr = "今天中午吃什么";
        stringRedisTemplate.opsForValue().set(testStrKey, testStr, 100, TimeUnit.MINUTES);
        String testStrJsonKey = "test:str:testStrJsonKey";
        stringRedisTemplate.opsForValue().set(testStrJsonKey, JSONUtil.toJsonStr(userInfo), 100, TimeUnit.MINUTES);
        return LeResponse.succ();
    }


}
