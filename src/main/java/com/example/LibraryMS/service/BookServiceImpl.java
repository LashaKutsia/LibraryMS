package com.example.LibraryMS.service;
import com.example.LibraryMS.exception.*;
import com.example.LibraryMS.dao.BookRepository;
import com.example.LibraryMS.entity.Book;
import com.example.LibraryMS.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserService userService;

//    @Override
//    public Book findBytitle(String title) {
//        List<Book> result = bookRepository.findByTitleContaining(title);
//
//        Book theBook = null;
//        if (result.equals(title)) {
//            theBook = result.;
//        }
//        else {
//            throw new RuntimeException("Did not find user id - " + theId);
//        }
//
//        return theBook;
//    }



    public void borrowBook(int bookId, int userId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Optional<User> optionalUser = Optional.ofNullable(userService.findById(userId));

        if (optionalBook.isPresent() && optionalUser.isPresent()) {
            Book book = optionalBook.get();

            if ("AVAILABLE".equals(book.getStatus())) {
                book.setStatus("BORROWED");
                book.setUser(optionalUser.get());
                bookRepository.save(book);
            } else {
                throw new BookNotAvailableException("Book is already borrowed.");
            }
        } else {
            throw new EntityNotFoundException("Book or User not found");
        }
    }

    @Override
    public boolean borrowBook(int bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);

        return book != null && "BORROWED".equals(book.getStatus());
    }

    public void returnBook(int bookId, Long userId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Optional<User> optionalUser = Optional.ofNullable(userService.findById(Math.toIntExact(userId)));

        if (optionalBook.isPresent() && optionalUser.isPresent()) {
            Book book = optionalBook.get();

            if ("BORROWED".equals(book.getStatus()) && userId.equals(book.getUser().getId())) {
                book.setStatus("AVAILABLE");
                book.setUser(null);
                bookRepository.save(book);
            } else {
                throw new InvalidBookReturnException("Invalid book return request.");
            }
        } else {
            throw new EntityNotFoundException("Book or User not found");
        }
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title);

    }

    @Override
    public void updateBookStatus(int bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (book.getUser() != null) {
                book.setStatus("BORROWED");
            } else {
                book.setStatus("AVAILABLE");
            }
            bookRepository.save(book);
        } else {
            throw new EntityNotFoundException("Book not found with ID: " + bookId);
        }
    }
}
