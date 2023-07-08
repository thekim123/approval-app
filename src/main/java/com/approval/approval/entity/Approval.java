package com.approval.approval.entity;

import com.approval.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)

public class Approval extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private long id;
    private String title;
    private Status status;

    @OneToMany
    @Builder.Default
    private List<ApprovalLine> approvalLine = new ArrayList<>();

    public void approve() {
        this.status = Status.APPROVED;
    }
}
