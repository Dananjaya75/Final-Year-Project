package lk.earth.earthuniversity.security;

import lk.earth.earthuniversity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login2")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        User user = new User();

        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        // Generate JWT token
        //String token = jwtTokenUtil.generateToken(user);

        // Return the token as the response
        return ResponseEntity.ok("token");//new JwtResponse(token));
    }
}
