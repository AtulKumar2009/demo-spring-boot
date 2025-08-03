package com.example.app.database.book.dao.impl;


import com.example.app.database.book.TestDataUtils;
import com.example.app.database.book.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorEntityDaoImplTests {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;

    @Test
    public void testCreateAuthorSql() {
        Author author = TestDataUtils.createTestAuthorA();
        underTest.create(author);
        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
                eq(1L),
                eq("Abigail Rose"),
                eq(80)
        );
    }

    @Test
    public void testFindOneSql() {
        Author author = TestDataUtils.createTestAuthorA();
        underTest.findOne(author.getId());
        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
                eq(1L)
        );
    }

    @Test
    public void testFindManySql() {
        underTest.find();
        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any()
        );
    }

    @Test
    public void testUpdateSql() {
        Author author = TestDataUtils.createTestAuthorA();
        underTest.update(author);

        verify(jdbcTemplate).update(
                "UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?",
                author.getId(),
                author.getName(),
                author.getAge(),
                author.getId()
        );
    }

    @Test
    public void testDeleteSql() {
        underTest.delete(1L);
        verify(jdbcTemplate).update(
                "DELETE FROM authors where id = ?",
                1L
        );
    }
}
