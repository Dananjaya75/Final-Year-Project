package lk.earth.earthuniversity.security;

import lk.earth.earthuniversity.dao.UserDao;
import lk.earth.earthuniversity.entity.Privilages;
import lk.earth.earthuniversity.entity.User;
import lk.earth.earthuniversity.entity.Userrole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    final UserDao userdao;

    @Autowired
    public UserService(UserDao userdao) {
        this.userdao = userdao;
    }

    public User getByUsername(String username){

        User user = new User();

        if ("AdminEUC".equals(username)){

            user.setUsername(username);

        }else {
            user = userdao.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
        }

        return user;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username.equals("AdminEUC")) {
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("gender-list-get"));
            authorities.add(new SimpleGrantedAuthority("designation-list-get"));
            authorities.add(new SimpleGrantedAuthority("employeestatus-list-get"));
            authorities.add(new SimpleGrantedAuthority("employee-select"));

            return org.springframework.security.core.userdetails.User
                    .withUsername("AdminEUC")
                    .password(new BCryptPasswordEncoder().encode("Admin1234"))
                    .authorities(authorities)
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        }
        else {

            User user = userdao.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }

            Set<SimpleGrantedAuthority> authorities = new HashSet<>();

            List<Userrole> userroles = (List<Userrole>) user.getUserroles();

            for(Userrole u : userroles){
                List<Privilages> privileges = (List<Privilages>) u.getRole().getPrivilages();
                for (Privilages p:privileges){
                    authorities.add(new SimpleGrantedAuthority(p.getAuthority()));
                }
            }

            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .authorities(authorities)
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        }
    }
}
