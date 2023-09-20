package exchange.foreignexchangeapi.repositories;

import exchange.foreignexchangeapi.model.entities.ErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {

}
