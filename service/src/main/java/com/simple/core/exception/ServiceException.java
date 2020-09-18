package com.simple.core.exception;

/**
 * 业务自定义异常
 * @author hejinguo
 * @version $Id: ServiceException.java, v 0.1 2019年1月20日 上午10:05:06
 */
public class ServiceException extends AbsException {

    /**  */
    private static final long serialVersionUID = 3314548681615064110L;

    public ServiceException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression,
                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public ServiceException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public ServiceException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
}
