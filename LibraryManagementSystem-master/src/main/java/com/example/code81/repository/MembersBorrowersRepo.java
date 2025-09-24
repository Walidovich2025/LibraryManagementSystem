package com.example.code81.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.code81.entity.MembersBorrowers;
@Repository
public interface MembersBorrowersRepo extends JpaRepository<MembersBorrowers, Long> {

    Optional<MembersBorrowers> findByEmail(String email);

    List<MembersBorrowers> findByMembershipStatus(String status);

}
