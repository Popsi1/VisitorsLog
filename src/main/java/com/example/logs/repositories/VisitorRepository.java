package com.example.logs.repositories;
import com.example.visitorslogs.models.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    Visitor findVisitorByUserName(String username);
    Visitor findVisitorByUserNameAndPassword(String username, String password);
}
