package ua.dev.techtask.service;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import net.bytebuddy.asm.Advice.Argument;
import ua.dev.techtask.dto.BookDto;
import ua.dev.techtask.dto.BorrowDto;
import ua.dev.techtask.entity.Book;
import ua.dev.techtask.entity.Borrow;
import ua.dev.techtask.repository.BookRepository;
import ua.dev.techtask.repository.BorrowRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

  @Mock
  private BookRepository bookRepository;

  @Mock
  private BorrowRepository borrowRepository;

  @InjectMocks
  private BookService bookService;

  @Test
  void testGetAllBooks() {

    Book book1 = new Book();
    book1.setId(1);
    book1.setTitle("Book test title");
    book1.setAuthor("Book Author");
    book1.setAmount(10);

    Book book2 = new Book();
    book2.setId(2);
    book2.setTitle("Second book test title");
    book2.setAuthor("Book Author");
    book2.setAmount(10);

    Mockito.when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

    List<Book> books = bookService.getAllBooks();
    Assertions.assertThat(books.getFirst().getTitle()).isEqualTo("Book test title");
    Assertions.assertThat(books.getLast().getId()).isEqualTo(2);
  }

  @Test
  void testCreateBook_whenBookExist_incrementAmount() {
    Book existinBook = new Book();
    existinBook.setTitle("Title");
    existinBook.setAuthor("Book Author");
    existinBook.setAmount(2);

    BookDto req = new BookDto("Title", "Bokk Author", 0);

    Mockito.when(bookRepository.findByTitleAndAuthor("Title", "Book Author")).thenReturn(Optional.of(existinBook));
    bookService.createBook(req);

    ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
    Mockito.verify(bookRepository.save(captor.capture()));

    Book savedBook = captor.getValue();
    Assertions.assertThat(savedBook.getAmount()).isEqualTo(3);
    Assertions.assertThat(savedBook.getTitle()).isEqualTo("Title");
  }

  @Test
  void testCreateBook_whenBookDoesNotExist_createNewWithGivenAmount() {
    BookDto req = new BookDto("New Title", "New Author", 5);
    Mockito.when(bookRepository.findByTitleAndAuthor("New Title", "New Author")).thenReturn(Optional.empty());

    bookService.createBook(req);

    ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
    Mockito.verify(bookRepository).save(captor.capture());

    Book savedBook = captor.getValue();
    Assertions.assertThat(savedBook.getTitle()).isEqualTo("New Title");
    Assertions.assertThat(savedBook.getAuthor()).isEqualTo("New Author");
    Assertions.assertThat(savedBook.getAmount()).isEqualTo(5);
  }

  @Test
  void testCreateBook_whenBookDoesNotExistAndAmountIsZero_setAmountTo1() {
    BookDto req = new BookDto("Zero Book", "Some Author", 0);
    Mockito.when(bookRepository.findByTitleAndAuthor("Zero book", "Some Author")).thenReturn(Optional.empty());
    bookService.createBook(req);
    ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
    Mockito.verify(bookRepository).save(captor.capture());

    Book savedBook = captor.getValue();
    Assertions.assertThat(savedBook.getAmount()).isEqualTo(1);
  }

  @Test
  void testEditBook() {
    BookDto req = new BookDto("New Title", "New Author", 5);
    req.setId(2);
    Book savedBook = new Book();
    savedBook.setId(2);
    savedBook.setTitle("Old Title");
    savedBook.setAuthor("Old Author");
    savedBook.setAmount(3);

    Mockito.when(bookRepository.findById(req.getId())).thenReturn(Optional.of(savedBook));

    bookService.editBook(req);
    ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
    Mockito.verify(bookRepository).save(captor.capture());

    Book editedBook = captor.getValue();

    Assertions.assertThat(editedBook.getId()).isEqualTo(2);
    Assertions.assertThat(editedBook.getTitle()).isEqualTo("New Title");
    Assertions.assertThat(editedBook.getAuthor()).isEqualTo("New Author");
    Assertions.assertThat(editedBook.getAmount()).isEqualTo(5);
  }
}
