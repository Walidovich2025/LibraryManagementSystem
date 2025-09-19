package com.example.code81.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.code81.entity.UserActivityLog;
@Repository
public interface UserActivityLogRepo extends JpaRepository<UserActivityLog, Long>{

    

}
