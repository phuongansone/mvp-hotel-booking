package com.hotel.mvp.hotelbooking.repository;

import com.hotel.mvp.hotelbooking.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
