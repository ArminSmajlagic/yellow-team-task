package com.yellow.offer.Domain.Services.Common;

import com.yellow.offer.Application.Requests.Common.GetAllRequest;
import com.yellow.offer.Domain.Repositories.Common.IRepository;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Service
public class BaseService<Type,GetAll extends GetAllRequest, DTOType,InsertRequestType, UpdateRequestType, IDType> implements IService<DTOType,GetAll,InsertRequestType, UpdateRequestType, IDType> {
    @Setter
    private IRepository<Type, IDType> repository;
    @Autowired
    private ModelMapper modelMapper;
    DTOType mappedResult;
    @Override
    public List<DTOType> GetAll(GetAll request) {
        //This types bellow are Classes inferred by java.lang.reflect to perform mapping; this types can not be inferred in constructor
        // this is the reason why I repeat myself in every method
        Class<DTOType> dtoType = (Class<DTOType>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[2];

        List<Type> result = repository.GetAll();

        List<DTOType> mappedResult = result.stream().map(e -> modelMapper.map(e, dtoType)).toList();

        return mappedResult;
    }
    @Override
    public DTOType GetById(IDType id) {
        Class<DTOType> dtoType = (Class<DTOType>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[2];

        Type result = repository.GetById(id);

        if(result!=null) {
            mappedResult = modelMapper.map(result, dtoType);
            return mappedResult;
        }
        else
            return null;
    }

    @Override
    public Integer InsertOne(InsertRequestType request) {
        Class<Type> mainType = (Class<Type>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        Type toBeInserted = modelMapper.map(request, mainType);

        Integer result = repository.InsertOne(toBeInserted);

        return result;
    }

    @Override
    public DTOType PatchOne(UpdateRequestType request) {
        Class<DTOType> dtoType = (Class<DTOType>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        Class<Type> mainType = (Class<Type>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        Type toBePatched = modelMapper.map(request, mainType);

        Type result = repository.PatchOne(toBePatched);

        if(result==null)
            return null;

        mappedResult = modelMapper.map(result, dtoType);

        return mappedResult;
    }

    @Override
    public DTOType DeleteOne(IDType id) {
        Class<DTOType> dtoType = (Class<DTOType>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        Class<Type> mainType = (Class<Type>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        Type entity = repository.GetById(id);

       repository.DeleteOne(id);

        mappedResult = modelMapper.map(entity, dtoType);

        return mappedResult;
    }
}
