package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OperationDao extends JpaRepository<Operation,Integer> {

    @Query("select e from Operation e where e.id = :id")
    Operation findByMyId(@Param("id") Integer id);

    @Query("SELECT e FROM Operation e WHERE e.module.id = :id")
    List<Operation> findAllByModule(@Param("id") Integer id);

}
