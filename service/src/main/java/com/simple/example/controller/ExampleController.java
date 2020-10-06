package com.simple.example.controller;

import com.alibaba.fastjson.JSON;
import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.simple.common.api.BaseResponse;
import com.simple.common.api.GenericRequest;
import com.simple.common.api.GenericResponse;
import com.simple.common.auth.AuthConstant;
import com.simple.example.dto.AccountDto;
import com.simple.example.dto.CreateAccountRequest;
import com.simple.example.dto.GenericAccountResponse;
import com.simple.example.model.ExampleModel;
import com.simple.example.service.ExampleService;
import com.simple.common.env.EnvConfig;
import com.simple.common.props.AppProps;
import org.jose4j.jwk.RsaJsonWebKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/example/mobile")
@Validated
public class ExampleController {
    static RsaJsonWebKey jwk = null;

    static final ILogger logger = SLoggerFactory.getLogger(ExampleController.class);

    @Autowired
    private ExampleService exampleService;



    @PostMapping(path = "/create")
    public GenericAccountResponse createAccount(@RequestBody @Valid CreateAccountRequest request) {
        AccountDto accountDto = exampleService.create(request.getName(), request.getEmail(), request.getPhoneNumber(),request.getPassword());
        GenericAccountResponse genericAccountResponse = new GenericAccountResponse(accountDto);
        return genericAccountResponse;
    }

    @GetMapping(path = "/test")
    BaseResponse changeEmail(@RequestHeader(AuthConstant.AUTHORIZATION_HEADER) String authz, @RequestParam @Valid String request){
        BaseResponse result = BaseResponse.build().message(request);
        return result;
    }

    @GetMapping(path = "/testrequest")
    BaseResponse test(@RequestParam @Valid String request){
        BaseResponse result = BaseResponse.build().message(request);
        return result;
    }

    @GetMapping(path = "/testexample1")
    BaseResponse testexample(@RequestBody GenericRequest req){
       return GenericResponse.builder().data(JSON.parseObject(JSON.toJSONString(req))).build();
    }

    @GetMapping(path = "/testexample2")
    BaseResponse testexample4(@RequestBody GenericRequest req, @RequestParam String inputString){
        GenericResponse result = GenericResponse.builder().build();
        result.addKey$Value("msg", inputString);
        result.addKey$Value("requestName", req.getString("name"));
        return result;
    }

    @GetMapping(path = "/testexample3")
    BaseResponse testexample2(@RequestBody GenericRequest req){
        GenericResponse result = GenericResponse.builder().build();
        result.setDataObject(req);
        return result;
    }
    @GetMapping(path = "/testexample4")
    BaseResponse testexample3(@RequestBody GenericRequest req){
        GenericResponse result = GenericResponse.builder().build();
        result.setDataObject(req);
        return result;
    }

}
