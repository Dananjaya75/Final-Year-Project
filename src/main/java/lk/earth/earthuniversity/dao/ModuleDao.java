package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ModuleDao extends JpaRepository<Module,Integer> {
}
