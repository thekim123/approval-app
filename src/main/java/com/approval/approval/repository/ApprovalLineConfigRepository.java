package com.approval.approval.repository;

import com.approval.approval.entity.ApprovalLineConfig;
import com.approval.approval.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalLineConfigRepository extends JpaRepository<ApprovalLineConfig, Long> {
    List<ApprovalLineConfig> findByType(Type type);
}
