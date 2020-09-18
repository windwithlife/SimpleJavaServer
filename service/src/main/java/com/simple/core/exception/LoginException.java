package com.simple.core.exception;

/**
 * 登录异常
 * @author hejinguo
 * @version $Id: LoginException.java, v 0.1 2019年1月20日 上午10:18:33
 */
public class LoginException extends AbsException {
    /**  */
    private static final long serialVersionUID = 5802957181051925268L;

    public LoginException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public LoginException(String message, Throwable cause, boolean enableSuppression,
                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public LoginException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public LoginException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

}
