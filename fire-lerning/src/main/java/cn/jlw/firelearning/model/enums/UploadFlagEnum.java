package cn.jlw.firelearning.model.enums;

import lombok.Getter;

/**
 * 上传文件标志
 */
public enum UploadFlagEnum {

    AVATAR(1, "头像");

    @Getter
    private final Integer key;
    @Getter
    private final String value;

    UploadFlagEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValue(Integer key) {
        for (UploadFlagEnum temp : values()) {
            if (temp.getKey().equals(key)) {
                return temp.getValue();
            }
        }
        return "";
    }
}
