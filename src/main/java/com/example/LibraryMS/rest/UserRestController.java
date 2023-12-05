package com.example.LibraryMS.rest;

import com.example.LibraryMS.entity.User;
import com.example.LibraryMS.entity.Book;
import com.example.LibraryMS.service.BookService;
import com.example.LibraryMS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/LMS")
public class UserRestController {
    private UserService userService;

    private BookService bookService;


    @Autowired
    public  UserRestController(UserService theuserService, BookService thebookService ){userService = theuserService; bookService = thebookService;}

    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("hello from secured endpoint");
    }
    @GetMapping("/users")
    public List<User> findAll(){return userService.findAll();}

    @GetMapping("/users/{userId}")
    public  User getUser(@PathVariable int userId) {
        User theUser = userService.findById(userId);
        if (theUser == null) {
            throw new RuntimeException("User id not found - " + userId);
        }
        return theUser;
    }

    @PostMapping("/register")
    public User addUser(@RequestBody User theUser) {


        theUser.setId(0);

        User dbUser = userService.save(theUser);

        return dbUser;
    }

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable int userId) {

        User tempUser = userService.findById(userId);

        if (tempUser == null) {
            throw new RuntimeException("User id not found - " + userId);
        }

        userService.deleteById(userId);

        return "Deleted User id - " + userId;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooksByTitle(@RequestParam String title) {
        List<Book> books = bookService.searchBooksByTitle(title);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

@GetMapping("/books/{bookId}/borrowed")
public ResponseEntity<String> checkBookBorrowStatus(@PathVariable int bookId) {
    try {
        boolean isBorrowed = bookService.borrowBook(bookId);
        if (isBorrowed) {
            return new ResponseEntity<>("Book with ID " + bookId + " is borrowed.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book with ID " + bookId + " is available.", HttpStatus.OK);
        }
    } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestParam int bookId, @RequestParam Long userId) {
        try {
            bookService.returnBook(bookId, userId);
            return new ResponseEntity<>("Book returned successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/updateBookStatus/{bookId}")
    public ResponseEntity<String> updateBookStatus(@PathVariable int bookId) {
        try {
            bookService.updateBookStatus(bookId);
            return new ResponseEntity<>("Book status updated successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
