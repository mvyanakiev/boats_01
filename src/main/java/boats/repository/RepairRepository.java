package boats.repository;


import boats.domain.entities.People;
import boats.domain.entities.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairRepository extends JpaRepository<Repair, String> {



}
