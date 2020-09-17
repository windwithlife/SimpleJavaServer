package com.simple.account.service.helper;

import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.google.common.collect.Maps;
import io.intercom.api.Avatar;
import io.intercom.api.CustomAttribute;
import io.intercom.api.Event;
import io.intercom.api.User;
import io.sentry.SentryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.simple.common.config.AppConfig;
import com.simple.account.model.Account;
import com.simple.account.repo.AccountRepo;
import com.simple.common.api.BaseResponse;
import com.simple.common.api.ResultCode;
import com.simple.common.auth.AuthConstant;
import com.simple.common.env.EnvConfig;
import com.simple.common.error.ServiceException;
//import xyz.staffjoy.company.client.CompanyClient;
//import xyz.staffjoy.company.dto.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class ServiceHelper {
    static final ILogger logger = SLoggerFactory.getLogger(ServiceHelper.class);

    private final SentryClient sentryClient;
    private final EnvConfig envConfig;
    public void handleError(ILogger log, String errMsg) {
        log.error(errMsg);
        if (!envConfig.isDebug()) {
            sentryClient.sendMessage(errMsg);
        }
    }
    public void handleException(ILogger log, Exception ex, String errMsg) {
        log.error(errMsg, ex);
        if (!envConfig.isDebug()) {
            sentryClient.sendException(ex);
        }
    }
}
