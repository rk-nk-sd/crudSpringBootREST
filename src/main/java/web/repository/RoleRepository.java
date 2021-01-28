package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.model.Role;

//@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("select r from Role r where r.id=:id")
    Role findById(@Param("id") Long id);

    void deleteById(Long id);

    @Query("select r from Role r where r.name=:role")
    Role findByRoleName(@Param("role") String role);
}
