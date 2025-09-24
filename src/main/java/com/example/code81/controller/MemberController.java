package com.example.code81.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.code81.entity.MembersBorrowers;
import com.example.code81.service.MembersBorrowersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MembersBorrowersService membersBorrowersService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('STAFF')")
    public ResponseEntity<List<MembersBorrowers>> getAllMembers() {
        List<MembersBorrowers> members = membersBorrowersService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('STAFF')")
    public ResponseEntity<MembersBorrowers> getMemberById(@PathVariable Long id) {
        Optional<MembersBorrowers> member = membersBorrowersService.getMemberById(id);
        return member.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('STAFF')")
    public ResponseEntity<MembersBorrowers> getMemberByEmail(@PathVariable String email) {
        Optional<MembersBorrowers> member = membersBorrowersService.getMemberByEmail(email);
        return member.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN') or hasRole('STAFF')")
    public ResponseEntity<List<MembersBorrowers>> getMembersByStatus(@PathVariable String status) {
        List<MembersBorrowers> members = membersBorrowersService.getMembersByStatus(status);
        return ResponseEntity.ok(members);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<MembersBorrowers> createMember(@RequestBody MembersBorrowers member) {
        try {
            MembersBorrowers createdMember = membersBorrowersService.createMember(member);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMember);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<MembersBorrowers> updateMember(@PathVariable Long id, @RequestBody MembersBorrowers member) throws IllegalArgumentException {
        try {
            MembersBorrowers updatedMember = membersBorrowersService.updateMember(id, member);
            return ResponseEntity.ok(updatedMember);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        try {
            membersBorrowersService.deleteMember(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
