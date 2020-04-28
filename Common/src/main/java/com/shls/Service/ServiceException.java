package com.shls.Service;

/**
 * 自定义异常
 */
public class ServiceException extends RuntimeException {
    public ServiceException(){
        super();
    }

    public ServiceException(String mes){
        super(mes);
    }
}
