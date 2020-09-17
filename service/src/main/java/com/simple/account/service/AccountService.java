package com.simple.account.service;

import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.simple.account.dto.AccountDto;
import com.simple.account.dto.AccountList;
import com.simple.account.repo.AccountRepo;
import com.simple.account.repo.AccountSecretRepo;
import com.simple.account.service.helper.ServiceHelper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.simple.account.AccountConstant;
import com.simple.account.model.Account;
import com.simple.account.model.AccountSecret;
import com.simple.common.props.AppProps;
import com.simple.common.api.BaseResponse;
import com.simple.common.api.ResultCode;
import com.simple.common.auditlog.LogEntry;
import com.simple.common.auth.AuthConstant;
import com.simple.common.auth.AuthContext;
import com.simple.common.crypto.Sign;
import com.simple.common.env.EnvConfig;
import com.simple.common.error.ServiceException;
import com.simple.common.utils.Helper;
//import com.simple.mail.client.MailClient;
//import com.simple.mail.dto.EmailRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AccountService {

    static ILogger logger = SLoggerFactory.getLogger(AccountService.class);

    private final AccountRepo accountRepo;

    private final AccountSecretRepo accountSecretRepo;

    private final AppProps appProps;

    private final EnvConfig envConfig;



    private final ServiceHelper serviceHelper;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;


    public AccountDto create(String name, String email, String phoneNumber,String pwd) {
        if (StringUtils.hasText(email)) {
            // Check to see if account exists
            Account foundAccount = accountRepo.findAccountByEmail(email);
            if (foundAccount != null) {
                throw new ServiceException("A user with that email already exists. Try another email");
            }
        }

        if (StringUtils.hasText(name)) {
            // Check to see if account exists
            Account foundAccount = accountRepo.findAccountByName(name);
            if (foundAccount != null) {
                throw new ServiceException("A user with that name already exists. Try a new name");
            }
        }

        if (StringUtils.hasText(phoneNumber)) {
            Account foundAccount = accountRepo.findAccountByPhoneNumber(phoneNumber);
            if (foundAccount != null) {
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

        Account account = Account.builder()
                .email(email).name(name).phoneNumber(phoneNumber)
                .build();
        //account.setPhotoUrl(Helper.generateGravatarUrl(account.getEmail()));
        account.setMemberSince(Instant.now());

        try {
            Account result = accountRepo.save(account);
            String userId = result.getId();
            //this.updatePassword(userId, pwd);
        } catch (Exception ex) {
            String errMsg = "Could not create user account";
            serviceHelper.handleException(logger, ex, errMsg);
            throw new ServiceException(errMsg, ex);
        }


        AccountDto accountDto = this.convertToDto(account);
        return accountDto;
    }



    public AccountDto update(AccountDto newAccountDto) {
        Account newAccount = this.convertToModel(newAccountDto);

        Account existingAccount = accountRepo.findAccountById(newAccount.getId());
        if (existingAccount == null) {
            throw new ServiceException(ResultCode.NOT_FOUND, String.format("User with id %s not found", newAccount.getId()));
        }
        entityManager.detach(existingAccount);

//        if (!serviceHelper.isAlmostSameInstant(newAccount.getMemberSince(), existingAccount.getMemberSince())) {
//            throw new ServiceException(ResultCode.REQ_REJECT, "You cannot modify the member_since date");
//        }

        if (StringUtils.hasText(newAccount.getEmail()) && !newAccount.getEmail().equals(existingAccount.getEmail())) {
            Account foundAccount = accountRepo.findAccountByEmail(newAccount.getEmail());
            if (foundAccount != null) {
                throw new ServiceException(ResultCode.REQ_REJECT, "A user with that email already exists. Try a password reset");
            }
        }

        if (StringUtils.hasText(newAccount.getPhoneNumber()) && !newAccount.getPhoneNumber().equals(existingAccount.getPhoneNumber())) {
            Account foundAccount = accountRepo.findAccountByPhoneNumber(newAccount.getPhoneNumber());
            if (foundAccount != null) {
                throw new ServiceException(ResultCode.REQ_REJECT, "A user with that phonenumber already exists. Try a password reset");
            }
        }

        if (AuthConstant.AUTHORIZATION_AUTHENTICATED_USER.equals(AuthContext.getAuthz())) {
            if (!existingAccount.isConfirmedAndActive() && newAccount.isConfirmedAndActive()) {
                throw new ServiceException(ResultCode.REQ_REJECT, "You cannot activate this account");
            }
            if (existingAccount.isSupport() != newAccount.isSupport()) {
                throw new ServiceException(ResultCode.REQ_REJECT, "You cannot change the support parameter");
            }
            if (!existingAccount.getPhotoUrl().equals(newAccount.getPhotoUrl())) {
                throw new ServiceException(ResultCode.REQ_REJECT, "You cannot change the photo through this endpoint (see docs)");
            }
            // User can request email change - not do it :-)
            if (!existingAccount.getEmail().equals(newAccount.getEmail())) {
                //this.requestEmailChange(newAccount.getId(), newAccount.getEmail());
                // revert
                newAccount.setEmail(existingAccount.getEmail());
            }
        }

        newAccount.setPhotoUrl(Helper.generateGravatarUrl(newAccount.getEmail()));

        try {
            accountRepo.save(newAccount);
        } catch (Exception ex) {
            String errMsg = "Could not update the user account";
            serviceHelper.handleException(logger, ex, errMsg);
            throw new ServiceException(errMsg, ex);
        }

        //serviceHelper.syncUserAsync(newAccount.getId());

        LogEntry auditLog = LogEntry.builder()
                .authorization(AuthContext.getAuthz())
                .currentUserId(AuthContext.getUserId())
                .targetType("account")
                .targetId(newAccount.getId())
                .originalContents(existingAccount.toString())
                .updatedContents(newAccount.toString())
                .build();

        logger.info("updated account", auditLog);

        // If account is being activated, or if phone number is changed by current user - send text
        if (newAccount.isConfirmedAndActive() &&
                StringUtils.hasText(newAccount.getPhoneNumber()) &&
                !newAccount.getPhoneNumber().equals(existingAccount.getPhoneNumber())) {
            //serviceHelper.sendSmsGreeting(newAccount.getId());
        }

        //this.trackEventWithAuthCheck("account_updated");

        AccountDto accountDto = this.convertToDto(newAccount);
        return accountDto;
    }


    private AccountDto convertToDto(Account account) {
        return modelMapper.map(account, AccountDto.class);
    }

    private Account convertToModel(AccountDto accountDto) {
        return modelMapper.map(accountDto, Account.class);
    }


}
