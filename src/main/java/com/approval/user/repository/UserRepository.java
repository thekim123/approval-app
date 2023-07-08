package com.approval.user.repository;

import com.approval.user.entity.Organization;
import com.approval.user.entity.Position;
import com.approval.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByOrganizationAndPosition(Organization organization, Position position);

}
