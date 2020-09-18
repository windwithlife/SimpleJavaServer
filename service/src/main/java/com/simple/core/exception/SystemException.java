package com.simple.core.exception;

/**
 *系统异常 
 * @author hejinguo
 * @version $Id: SystemException.java, v 0.1 2019年1月20日 上午10:05:17
 */
public class SystemException extends AbsException {
    /**  */
    private static final long serialVersionUID = -1582224349390557864L;

    public SystemException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public SystemException(String message, Throwable cause, boolean enableSuppression,
                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public SystemException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public SystemException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
}
