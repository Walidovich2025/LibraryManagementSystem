package com.example.code81.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.code81.entity.MembersBorrowers;
import com.example.code81.repository.MembersBorrowersRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MembersBorrowersService {

    private final MembersBorrowersRepo membersBorrowersRepo;

     public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_SUSPENDED = "SUSPENDED";
    public static final String STATUS_EXPIRED = "EXPIRED";

    public static final int DEFAULT_BORROW_LIMIT = 5;


      public List<MembersBorrowers> getAllMembers() {
        return membersBorrowersRepo.findAll();
    }

    public Optional<MembersBorrowers> getMemberById(Long id) {
        return membersBorrowersRepo.findById(id);
    }

    public Optional<MembersBorrowers> getMemberByEmail(String email) {
        return membersBorrowersRepo.findByEmail(email);
    }

    public List<MembersBorrowers> getMembersByStatus(String status) {
        return membersBorrowersRepo.findByMembershipStatus(status);
    }



     public MembersBorrowers createMember(MembersBorrowers member) {
        //first check if email already exists
        if (membersBorrowersRepo.findByEmail(member.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + member.getEmail());
        }
        
        // Set default values if not provided
        if (member.getMembershipStatus() == null) {
            member.setMembershipStatus(STATUS_ACTIVE);
        }
        
        if (member.getMaxBorrowLimit() == null) {
            member.setMaxBorrowLimit(DEFAULT_BORROW_LIMIT);
        }
        
        if (member.getCurrentBorrowCount() == null) {
            member.setCurrentBorrowCount(0);
        }
        
        return membersBorrowersRepo.save(member);
    }
    

    public MembersBorrowers updateMember(Long id, MembersBorrowers memberinfo) {
        return membersBorrowersRepo.findById(id).map(member -> {
            // Check if email is exist 
            if (!member.getEmail().equals(memberinfo.getEmail()) && 
                membersBorrowersRepo.findByEmail(memberinfo.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email already exists: " + memberinfo.getEmail());
            }
            
           member.setName(memberinfo.getName());
           member.setPhone(memberinfo.getPhone());
           member.setEmail(memberinfo.getEmail());
           member.setCreatprocessAt(memberinfo.getCreatprocessAt());
           member.setUpdateAt(memberinfo.getUpdateAt());
           member.setCurrentBorrowCount(memberinfo.getCurrentBorrowCount());
           member.setMaxBorrowLimit(memberinfo.getMaxBorrowLimit());
           member.setMembershipStatus(memberinfo.getMembershipStatus());

            
            return membersBorrowersRepo.save(member);
        }).orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
    }
    
    
    public void deleteMember(Long id) {
        // Check if member has borrowed books
        MembersBorrowers member = membersBorrowersRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
        
        if (member.getCurrentBorrowCount() > 0) {
            throw new IllegalStateException("Cannot delete member with borrowed books");
        }
        
        membersBorrowersRepo.deleteById(id);
    }


}
