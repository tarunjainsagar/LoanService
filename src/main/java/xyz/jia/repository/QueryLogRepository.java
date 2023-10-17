package xyz.jia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.jia.model.entity.QueryLog;

@Repository
public interface QueryLogRepository extends JpaRepository<QueryLog, Long> {
}
