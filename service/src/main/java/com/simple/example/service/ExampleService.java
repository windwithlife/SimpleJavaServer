package com.simple.example.service;

import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.simple.common.error.ServiceHelper;
import com.simple.example.dto.AccountDto;
import com.simple.example.model.ExampleModel;
import com.simple.example.dao.ExampleRepo;

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
            ExampleModel foundExampleModel = exampleRepo.findAccountByPhoneNumber(phoneNumber);
            if (foundExampleModel != null) {
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

        ExampleModel exampleModel = ExampleModel.builder()
                .email(email).name(name).phoneNumber(phoneNumber)
                .build();


        try {
            ExampleModel result = exampleRepo.save(exampleModel);
            String userId = result.getId();
            //this.updatePassword(userId, pwd);
        } catch (Exception ex) {
            String errMsg = "Could not create user exampleModel";
            serviceHelper.handleException(logger, ex, errMsg);
            throw new ServiceException(errMsg, ex);
        }


        AccountDto accountDto = this.convertToDto(exampleModel);
        return accountDto;
    }



    public AccountDto update(AccountDto newAccountDto) {
        ExampleModel newExampleModel = this.convertToModel(newAccountDto);

        ExampleModel existingExampleModel = exampleRepo.findAccountById(newExampleModel.getId());
        if (existingExampleModel == null) {
            throw new ServiceException(ResultCode.NOT_FOUND, String.format("User with id %s not found", newExampleModel.getId()));
        }
        entityManager.detach(existingExampleModel);

        if (StringUtils.hasText(newExampleModel.getEmail()) && !newExampleModel.getEmail().equals(existingExampleModel.getEmail())) {
            ExampleModel foundExampleModel = exampleRepo.findAccountByEmail(newExampleModel.getEmail());
            if (foundExampleModel != null) {
                throw new ServiceException(ResultCode.REQ_REJECT, "A user with that email already exists. Try a password reset");
            }
        }

        if (StringUtils.hasText(newExampleModel.getPhoneNumber()) && !newExampleModel.getPhoneNumber().equals(existingExampleModel.getPhoneNumber())) {
            ExampleModel foundExampleModel = exampleRepo.findAccountByPhoneNumber(newExampleModel.getPhoneNumber());
            if (foundExampleModel != null) {
                throw new ServiceException(ResultCode.REQ_REJECT, "A user with that phonenumber already exists. Try a password reset");
            }
        }

        if (AuthConstant.AUTHORIZATION_AUTHENTICATED_USER.equals(AuthContext.getAuthz())) {
            if (!existingExampleModel.isConfirmedAndActive() && newExampleModel.isConfirmedAndActive()) {
                throw new ServiceException(ResultCode.REQ_REJECT, "You cannot activate this account");
            }
            if (existingExampleModel.isSupport() != newExampleModel.isSupport()) {
                throw new ServiceException(ResultCode.REQ_REJECT, "You cannot change the support parameter");
            }
            if (!existingExampleModel.getPhotoUrl().equals(newExampleModel.getPhotoUrl())) {
                throw new ServiceException(ResultCode.REQ_REJECT, "You cannot change the photo through this endpoint (see docs)");
            }
            // User can request email change - not do it :-)
            if (!existingExampleModel.getEmail().equals(newExampleModel.getEmail())) {

                newExampleModel.setEmail(existingExampleModel.getEmail());
            }
        }

        newExampleModel.setPhotoUrl(Helper.generateGravatarUrl(newExampleModel.getEmail()));

        try {
            exampleRepo.save(newExampleModel);
        } catch (Exception ex) {
            String errMsg = "Could not update the user account";
            serviceHelper.handleException(logger, ex, errMsg);
            throw new ServiceException(errMsg, ex);
        }



        LogEntry auditLog = LogEntry.builder()
                .authorization(AuthContext.getAuthz())
                .currentUserId(AuthContext.getUserId())
                .targetType("account")
                .targetId(newExampleModel.getId())
                .originalContents(existingExampleModel.toString())
                .updatedContents(newExampleModel.toString())
                .build();

        logger.info("updated account", auditLog);

        // If account is being activated, or if phone number is changed by current user - send text
        if (newExampleModel.isConfirmedAndActive() &&
                StringUtils.hasText(newExampleModel.getPhoneNumber()) &&
                !newExampleModel.getPhoneNumber().equals(existingExampleModel.getPhoneNumber())) {
            //serviceHelper.sendSmsGreeting(newExampleModel.getId());
        }

        //this.trackEventWithAuthCheck("account_updated");

        AccountDto accountDto = this.convertToDto(newExampleModel);
        return accountDto;
    }


    private AccountDto convertToDto(ExampleModel exampleModel) {
        return modelMapper.map(exampleModel, AccountDto.class);
    }

    private ExampleModel convertToModel(AccountDto accountDto) {
        return modelMapper.map(accountDto, ExampleModel.class);
    }


}
