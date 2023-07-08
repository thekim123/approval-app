package com.approval.approval.service;

import com.approval.approval.entity.*;
import com.approval.approval.repository.ApprovalLineConfigRepository;
import com.approval.approval.repository.ApprovalLineRepository;
import com.approval.approval.repository.ApprovalRepository;
import com.approval.user.entity.User;
import com.approval.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final ApprovalLineConfigRepository configRepository;
    private final ApprovalLineRepository lineRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createApproval(User user, Type type) {
        Approval approval = Approval.builder()
                .title(type.toString())
                .build();

        List<ApprovalLineConfig> configList = configRepository.findByType(type);
        List<ApprovalLine> lineList =
                configList.stream().map(config -> {
                    List<User> userList = userRepository
                            .findByOrganizationAndPosition(user.getOrganization(), config.getPosition());
                    return config.makeApprovalLine(approval, userList);
                }).collect(Collectors.toList());

        ApprovalLine requestLine = ApprovalLine.builder()
                .approvalDate(LocalDate.now())
                .approvalStatus(Status.APPROVED)
                .step(1)
                .user(List.of(user))
                .build();
        lineList.add(requestLine);
        lineRepository.saveAll(lineList);
    }

    @Transactional
    public void decide(User user, long approvalId, Status status) {
        Approval approval = approvalRepository.findById(approvalId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 결재가 없습니다."));
        List<ApprovalLine> lineList = lineRepository.findByApproval(approval);

        ApprovalLine changedLine = changeLineStatus(user, lineList, status);

        if (status == Status.APPROVED) {
            deleteOtherLineWhenApproved(lineList, changedLine);
        }
        checkAllLineApproved(approval, lineList);
    }

    public ApprovalLine changeLineStatus(User user, List<ApprovalLine> lineList, Status status) {
        return lineList.stream().filter(l -> l.getUser().contains(user))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 결재자가 없습니다."))
                .approve(status);
    }

    public void checkAllLineApproved(Approval approval, List<ApprovalLine> lineList) {
        if (isApprovalComplete(lineList)) {
            approval.approve();
        }
    }

    public boolean isApprovalComplete(List<ApprovalLine> lineList) {
        return lineList.stream().allMatch(l -> l.getApprovalStatus() == Status.APPROVED);
    }

    public void deleteOtherLineWhenApproved(List<ApprovalLine> lineList, ApprovalLine changedLine) {
        List<ApprovalLine> deleteLineList =
                lineList.stream().filter(l -> isDeletedLine(changedLine, l))
                        .collect(Collectors.toList());
        lineRepository.deleteAll(deleteLineList);
    }

    public boolean isDeletedLine(ApprovalLine changedLine, ApprovalLine l) {
        return l.getApprovalStatus() == Status.PENDING && l.getStep() == changedLine.getStep();
    }

}
