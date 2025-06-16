package lk.earth.earthuniversity.controller;
import lk.earth.earthuniversity.dao.EmployeeDao;
import lk.earth.earthuniversity.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.stream.Collectors;

import java.util.List;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

    @Autowired
    private EmployeeDao employeedao;

    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasAuthority('employee-select')")p
    public List<Employee> get(@RequestParam HashMap<String, String> params) {

        List<Employee> employees = this.employeedao.findAll();

        if(params.isEmpty())  return employees;

        String number = params.get("code");
        String genderid= params.get("genderid");
        String fullname= params.get("name");
        String designationid= params.get("designationid");
        String nic= params.get("nic");

        Stream<Employee> estream = employees.stream();

        if(designationid!=null) estream = estream.filter(e -> e.getDesignation().getId()==Integer.parseInt(designationid));
        if(genderid!=null) estream = estream.filter(e -> e.getGender().getId()==Integer.parseInt(genderid));
        if(number!=null) estream = estream.filter(e -> e.getCode().equals(number));
        if(nic!=null) estream = estream.filter(e -> e.getNic().contains(nic));
        if(fullname!=null) estream = estream.filter(e -> e.getName().contains(fullname));

        return estream.collect(Collectors.toList());

    }

    @GetMapping(path ="/list",produces = "application/json")
    public List<Employee> get() {

        List<Employee> employees = this.employeedao.findAllNameId();

        employees = employees.stream().map(
                employee -> {
                    Employee e = new Employee(employee.getId(), employee.getName());
                    return  e;
                }
        ).collect(Collectors.toList());

        return employees;

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Employee-Insert')")
    public HashMap<String,String> add(@RequestBody Employee employee){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        if(employeedao.findByCode(employee.getCode())!=null)
            errors = errors+"<br> Existing Number";
        if(employeedao.findByNic(employee.getNic())!=null)
            errors = errors+"<br> Existing NIC";

        System.out.println(employee.getDoassignment());

        if(errors=="")
        employeedao.save(employee);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(employee.getId()));
        responce.put("url","/employees/"+employee.getId());
        responce.put("errors",errors);

        return responce;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Employee-Update')")
    public HashMap<String,String> update(@RequestBody Employee employee){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Employee emp1 = employeedao.findByCode(employee.getCode());
        Employee emp2 = employeedao.findByNic(employee.getNic());

        if(emp1!=null && employee.getId()!=emp1.getId())
            errors = errors+"<br> Existing Number";
        if(emp2!=null && employee.getId()!=emp2.getId())
            errors = errors+"<br> Existing NIC";

        if(errors=="") employeedao.save(employee);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(employee.getId()));
        responce.put("url","/employees/"+employee.getId());
        responce.put("errors",errors);

        return responce;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> delete(@PathVariable Integer id){

        System.out.println(id);

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

       Employee emp1 = employeedao.findByMyId(id);

        if(emp1==null)
            errors = errors+"<br> Employee Does Not Existed";

        if(errors=="") employeedao.delete(emp1);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(id));
        responce.put("url","/employees/"+id);
        responce.put("errors",errors);

        return responce;
    }

}




