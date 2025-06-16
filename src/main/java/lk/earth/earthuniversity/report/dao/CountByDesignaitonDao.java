package lk.earth.earthuniversity.report.dao;

import lk.earth.earthuniversity.report.entity.CountByDesignation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountByDesignaitonDao extends JpaRepository<CountByDesignation,Integer> {

    @Query(value = "SELECT NEW CountByDesignation(d.name, COUNT(e.name)) FROM Employee e, Designation d WHERE e.designation.id = d.id GROUP BY d.id")
    List<CountByDesignation> countByDesignation();

}

