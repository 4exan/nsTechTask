package ua.dev.techtask.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.dev.techtask.dto.MemberDto;
import ua.dev.techtask.entity.Member;
import ua.dev.techtask.exception.NoSuchMemberException;
import ua.dev.techtask.repository.BorrowRepository;
import ua.dev.techtask.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private BorrowRepository borrowRepository;

  @InjectMocks
  private MemberService memberService;

  @Test
  void getAllMembers_shouldReturnList() {
    List<Member> members = List.of(new Member(), new Member());
    Mockito.when(memberRepository.findAll()).thenReturn(members);

    List<Member> result = memberService.getAllMembers();

    Assertions.assertEquals(2, result.size());
    Mockito.verify(memberRepository).findAll();
  }

  @Test
  void getMemberById_whenFound_shouldReturnMember() {
    Member member = new Member();
    Mockito.when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

    Member result = memberService.getMemberById(1L);

    Assertions.assertEquals(member, result);
    Mockito.verify(memberRepository).findById(1L);
  }

  @Test
  void getMemberById_whenNotFound_shouldThrow() {
    Mockito.when(memberRepository.findById(1L)).thenReturn(Optional.empty());

    Assertions.assertThrows(NoSuchMemberException.class,
        () -> memberService.getMemberById(1L));
  }

  @Test
  void createNewMember_shouldSaveWithCurrentDate() {
    MemberDto dto = new MemberDto("Ivan");

    ArgumentCaptor<Member> captor = ArgumentCaptor.forClass(Member.class);

    memberService.createNewMember(dto);

    Mockito.verify(memberRepository).save(captor.capture());

    Member saved = captor.getValue();
    Assertions.assertEquals("Ivan", saved.getName());
    Assertions.assertNotNull(saved.getMembershipDate());
  }

  @Test
  void editMember_shouldUpdateNameAndSave() {
    Member existing = new Member();
    existing.setName("Old");

    Mockito.when(memberRepository.findById(5L)).thenReturn(Optional.of(existing));

    MemberDto dto = new MemberDto("New");

    memberService.editMember(5L, dto);

    Assertions.assertEquals("New", existing.getName());
    Mockito.verify(memberRepository).save(existing);
  }

  @Test
  void editMember_whenNotFound_shouldThrow() {
    Mockito.when(memberRepository.findById(5L)).thenReturn(Optional.empty());

    Assertions.assertThrows(NoSuchMemberException.class,
        () -> memberService.editMember(5L, new MemberDto("Name")));
  }

  @Test
  void deleteMember_whenHasBorrowedBooks_shouldThrow() {
    Mockito.when(borrowRepository.countByMemberIdAndReturnDateIsNull(10L)).thenReturn(2);

    IllegalStateException ex = Assertions.assertThrows(IllegalStateException.class,
        () -> memberService.deleteMember(10L));

    Assertions.assertEquals("Cannot delete member with borrowed books", ex.getMessage());
  }

  @Test
  void deleteMember_whenNoBorrows_shouldDelete() {
    Mockito.when(borrowRepository.countByMemberIdAndReturnDateIsNull(10L)).thenReturn(0);

    memberService.deleteMember(10L);

    Mockito.verify(memberRepository).deleteById(10L);
  }
}
