package com.simple.example.controller;


import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.simple.common.api.*;
import com.simple.common.auth.AuthConstant;
import com.simple.common.auth.Authorize;
import com.simple.common.auth.LoginRequired;
import com.simple.example.dto.*;
import com.simple.example.service.ExampleService;
import org.jose4j.jwk.RsaJsonWebKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/mobile/exampleService")
@Validated
public class ExampleController {


    static final ILogger logger = SLoggerFactory.getLogger(ExampleController.class);

    @Autowired
    private ExampleService exampleService;

    //支持，但不建议使用
    @PostMapping(path = "/test")
    BaseResponse changeEmail(@RequestHeader(AuthConstant.AUTHORIZATION_HEADER) String authz, @RequestParam @Valid String request){
        BaseResponse result = BaseResponse.build().message(request);
        return result;
    }

    /*
    //支持，但不建议使用
    @PostMapping(path = "/test1")
    BaseResponse test1(@RequestParam @Valid String request){
        BaseResponse result = BaseResponse.build().message(request);
        return result;
    }

    //支持，但不建议使用
    @PostMapping(path = "/test2")
    BaseResponse test2(@RequestBody @Valid CreateAccountRequest request){
        BaseResponse result = BaseResponse.build().message(request.getName());
        return result;
    }

    //支持，但不建议使用
    @PostMapping(path = "/test3")
    ExampleVO test3(@RequestBody ExampleDto request){
        return ExampleVO.builder().name(request.getName()).email(request.getEmail()).build();
    }


    @PostMapping(path = "/test4")
    ExampleResponse test4(@RequestBody ExampleRequest request){
        return ExampleResponse.builder().name(request.getName()).email(request.getEmail()).build();
    }
    */
    @PostMapping(path = "/test5")
    BaseResponse test5(@RequestBody GenericRequest req){
        return new GenericResponse(req);
    }
    @PostMapping(path = "/test6")
    BaseResponse test6(@RequestBody GenericRequest req){
       GenericResponse result = new GenericResponse();
       result.setDataObject(ExampleVO.builder().email(req.getString("email")).name(req.getString("name")).build());
       return result;
    }

    @Authorize("guest")
    @PostMapping(path = "/testAuthorize")
    BaseResponse test7(@RequestBody GenericRequest req, @RequestParam String inputString){
        GenericResponse result = GenericResponse.build()
                .addKey$Value("msg", inputString)
                .addKey$Value("name", req.getString("name"));
        return result;
    }

    @PostMapping(path = "/example8")
    @LoginRequired
    BaseResponse example8(@RequestBody GenericRequest req, @RequestParam String inputString){
        ExampleDto request  = req.getObject(ExampleDto.class);
        GenericResponse result = new GenericResponse();
        result.setDataObject(request);
        return result;
    }

    @PostMapping(path = "/example9")
    @LoginRequired
    public SimpleResponse<ExampleDto> example9(@RequestBody SimpleRequest<ExampleDto> params){
        ExampleDto request  = params.getParams();
        SimpleResponse<ExampleDto> result = new SimpleResponse<ExampleDto>();
        return result.success(request);
    }


//
//    //支持，但不建议使用，此处支持微服务内RPC
//    @PostMapping(path = "/create")
//    public GenericAccountResponse createAccount(@RequestBody @Valid CreateAccountRequest request) {
//        AccountDto exampleDto = exampleService.create(request.getName(), request.getEmail(), request.getPhoneNumber(),request.getPassword());
//        GenericAccountResponse genericAccountResponse = new GenericAccountResponse(exampleDto);
//        return genericAccountResponse;
//    }

}
