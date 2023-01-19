package com.yellow.offer.Domain.Repositories.Common;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRepository<Type, IDType>{
    List<Type> GetAll();
    Type GetById(IDType id);
    Integer InsertOne(Type request);
    Type PatchOne(Type request);
    Type DeleteOne(IDType id);
}
