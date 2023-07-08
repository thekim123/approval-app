package com.approval.approval.repository;

import com.approval.approval.entity.Approval;
import com.approval.approval.entity.ApprovalLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalLineRepository extends JpaRepository<ApprovalLine, Long> {

    List<ApprovalLine> findByApproval(Approval approval);

}
