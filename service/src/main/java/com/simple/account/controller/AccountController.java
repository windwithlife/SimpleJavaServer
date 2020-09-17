package com.simple.account.controller;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.simple.account.dto.*;
//import com.simple.account.security.JwtUtils;
//import com.simple.account.security.VerificationKeys;
import com.simple.account.service.AccountService;
import com.simple.common.api.ResultCode;
import com.simple.common.auth.*;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.simple.account.dto.*;
import com.simple.common.props.AppProps;
import com.simple.common.api.BaseResponse;
import com.simple.common.crypto.Sign;
import com.simple.common.env.EnvConfig;
import com.simple.common.env.EnvConstant;
import com.simple.common.error.ServiceException;
import com.simple.common.validation.PhoneNumber;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

import static com.simple.common.auth.Sessions.LONG_SESSION;
import static com.simple.common.auth.Sessions.SHORT_SESSION;

@RestController
@RequestMapping("/v1/account")
@Validated
public class AccountController {
    static RsaJsonWebKey jwk = null;

    static final ILogger logger = SLoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private EnvConfig envConfig;

    @Autowired
    private AppProps appProps;

    @PostMapping(path = "/create")
    public GenericAccountResponse createAccount(@RequestBody @Valid CreateAccountRequest request) {
        AccountDto accountDto = accountService.create(request.getName(), request.getEmail(), request.getPhoneNumber(),request.getPassword());
        GenericAccountResponse genericAccountResponse = new GenericAccountResponse(accountDto);
        return genericAccountResponse;
    }


    @PostMapping(path = "/signup")
    public GenericAccountResponse signupAccount(@RequestBody @Valid CreateAccountRequest request) {
        return this.createAccount(request);
    }
    @GetMapping(path = "/register")
    public GenericAccountResponse signupAccountTest(@RequestParam String name,  @RequestParam String email,@RequestParam String phoneNumber) {
        CreateAccountRequest request = CreateAccountRequest.builder().name(name).email(email).phoneNumber(phoneNumber).build();
        return this.createAccount(request);
    }



    @PutMapping(path = "/update")
    public GenericAccountResponse updateAccount(@RequestBody @Valid AccountDto newAccountDto) {
        //this.validateAuthenticatedUser(newAccountDto.getId());
        //this.validateEnv();

        AccountDto accountDto =  accountService.update(newAccountDto);

        GenericAccountResponse genericAccountResponse = new GenericAccountResponse(accountDto);
        return genericAccountResponse;
    }



}
