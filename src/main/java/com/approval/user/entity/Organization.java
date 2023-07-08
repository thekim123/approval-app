package com.approval.user.entity;

import com.approval.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
public class Organization extends BaseTimeEntity {

    @Id
    private String code;
    private String name;

    @OneToMany
    @Builder.Default
    private List<User> user = new ArrayList<>();

}
