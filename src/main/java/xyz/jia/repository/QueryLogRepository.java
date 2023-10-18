package xyz.jia.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.jia.model.entity.QueryLog;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QueryLogRepository extends JpaRepository<QueryLog, Long> {

    List<QueryLog> findByRequestTimestampBetween(LocalDateTime fromDate, LocalDateTime toDate, Sort sort);
}
