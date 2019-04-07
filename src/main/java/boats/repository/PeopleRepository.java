package boats.repository;


import boats.domain.entities.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<People, String> {

    List<People> findPeopleByCustomerIsTrue();
}
