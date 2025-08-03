package com.example.app.database.book.dao.impl;

import com.example.app.database.book.dao.BookDao;
import com.example.app.database.book.domain.Book;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class BookDaoImpl implements BookDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void create(Book book) {
        jdbcTemplate.update(
                "INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)",
                book.getIsbn(),
                book.getTitle(),
                book.getAuthorId()
        );
    }

    @Override
    public Optional<Book> findOne(String isbn) {
        List<Book> bookList = jdbcTemplate.query(
                "SELECT isbn, title, author_id from books WHERE isbn = ? LIMIT 1",
                new BookRowMapper(),
                isbn
        );
        return bookList.stream().findFirst();
    }

    @Override
    public List<Book> find() {
        return jdbcTemplate.query(
                "SELECT isbn, title, author_id from books",
                new BookRowMapper()
        );
    }

    @Override
    public void update(String isbn, Book book) {
        jdbcTemplate.update(
                "UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?",
                book.getIsbn(), book.getTitle(), book.getAuthorId(), isbn
        );
    }

    @Override
    public void delete(String isbn) {
        jdbcTemplate.update(
                "DELETE FROM books where isbn = ?",
                isbn
        );
    }

    public static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Book.builder()
                    .title(rs.getString("title"))
                    .isbn(rs.getString("isbn"))
                    .authorId(rs.getLong("author_id"))
                    .build();
        }
    }
}
