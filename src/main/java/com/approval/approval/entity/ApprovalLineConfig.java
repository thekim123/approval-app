package com.approval.approval.entity;

import com.approval.common.BaseTimeEntity;
import com.approval.user.entity.Position;
import com.approval.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ApprovalLineConfig extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int step;
    private Position position;

    @Enumerated(EnumType.STRING)
    private Type type;

    public ApprovalLine makeApprovalLine(Approval approval, List<User> userList) {
        return ApprovalLine.builder()
                .approval(approval)
                .step(this.step)
                .user(userList)
                .approvalStatus(Status.PENDING)
                .build();
    }

}
