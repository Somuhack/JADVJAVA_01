package com.example.usersm.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.usersm.Entity.AuditLog;

public interface AuditRepository extends JpaRepository<AuditLog,Long>{
    
}
