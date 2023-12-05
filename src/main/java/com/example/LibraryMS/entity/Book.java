package com.example.LibraryMS.entity;

import jakarta.persistence.*;

//import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {

    public Book() {
    }
    public Book(String title, String author, int isbn, String status) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="title")
    private String title;

    @Column(name="author")
    private  String author;

    @Column(name="isbn")
    private  int isbn;

    @Column(name="status")
    private String status;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn=" + isbn +
                ", status='" + status + '\'' +
                ", user=" + user +
                '}';
    }


}
