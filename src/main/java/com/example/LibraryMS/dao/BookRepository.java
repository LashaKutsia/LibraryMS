package com.example.LibraryMS.dao;

import com.example.LibraryMS.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitleContaining(String title);


}
