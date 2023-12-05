package com.example.LibraryMS.service;

import com.example.LibraryMS.entity.Book;

import java.util.List;

public interface BookService {
    boolean borrowBook(int bookId);

    void returnBook(int bookId, Long userId);

    List<Book> findAll();

    List<Book> searchBooksByTitle(String title);


    void updateBookStatus(int bookId);
}
