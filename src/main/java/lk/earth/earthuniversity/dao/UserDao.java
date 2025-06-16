package lk.earth.earthuniversity.dao;


import lk.earth.earthuniversity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository<User,Integer> {
    User findByUsername(String username);
}
