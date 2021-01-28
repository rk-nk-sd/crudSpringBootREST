package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.model.User;

//@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    @Query("select u from User u where u.id=:id")
//    User findById(@Param("id") int id);
      User findById(long id);

//    @Query("select u from User u where u.login=:name")
//    User findByName(@Param("name") String name);
}
