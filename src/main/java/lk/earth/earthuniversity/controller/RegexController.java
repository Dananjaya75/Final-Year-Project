package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.entity.Employee;
import lk.earth.earthuniversity.entity.User;
import lk.earth.earthuniversity.util.RegexProvider;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;



@CrossOrigin
@RestController
@RequestMapping(value = "/regexes")
public class RegexController {

    @GetMapping(path ="/employee", produces = "application/json")
    public HashMap<String, HashMap<String, String>> employee() {
        return RegexProvider.get(new Employee());
    }

    @GetMapping(path ="/users", produces = "application/json")
    public HashMap<String, HashMap<String, String>> user() {
        return RegexProvider.get(new User());
    }


}


