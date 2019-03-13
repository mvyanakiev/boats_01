package boats.repository;

import boats.domain.entities.Charter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharterRepository extends JpaRepository<Charter, String> {
}
