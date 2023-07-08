package com.approval.approval.entity;

import com.approval.common.BaseTimeEntity;
import com.approval.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class ApprovalLine extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int step;
    private Status approvalStatus;
    private LocalDate approvalDate;
    private String comment;


    @ManyToOne
    private Approval approval;
    @ManyToMany
    private List<User> user;

    public ApprovalLine approve(Status status) {
        this.approvalStatus = status;
        return this;
    }
}
