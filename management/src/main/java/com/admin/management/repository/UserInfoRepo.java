package com.admin.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.management.model.UserInfo;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {
    
    // 根据用户名查找用户
    Optional<UserInfo> findByUsername(String username);
    
    // 根据邮箱查找用户
    Optional<UserInfo> findByEmail(String email);
    
    // 根据角色查找所有用户
    List<UserInfo> findByRole(String role);
    
    // 查找所有活跃用户
    List<UserInfo> findByActiveTrue();
    
    // 根据用户名和状态查找用户
    List<UserInfo> findByUsernameContainingAndActive(String username, boolean active);
}
