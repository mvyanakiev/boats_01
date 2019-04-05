package boats.repository;

import boats.domain.entities.Equipment;
import boats.domain.models.serviceModels.EquipmentServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, String> {

    List<Equipment> findEquipmentByBoat_Id(String boatId);

}
