package ua.dev.techtask.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.dev.techtask.dto.MemberDto;
import ua.dev.techtask.entity.Member;
import ua.dev.techtask.exception.NoSuchMemberException;
import ua.dev.techtask.repository.MemberRepository;

@Service
public class MemberService {

  @Autowired
  private MemberRepository memberRepository;

  public List<Member> getAllMembers() {
    return memberRepository.findAll();
  }

  public Member getMemberById(long id) {
    Member member = memberRepository.findById(id).orElseThrow(() -> new NoSuchMemberException());
    return member;
  }

  public void createNewMember(MemberDto req) {
    Member member = new Member();
    member.setName(req.getName());
    member.setMembershipDate(LocalDateTime.now());
    saveMemberToDB(member);
  }

  public void editMember(MemberDto req) {
    Member member = memberRepository.findById(req.getId()).orElseThrow(() -> new NoSuchMemberException());
    member.setName(req.getName());
    saveMemberToDB(member);
  }

  // TODO impl deletion logic
  public void deleteMember(long id) {
    memberRepository.deleteById(id);
  }

  private void saveMemberToDB(Member member) {
    try {
      memberRepository.save(member);
    } catch (Exception e) {
      throw new RuntimeException("Error occured when saving member to DB -> ", e);
    }
  }

}
