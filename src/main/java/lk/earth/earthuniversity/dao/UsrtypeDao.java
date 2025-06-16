package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Usetype;
import org.hibernate.usertype.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsrtypeDao extends JpaRepository<Usetype,Integer> {
}
