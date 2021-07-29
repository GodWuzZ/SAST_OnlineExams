package sast.onlineexams.common.api;

/**
 * @author sherman
 * @create 2021-07-29 8:33
 * @description 封装API的错误码
 */
public interface ErrorCode {
    long getCode();
    String getMessage();
}
