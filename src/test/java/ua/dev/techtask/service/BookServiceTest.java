package ua.dev.techtask.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.dev.techtask.dto.BookDto;
import ua.dev.techtask.entity.Book;
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
  void getAllBooks_returnListOfBooks() {

    List<Book> books = List.of(new Book(), new Book());

    Mockito.when(bookRepository.findAll()).thenReturn(books);

    List<Book> result = bookService.getAllBooks();

    Assertions.assertEquals(2, result.size());
  }

  @Test
  void createBook_whenBookExists_shouldIncrementAmount() {
    Book existingBook = new Book();
    existingBook.setAmount(2);
    existingBook.setTitle("Title");
    existingBook.setAuthor("Author");

    BookDto dto = new BookDto("Title", "Author", 0);

    Mockito.when(bookRepository.findByTitleAndAuthor("Title", "Author"))
        .thenReturn(Optional.of(existingBook));

    bookService.createBook(dto);

    Assertions.assertEquals(3, existingBook.getAmount());
    Mockito.verify(bookRepository).save(existingBook);
  }

  @Test
  void createBook_whenBookNotExists_shouldCreateNewWithDefaultAmount() {
    BookDto dto = new BookDto("New Title", "New Author", 0);

    Mockito.when(bookRepository.findByTitleAndAuthor("New Title", "New Author"))
        .thenReturn(Optional.empty());

    ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);

    bookService.createBook(dto);

    Mockito.verify(bookRepository).save(captor.capture());
    Book savedBook = captor.getValue();

    Assertions.assertEquals("New Title", savedBook.getTitle());
    Assertions.assertEquals("New Author", savedBook.getAuthor());
    Assertions.assertEquals(1, savedBook.getAmount());
  }

  @Test
  void createBook_whenAmountProvided_shouldUseProvidedAmount() {
    BookDto dto = new BookDto("Another", "Author", 5);

    Mockito.when(bookRepository.findByTitleAndAuthor("Another", "Author"))
        .thenReturn(Optional.empty());

    bookService.createBook(dto);

    ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
    Mockito.verify(bookRepository).save(captor.capture());
    Assertions.assertEquals(5, captor.getValue().getAmount());
  }

  @Test
  void editBook_shouldUpdateBookFields() {
    Book existing = new Book();
    existing.setId(1L);
    BookDto dto = new BookDto("Updated", "Updated Author", 7);

    Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));

    bookService.editBook(1L, dto);

    Assertions.assertEquals("Updated", existing.getTitle());
    Assertions.assertEquals("Updated Author", existing.getAuthor());
    Assertions.assertEquals(7, existing.getAmount());
    Mockito.verify(bookRepository).save(existing);
  }

  @Test
  void editBook_whenNotFound_shouldThrowException() {
    Mockito.when(bookRepository.findById(100L)).thenReturn(Optional.empty());

    BookDto dto = new BookDto("Title", "Author", 3);

    Assertions.assertThrows(RuntimeException.class,
        () -> bookService.editBook(100L, dto));
  }

  @Test
  void deleteBook_whenBorrowed_shouldThrowException() {
    Mockito.when(borrowRepository.countByBookIdAndReturnDateIsNull(5L)).thenReturn(1);

    IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class,
        () -> bookService.deleteBook(5L));

    Assertions.assertEquals("Cannot delete book with active borrows", exception.getMessage());
  }

  @Test
  void deleteBook_whenNotBorrowed_shouldDelete() {
    Mockito.when(borrowRepository.countByBookIdAndReturnDateIsNull(5L)).thenReturn(0);

    bookService.deleteBook(5L);

    Mockito.verify(bookRepository).deleteById(5L);
  }
}
