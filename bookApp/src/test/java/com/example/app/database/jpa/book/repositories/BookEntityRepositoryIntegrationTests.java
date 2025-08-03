package com.example.app.database.jpa.book.repositories;

import com.example.app.database.jpa.book.TestDataUtils;
import com.example.app.database.jpa.book.domain.entities.AuthorEntity;
import com.example.app.database.jpa.book.domain.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookEntityRepositoryIntegrationTests {

    private final BookRepository underTest;

    private final AuthorRepository authorRepository;

    @Autowired
    public BookEntityRepositoryIntegrationTests(
            BookRepository bookRepository,
            AuthorRepository authorRepository
    ) {
        this.underTest = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Test
    public void testBookCreated() {
        AuthorEntity authorEntity = TestDataUtils.createTestAuthorEntityA();
        BookEntity bookEntity = TestDataUtils.createTestBookEntityA(authorEntity);
        underTest.save(bookEntity);
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get())
                .usingRecursiveComparison()
                .ignoringFields("author.id")
                .isEqualTo(bookEntity);
    }

    @Test
    public void testBookFind() {
        AuthorEntity authorEntity = TestDataUtils.createTestAuthorEntityA();
        AuthorEntity savedAuthorEntity = authorRepository.save(authorEntity);
        BookEntity bookEntityA = TestDataUtils.createTestBookEntityA(savedAuthorEntity);
        underTest.save(bookEntityA);
        BookEntity bookEntityB = TestDataUtils.createTestBookEntityB(savedAuthorEntity);
        underTest.save(bookEntityB);
        BookEntity bookEntityC = TestDataUtils.createTestBookEntityC(savedAuthorEntity);
        underTest.save(bookEntityC);
        Iterable<BookEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(bookEntityA, bookEntityB, bookEntityC);
    }

    @Test
    public void testUpdated() {
        AuthorEntity authorEntity = TestDataUtils.createTestAuthorEntityA();
        AuthorEntity savedAuthorEntity = authorRepository.save(authorEntity);

        BookEntity bookEntityA = TestDataUtils.createTestBookEntityA(savedAuthorEntity);
        underTest.save(bookEntityA);

        bookEntityA.setTitle("UPDATED");
        underTest.save(bookEntityA);

        Optional<BookEntity> result = underTest.findById(bookEntityA.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntityA);
    }

    @Test
    public void testDeleted() {
        AuthorEntity authorEntity = TestDataUtils.createTestAuthorEntityA();
        AuthorEntity savedAuthorEntity = authorRepository.save(authorEntity);
        BookEntity bookEntityA = TestDataUtils.createTestBookEntityA(savedAuthorEntity);
        underTest.save(bookEntityA);
        underTest.delete(bookEntityA);
        Optional<BookEntity> result = underTest.findById(bookEntityA.getIsbn());
        assertThat(result).isEmpty();
    }

}