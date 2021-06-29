package com.simple.example.service;

import com.github.structlog4j.ILogger;
import com.github.structlog4j.SLoggerFactory;
import com.simple.common.error.ServiceHelper;
import com.simple.example.dao.ContextQuery;
import com.simple.example.dao.ExampleDao;
//import com.simple.example.dto.AccountDto;
import com.simple.example.dao.ExampleRepository;
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

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExampleService {

    static ILogger logger = SLoggerFactory.getLogger(ExampleService.class);

    private final ExampleRepo exampleRepo;
    private final ExampleDao exampleDao;
    private final ExampleRepository dao;
    private final AppProps appProps;
    private final EnvConfig envConfig;
    private final ServiceHelper serviceHelper;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;
    private final ContextQuery contextQuery;


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

    public String deleteById(String id){
        try{
            int count =  this.exampleDao.deleteById(id);
            if (count >0){
                return id;
            }else{
                return null;
            }
        }catch (Exception e){
            ServiceHelper.handleServiceException(e, "failed to delete model");
            return null;
        }

    }

    public List<ExampleVO> findByName(String name){
        try{
            return this.exampleDao.findByName(name);
        }catch (Exception ex){
            throw new ServiceException("failed to findbyname");

        }

    }


    public List<ExampleDto> findAllPages(){
        List<ExampleDto> list = contextQuery.findList("select * from example", ExampleDto.class);
        return  list;
    }
    private ExampleVO convertToVO(ExampleModel exampleModel) {
        return modelMapper.map(exampleModel, ExampleVO.class);
    }

    private ExampleModel convertToModel(ExampleDto exampleDto) {
        return modelMapper.map(exampleDto, ExampleModel.class);
    }


}
