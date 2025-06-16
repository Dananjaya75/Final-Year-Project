package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.UserDao;
import lk.earth.earthuniversity.entity.Privilages;
import lk.earth.earthuniversity.entity.User;
import lk.earth.earthuniversity.entity.Userrole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/authorities")
public class UserAuthorityController {

    @Autowired
    private UserDao userdao;

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> getUserAuthoritiesByUsername(@PathVariable String username) {
        User user = userdao.findByUsername(username);
        List<String> authorities = new ArrayList<>();

        if (user != null){
            List<Userrole> userroles = (List<Userrole>) user.getUserroles();

            for (Userrole u : userroles) {
                List<Privilages> Privilegess = (List<Privilages>) u.getRole().getPrivilages();
                for (Privilages p : Privilegess) {
                    String authority = p.getAuthority();
                    authorities.add(authority);
                }
            }
        }else{
            authorities = Arrays.asList(
                    "user-select","user-delete","user-update","user-insert",
                    "privilege-select","privilege-delete","privilege-update","privilege-insert",
                    "employee-select","employee-delete","employee-update","employee-insert",
                    "operations-select","operations-delete","operations-update","operations-insert",

                    "program-select","program-delete","program-update","program-insert"


            );
        }

        return authorities;
    }
}
