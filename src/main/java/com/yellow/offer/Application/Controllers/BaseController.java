package com.yellow.offer.Application.Controllers;


import com.yellow.offer.Application.Requests.Common.FindOneRequest;
import com.yellow.offer.Application.Requests.Common.GetAllRequest;
import com.yellow.offer.Application.Exceptions.InternalServerErrorException;
import com.yellow.offer.Domain.Services.Common.IService;
import jakarta.persistence.Cacheable;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;

@Slf4j
public class BaseController<DTOType, FindRequest extends FindOneRequest<IDType>, GetRequest extends GetAllRequest, InsertRequest, UpdateRequest, IDType> {
    private final IService<DTOType,GetRequest, InsertRequest, UpdateRequest, IDType> service;
    @Autowired
    public BaseController(IService<DTOType,GetRequest, InsertRequest, UpdateRequest, IDType> service){
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<List<DTOType>> GetAll(GetRequest request){
        try {
            var result = service.GetAll(request);
            return ResponseEntity.ok(result.stream().toList());
        }catch (Exception e){
            log.error(e.getMessage(),e.getCause(), e.getStackTrace());
            throw new InternalServerErrorException("Service seems to be unavailable at the moment. Please try again later.");
        }
    }
    //Not necessary for completion of task and the logic in repository is not finished

    @GetMapping("/getById")
    public ResponseEntity<Object> FindOne(@Valid FindRequest request) throws NotFoundException{

        try {
            var result = service.GetById(request.getId());

            if (result == null)
                throw new NotFoundException("Entity with given id was not found!");

            return ResponseEntity.ok(result);
        }catch(NotFoundException ex){
            throw new NotFoundException("Entity with given id: "+ request.getId() +" was not found!");
        }
        catch (Exception e) {
            log.error(e.getMessage(),e.getCause(), e.getStackTrace());
            throw new InternalServerErrorException("Service seems to be unavailable at the moment. Please try again later.");
        }
    }

    //Not necessary for completion of task
    @PostMapping
    public ResponseEntity<Object> InsertOne(@RequestBody @Valid InsertRequest request) throws InternalServerErrorException{

        try {
            var result = service.InsertOne(request);

            if (result == null)
                throw new InternalServerErrorException("Seems like your account was not inserted!");

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error(e.getMessage(),e.getCause(), e.getStackTrace());
            throw new InternalServerErrorException("Service seems to be unavailable at the moment. Please try again later.");
        }
    }
    //Not necessary for completion of task and the logic in repository is not finished
    @DeleteMapping
    public ResponseEntity<Object> DeleteOne(@RequestParam("id") IDType id) throws BindException {

        try {
            if(id == null)
                throw new BindException(0,"account id");

            var result = service.DeleteOne(id);

            if (result == null)
                throw new NotFoundException("Entity with given id was not found!");

            return ResponseEntity.ok(result);
        } catch (BindException e) {
            throw new BindException(id, "account id");
        }catch(NotFoundException ex){
            throw new NotFoundException("Entity with given id: "+ id +" was not found!");
        }
        catch (Exception e) {
            log.error(e.getMessage(),e.getCause(), e.getStackTrace());
            throw new InternalServerErrorException("Service seems to be unavailable at the moment. Please try again later.");
        }
    }
    //Not necessary for completion of task
    @PatchMapping
    public ResponseEntity<Object> PatchOne(@RequestBody @Valid UpdateRequest request){
        try {
            var result = service.PatchOne(request);

            if (result == null)
                throw new NotFoundException("Entity with given id  was not found!");

            return ResponseEntity.ok(result);
        }catch(NotFoundException ex){
            throw new NotFoundException("Entity with given id was not found!");
        }
        catch (Exception e) {
            log.error(e.getMessage(),e.getCause(), e.getStackTrace());
            throw new InternalServerErrorException("Service seems to be unavailable at the moment. Please try again later.");
        }

    }
}
