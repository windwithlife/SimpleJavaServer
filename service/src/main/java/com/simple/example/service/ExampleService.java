package com.simple.example.service;

import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.simple.common.error.ServiceHelper;
import com.simple.example.dao.ExampleDao;
import com.simple.example.dto.AccountDto;
import com.simple.example.dto.ExampleDto;
import com.simple.example.dto.ExampleVO;
import com.simple.example.model.ExampleModel;
import com.simple.example.dao.ExampleRepo;

import com.simple.common.env.EnvConfig;
import com.simple.common.error.ServiceException;
import com.simple.common.props.AppProps;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ExampleService {

    static ILogger logger = SLoggerFactory.getLogger(ExampleService.class);

    private final ExampleRepo exampleRepo;
    private final ExampleDao exampleDao;

    private final AppProps appProps;

    private final EnvConfig envConfig;

    private final ServiceHelper serviceHelper;

    private final ModelMapper modelMapper;


    public ExampleVO save(ExampleDto example){
        try{
            ExampleModel model = this.convertToModel(example);
            this.exampleDao.add(model);
            System.out.println(model.toString());
            ExampleVO vo = this.convertToVO(model);
            System.out.println(vo.toString());
            //return this.convertToVO(model);
            return vo;

        }catch (Exception ex){
            ServiceHelper.handleServiceException(ex,"failed");
            throw new ServiceException("failed to add data to database!");
        }


    }

    public ExampleVO update(ExampleDto example){
        try{
            ExampleModel model = this.convertToModel(example);
            this.exampleDao.update(model);

            return this.convertToVO(model);

        }catch (Exception ex){
            ServiceHelper.handleServiceException(ex,"failed to add");
            throw new ServiceException("failed to add data to database!");
        }


    }

    private ExampleVO convertToVO(ExampleModel exampleModel) {
        return modelMapper.map(exampleModel, ExampleVO.class);
    }

    private ExampleModel convertToModel(ExampleDto exampleDto) {
        return modelMapper.map(exampleDto, ExampleModel.class);
    }

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

        return this.modelMapper.map(exampleModel, AccountDto.class);

    }


}
