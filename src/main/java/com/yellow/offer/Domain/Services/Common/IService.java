package com.yellow.offer.Domain.Services.Common;

import java.util.Collection;

public interface IService<DTOType, GetRequest, InsertRequestType, UpdateRequestType, IDType> {
    Collection<DTOType> GetAll(GetRequest request);
    DTOType GetById(IDType id);
    Integer InsertOne(InsertRequestType request);
    DTOType PatchOne(UpdateRequestType request);
    DTOType DeleteOne(IDType id);
}
