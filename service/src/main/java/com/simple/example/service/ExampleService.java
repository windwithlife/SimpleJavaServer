package com.simple.example.service;

import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.simple.example.dto.AccountDto;
import com.simple.example.model.Example;
import com.simple.example.dao.ExampleRepo;
import com.simple.core.exception.ServiceHelper;
import com.simple.common.api.ResultCode;
import com.simple.common.auditlog.LogEntry;
import com.simple.common.auth.AuthConstant;
import com.simple.common.auth.AuthContext;
import com.simple.common.env.EnvConfig;
import com.simple.common.error.ServiceException;
import com.simple.common.props.AppProps;
import com.simple.common.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor
public class ExampleService {

    static ILogger logger = SLoggerFactory.getLogger(ExampleService.class);

    private final ExampleRepo exampleRepo;



    private final AppProps appProps;

    private final EnvConfig envConfig;



    private final ServiceHelper serviceHelper;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;


    public AccountDto create(String name, String email, String phoneNumber,String pwd) {



        if (StringUtils.hasText(phoneNumber)) {
            Example foundExample = exampleRepo.findAccountByPhoneNumber(phoneNumber);
            if (foundExample != null) {
                throw new ServiceException("A user with that phonenumber already exists. Try a new phonenumber");
            }
        }

        // Column name/email/phone_number cannot be null
        if (name == null) {
            name = "";
        }
        if (email == null) {
            email = "";
        }
        if (phoneNumber == null) {
            phoneNumber = "";
        }

        Example example = Example.builder()
                .email(email).name(name).phoneNumber(phoneNumber)
                .build();


        try {
            Example result = exampleRepo.save(example);
            String userId = result.getId();
            //this.updatePassword(userId, pwd);
        } catch (Exception ex) {
            String errMsg = "Could not create user example";
            serviceHelper.handleException(logger, ex, errMsg);
            throw new ServiceException(errMsg, ex);
        }


        AccountDto accountDto = this.convertToDto(example);
        return accountDto;
    }



    public AccountDto update(AccountDto newAccountDto) {
        Example newExample = this.convertToModel(newAccountDto);

        Example existingExample = exampleRepo.findAccountById(newExample.getId());
        if (existingExample == null) {
            throw new ServiceException(ResultCode.NOT_FOUND, String.format("User with id %s not found", newExample.getId()));
        }
        entityManager.detach(existingExample);

        if (StringUtils.hasText(newExample.getEmail()) && !newExample.getEmail().equals(existingExample.getEmail())) {
            Example foundExample = exampleRepo.findAccountByEmail(newExample.getEmail());
            if (foundExample != null) {
                throw new ServiceException(ResultCode.REQ_REJECT, "A user with that email already exists. Try a password reset");
            }
        }

        if (StringUtils.hasText(newExample.getPhoneNumber()) && !newExample.getPhoneNumber().equals(existingExample.getPhoneNumber())) {
            Example foundExample = exampleRepo.findAccountByPhoneNumber(newExample.getPhoneNumber());
            if (foundExample != null) {
                throw new ServiceException(ResultCode.REQ_REJECT, "A user with that phonenumber already exists. Try a password reset");
            }
        }

        if (AuthConstant.AUTHORIZATION_AUTHENTICATED_USER.equals(AuthContext.getAuthz())) {
            if (!existingExample.isConfirmedAndActive() && newExample.isConfirmedAndActive()) {
                throw new ServiceException(ResultCode.REQ_REJECT, "You cannot activate this account");
            }
            if (existingExample.isSupport() != newExample.isSupport()) {
                throw new ServiceException(ResultCode.REQ_REJECT, "You cannot change the support parameter");
            }
            if (!existingExample.getPhotoUrl().equals(newExample.getPhotoUrl())) {
                throw new ServiceException(ResultCode.REQ_REJECT, "You cannot change the photo through this endpoint (see docs)");
            }
            // User can request email change - not do it :-)
            if (!existingExample.getEmail().equals(newExample.getEmail())) {

                newExample.setEmail(existingExample.getEmail());
            }
        }

        newExample.setPhotoUrl(Helper.generateGravatarUrl(newExample.getEmail()));

        try {
            exampleRepo.save(newExample);
        } catch (Exception ex) {
            String errMsg = "Could not update the user account";
            serviceHelper.handleException(logger, ex, errMsg);
            throw new ServiceException(errMsg, ex);
        }



        LogEntry auditLog = LogEntry.builder()
                .authorization(AuthContext.getAuthz())
                .currentUserId(AuthContext.getUserId())
                .targetType("account")
                .targetId(newExample.getId())
                .originalContents(existingExample.toString())
                .updatedContents(newExample.toString())
                .build();

        logger.info("updated account", auditLog);

        // If account is being activated, or if phone number is changed by current user - send text
        if (newExample.isConfirmedAndActive() &&
                StringUtils.hasText(newExample.getPhoneNumber()) &&
                !newExample.getPhoneNumber().equals(existingExample.getPhoneNumber())) {
            //serviceHelper.sendSmsGreeting(newExample.getId());
        }

        //this.trackEventWithAuthCheck("account_updated");

        AccountDto accountDto = this.convertToDto(newExample);
        return accountDto;
    }


    private AccountDto convertToDto(Example example) {
        return modelMapper.map(example, AccountDto.class);
    }

    private Example convertToModel(AccountDto accountDto) {
        return modelMapper.map(accountDto, Example.class);
    }


}
