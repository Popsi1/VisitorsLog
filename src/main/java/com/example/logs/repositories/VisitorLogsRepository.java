package com.example.logs.repositories;
import com.example.logs.models.VisitorLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorLogsRepository extends JpaRepository<VisitorLogs, Long> {
}
