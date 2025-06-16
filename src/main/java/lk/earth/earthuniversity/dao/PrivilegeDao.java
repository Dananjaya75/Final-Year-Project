package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Privilages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PrivilegeDao extends JpaRepository<Privilages,Integer> {

    Optional<Privilages> findById(Integer id);

    @Query("select e from Privilages e where e.id = :id")
    Privilages findByMyId(@Param("id") Integer id);

//    @Query("SELECT p FROM Privileges p WHERE p.module.id = :id")
//    List<Privileges> findAllByModuleId(@Param("id") Integer id);

}
