package ua.dev.techtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.dev.techtask.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
    
}
