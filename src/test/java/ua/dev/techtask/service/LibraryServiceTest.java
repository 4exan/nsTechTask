package ua.dev.techtask.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import ua.dev.techtask.dto.BorrowDto;
import ua.dev.techtask.dto.BorrowedBookStat;
import ua.dev.techtask.entity.Book;
import ua.dev.techtask.entity.Borrow;
import ua.dev.techtask.entity.Member;
import ua.dev.techtask.exception.NoSuchBookException;
import ua.dev.techtask.exception.NoSuchBorrowException;
import ua.dev.techtask.exception.NoSuchMemberException;
import ua.dev.techtask.repository.BookRepository;
import ua.dev.techtask.repository.BorrowRepository;
import ua.dev.techtask.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTest {
  @Mock
  private BookRepository bookRepository;

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private BorrowRepository borrowRepository;

  @InjectMocks
  private LibraryService libraryService;

  @BeforeEach
  void setup() {
    ReflectionTestUtils.setField(libraryService, "borrowLimit", 10);
  }

  @Test
  void borrowBook_shouldBorrowSuccessfully() {
    BorrowDto dto = new BorrowDto(2L, 1L);
    Book book = new Book();
    book.setId(1L);
    book.setAmount(2);

    Member member = new Member();
    member.setId(2L);

    Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
    Mockito.when(memberRepository.findById(2L)).thenReturn(Optional.of(member));
    Mockito.when(borrowRepository.countByMemberIdAndReturnDateIsNull(2L)).thenReturn(1);

    libraryService.borrowBook(dto);

    Assertions.assertEquals(1, book.getAmount());
    Mockito.verify(bookRepository).save(book);
    Mockito.verify(borrowRepository).save(Mockito.any(Borrow.class));
  }

  @Test
  void borrowBook_whenBookNotFound_shouldThrow() {
    BorrowDto dto = new BorrowDto(2L, 1L);
    Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());

    Assertions.assertThrows(NoSuchBookException.class, () -> {
      libraryService.borrowBook(dto);
    });
  }

  @Test
  void borrowBook_whenBookAmountZero_shouldThrow() {
    BorrowDto dto = new BorrowDto(2L, 1L);
    Book book = new Book();
    book.setAmount(0);

    Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

    Assertions.assertThrows(IllegalStateException.class, () -> {
      libraryService.borrowBook(dto);
    });
  }

  @Test
  void borrowBook_whenMemberNotFound_shouldThrow() {
    BorrowDto dto = new BorrowDto(2L, 1L);
    Book book = new Book();
    book.setAmount(1);

    Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
    Mockito.when(memberRepository.findById(2L)).thenReturn(Optional.empty());

    Assertions.assertThrows(NoSuchMemberException.class, () -> {
      libraryService.borrowBook(dto);
    });
  }

  @Test
  void borrowBook_whenMemberHasTooManyBorrows_shouldThrow() {
    BorrowDto dto = new BorrowDto(2L, 1L);
    Book book = new Book();
    book.setAmount(2);
    book.setId(1L);

    Member member = new Member();
    member.setId(2L);

    Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
    Mockito.when(memberRepository.findById(2L)).thenReturn(Optional.of(member));
    Mockito.when(borrowRepository.countByMemberIdAndReturnDateIsNull(2L)).thenReturn(10);

    IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
      libraryService.borrowBook(dto);
    });

    Assertions.assertEquals("Cannot borrow more books", exception.getMessage());
  }

  @Test
  void returnBook_shouldProcessReturn() {
    BorrowDto dto = new BorrowDto(2L, 1L);
    Book book = new Book();
    book.setAmount(2);

    Borrow borrow = new Borrow();
    borrow.setBook(book);

    Mockito.when(borrowRepository.findByMemberIdAndBookIdAndReturnDateIsNull(2L, 1L))
        .thenReturn(Optional.of(borrow));

    libraryService.returnBook(dto);

    Assertions.assertEquals(3, book.getAmount());
    Mockito.verify(bookRepository).save(book);
    Assertions.assertNotNull(borrow.getReturnDate());
  }

  @Test
  void returnBook_whenNotFound_shouldThrow() {
    BorrowDto dto = new BorrowDto(2L, 1L);

    Mockito.when(borrowRepository.findByMemberIdAndBookIdAndReturnDateIsNull(2L, 1L))
        .thenReturn(Optional.empty());

    Assertions.assertThrows(NoSuchBorrowException.class, () -> {
      libraryService.returnBook(dto);
    });
  }

  @Test
  void getAllBorrowedBooksByMemberName_shouldReturnList() {
    List<Book> books = List.of(new Book(), new Book());

    Mockito.when(borrowRepository.findBooksBorrowedByMemberName("Ivan")).thenReturn(books);

    List<Book> result = libraryService.getAllBorrowedBooksByMemberName("Ivan");

    Assertions.assertEquals(2, result.size());
    Mockito.verify(borrowRepository).findBooksBorrowedByMemberName("Ivan");
  }

  @Test
  void getAllBorrowedBooksByMemberName_whenNull_shouldThrow() {
    Mockito.when(borrowRepository.findBooksBorrowedByMemberName("Ivan")).thenReturn(null);

    Assertions.assertThrows(RuntimeException.class, () -> {
      libraryService.getAllBorrowedBooksByMemberName("Ivan");
    });
  }

  @Test
  void findDistinctBorrowedBookTitles_shouldReturnList() {
    List<String> titles = List.of("Book1", "Book2");

    Mockito.when(borrowRepository.findDisctinctBorrowedBookTitles()).thenReturn(titles);

    List<String> result = libraryService.findDistinctBorrowedBookTitles();

    Assertions.assertEquals(2, result.size());
    Assertions.assertEquals("Book1", result.get(0));
    Mockito.verify(borrowRepository).findDisctinctBorrowedBookTitles();
  }

  @Test
  void findDistinctBorrowedBookTitles_whenNull_shouldThrow() {
    Mockito.when(borrowRepository.findDisctinctBorrowedBookTitles()).thenReturn(null);

    Assertions.assertThrows(RuntimeException.class, () -> {
      libraryService.findDistinctBorrowedBookTitles();
    });
  }

  @Test
  void findBorrowedBookStats_shouldReturnStats() {
    List<BorrowedBookStat> stats = List.of(
        new BorrowedBookStat("Book A", 3),
        new BorrowedBookStat("Book B", 2));

    Mockito.when(borrowRepository.findBookBorrowStats()).thenReturn(stats);

    List<BorrowedBookStat> result = libraryService.findBorrowedBookStats();

    Assertions.assertEquals(2, result.size());
    Assertions.assertEquals("Book A", result.get(0).getTitle());
    Mockito.verify(borrowRepository).findBookBorrowStats();
  }

  @Test
  void findBorrowedBookStats_whenNull_shouldThrow() {
    Mockito.when(borrowRepository.findBookBorrowStats()).thenReturn(null);

    Assertions.assertThrows(RuntimeException.class, () -> {
      libraryService.findBorrowedBookStats();
    });
  }
}
