package com.example.logs.repositories;
import com.example.logs.models.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Staff findStaffByUserName(String username);
    Staff findStaffByUserNameAndPassword(String username, String password);
}
