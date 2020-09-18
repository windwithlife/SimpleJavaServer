package com.simple.core.exception;

/**
 * Exception异常抽象类
 * 继承自RuntimeException, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 * @author hejinguo
 * @version $Id: AbsException.java, v 0.1 2019年1月20日 上午10:04:36
 */
public abstract class AbsException extends RuntimeException {
    /**  */
    private static final long serialVersionUID = -5812265653810227079L;

    public AbsException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public AbsException(String message, Throwable cause, boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public AbsException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public AbsException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public AbsException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
}
