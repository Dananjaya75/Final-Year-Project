package lk.earth.earthuniversity.controller;


import lk.earth.earthuniversity.dao.OpetypeDao;
import lk.earth.earthuniversity.entity.Opetype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/opetypes")
public class OpetypeController {

    @Autowired
    private OpetypeDao opetypedao;

    @GetMapping(path ="/list", produces = "application/json")
    public List<Opetype> get() {

        List<Opetype> opetypees = this.opetypedao.findAll();

        opetypees = opetypees.stream().map(
                opetype -> { Opetype d = new Opetype();
                    d.setId(opetype.getId());
                    d.setName(opetype.getName());
                    return d; }
        ).collect(Collectors.toList());

        return opetypees;

    }

}


