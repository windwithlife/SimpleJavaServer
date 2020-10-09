package com.simple.example.controller;


import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.simple.common.api.BaseResponse;
import com.simple.common.api.GenericRequest;
import com.simple.common.api.GenericResponse;
import com.simple.common.auth.AuthConstant;
import com.simple.example.dto.*;
import com.simple.example.service.ExampleService;
import org.jose4j.jwk.RsaJsonWebKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/example/mobile")
@Validated
public class ExampleController {
    static RsaJsonWebKey jwk = null;

    static final ILogger logger = SLoggerFactory.getLogger(ExampleController.class);

    @Autowired
    private ExampleService exampleService;


    @PostMapping(path = "/test")
    BaseResponse changeEmail(@RequestHeader(AuthConstant.AUTHORIZATION_HEADER) String authz, @RequestParam @Valid String request){
        BaseResponse result = BaseResponse.build().message(request);
        return result;
    }

    @PostMapping(path = "/test1")
    BaseResponse test1(@RequestParam @Valid String request){
        BaseResponse result = BaseResponse.build().message(request);
        return result;
    }

    @PostMapping(path = "/test2")
    BaseResponse test2(@RequestBody @Valid CreateAccountRequest request){
        BaseResponse result = BaseResponse.build().message(request.getName());
        return result;
    }
    @PostMapping(path = "/test3")
    ExampleVO test3(@RequestBody ExampleDto request){
        return ExampleVO.builder().name(request.getName()).email(request.getEmail()).build();
    }


    @PostMapping(path = "/test4")
    ExampleResponse test4(@RequestBody ExampleRequest request){
        return ExampleResponse.builder().name(request.getName()).email(request.getEmail()).build();
    }

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

    @PostMapping(path = "/test7")
    BaseResponse test7(@RequestBody GenericRequest req, @RequestParam String inputString){
        GenericResponse result = GenericResponse.build()
                .addKey$Value("msg", inputString)
                .addKey$Value("name", req.getString("name"));
        return result;
    }

    @PostMapping(path = "/test8")
    BaseResponse test8(@RequestBody GenericRequest req, @RequestParam String inputString){
        ExampleDto request  = req.getObject(ExampleDto.class);
        GenericResponse result = new GenericResponse();
        result.setDataObject(request);
        return result;
    }

    @PostMapping(path = "/addnew")
    BaseResponse createExample(@RequestBody GenericRequest req){
        ExampleDto paramObj  = req.getObject(ExampleDto.class);
        ExampleVO vo = this.exampleService.save(paramObj);
        GenericResponse result = new GenericResponse(vo);
        return result;
    }


    @PostMapping(path = "/update")
    BaseResponse update(@RequestBody GenericRequest req){

        ExampleDto dto  = req.getObject(ExampleDto.class);
        ExampleVO vo = this.exampleService.update(dto);
        GenericResponse result = new GenericResponse();
        result.setDataObject(vo);
        return result;
    }

    @PostMapping(path = "/deleteById")
    BaseResponse delete(@RequestParam String id){
        String resultId = this.exampleService.deleteById(id);
        GenericResponse result = new GenericResponse();
        result.addKey$Value("deleteId", resultId);
        return result;
    }

    @PostMapping(path = "/findByName")
    BaseResponse findByName(@RequestParam String name){
        List<ExampleVO> results = this.exampleService.findByName(name);
        GenericResponse result = new GenericResponse();
        result.addKey$Value("itemsCount", results.size());
        result.addKey$Value("items", results);
        return result;
    }

    @PostMapping(path = "/create")
    public GenericAccountResponse createAccount(@RequestBody @Valid CreateAccountRequest request) {
        AccountDto exampleDto = exampleService.create(request.getName(), request.getEmail(), request.getPhoneNumber(),request.getPassword());
        GenericAccountResponse genericAccountResponse = new GenericAccountResponse(exampleDto);
        return genericAccountResponse;
    }

}
