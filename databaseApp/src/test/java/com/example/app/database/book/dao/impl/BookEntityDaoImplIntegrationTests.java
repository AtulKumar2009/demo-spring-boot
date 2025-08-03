package com.example.app.database.book.dao.impl;

import com.example.app.database.book.TestDataUtils;
import com.example.app.database.book.dao.AuthorDao;
import com.example.app.database.book.domain.Author;
import com.example.app.database.book.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookEntityDaoImplIntegrationTests {

    private final BookDaoImpl underTest;

    private final AuthorDao authorDao;

    @Autowired
    public BookEntityDaoImplIntegrationTests(
            BookDaoImpl bookDao,
            AuthorDao authorDao
    ) {
        this.underTest = bookDao;
        this.authorDao = authorDao;
    }

    @Test
    public void testBookCreated() {
        Author author = TestDataUtils.createTestAuthorA();
        authorDao.create(author);

        Book book = TestDataUtils.createTestBookA();
        book.setAuthorId(author.getId());

        underTest.create(book);
        Optional<Book> result = underTest.findOne(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testBookFind() {
        Author author = TestDataUtils.createTestAuthorA();
        authorDao.create(author);

        Book bookA = TestDataUtils.createTestBookA();
        bookA.setAuthorId(author.getId());
        underTest.create(bookA);
        Book bookB = TestDataUtils.createTestBookB();
        bookB.setAuthorId(author.getId());
        underTest.create(bookB);
        Book bookC = TestDataUtils.createTestBookC();
        bookC.setAuthorId(author.getId());
        underTest.create(bookC);
        List<Book> result = underTest.find();
        assertThat(result)
                .hasSize(3)
                .containsExactly(bookA, bookB, bookC);
    }

    @Test
    public void testUpdated() {
        Author author = TestDataUtils.createTestAuthorA();
        authorDao.create(author);

        Book bookA = TestDataUtils.createTestBookA();
        bookA.setAuthorId(author.getId());
        underTest.create(bookA);

        bookA.setTitle("UPDATED");
        underTest.update(bookA.getIsbn(), bookA);

        Optional<Book> result = underTest.findOne(bookA.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookA);
    }

    @Test
    public void testDeleted() {
        Author author = TestDataUtils.createTestAuthorA();
        authorDao.create(author);

        Book bookA = TestDataUtils.createTestBookA();
        bookA.setAuthorId(author.getId());
        underTest.create(bookA);

        underTest.delete(bookA.getIsbn());

        Optional<Book> result = underTest.findOne(bookA.getIsbn());
        assertThat(result).isEmpty();
    }

}
