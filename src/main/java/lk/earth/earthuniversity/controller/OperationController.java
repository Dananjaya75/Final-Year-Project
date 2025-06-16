package lk.earth.earthuniversity.controller;


import lk.earth.earthuniversity.dao.OperationDao;
import lk.earth.earthuniversity.entity.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/operations")
public class OperationController {

    @Autowired
    private OperationDao operationDao;

    @GetMapping(produces = "application/json")
    public List<Operation> get(@RequestParam HashMap<String, String> params) {

        List<Operation> operations = this.operationDao.findAll();

        if(params.isEmpty())  return operations;

        String moduleid= params.get("moduleid");

        Stream<Operation> pstream = operations.stream();

        if(moduleid!=null) pstream = pstream.filter(p -> p.getModule().getId()==Integer.parseInt(moduleid));

        return pstream.collect(Collectors.toList());

    }

    @GetMapping(path ="/list/{id}", produces = "application/json")
    public List<Operation> get(@PathVariable Integer id) {

        List<Operation> operations = this.operationDao.findAllByModule(id);

        operations = operations.stream().map(
                operation -> { Operation op = new Operation();
                    op.setId(operation.getId());
                    op.setName(operation.getName());
                    op.setOpetype(operation.getOpetype());
                    op.setModule(operation.getModule());
                    return op; }
        ).collect(Collectors.toList());

        return operations;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> add(@RequestBody Operation operation){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        System.out.println(operation.getName());

        if(errors=="")
            operationDao.save(operation);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(operation.getId()));
        responce.put("url","/operations/"+operation.getId());
        responce.put("errors",errors);

        return responce;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> update(@RequestBody Operation operation){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        if(errors=="") operationDao.save(operation);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(operation.getId()));
        responce.put("url","/employees/"+operation.getId());
        responce.put("errors",errors);

        return responce;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> delete(@PathVariable Integer id){

        System.out.println(id);

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Operation prv = operationDao.findByMyId(id);

        if(prv==null)
            errors = errors+"<br> Employee Does Not Existed";

        if(errors=="") operationDao.delete(prv);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(id));
        responce.put("url","/operations/"+id);
        responce.put("errors",errors);

        return responce;
    }

}


