package com.example.app.database.book.dao.impl;

import com.example.app.database.book.TestDataUtils;
import com.example.app.database.book.domain.Author;
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
public class AuthorEntityDaoImplIntegrationTests {

    private final AuthorDaoImpl underTest;

    @Autowired
    public AuthorEntityDaoImplIntegrationTests(AuthorDaoImpl authorDao) {
        this.underTest = authorDao;
    }

    @Test
    public void testAuthorCreated() {
        Author author = TestDataUtils.createTestAuthorA();
        underTest.create(author);
        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testAuthorFindMany() {
        Author authorA = TestDataUtils.createTestAuthorA();
        underTest.create(authorA);
        Author authorB = TestDataUtils.createTestAuthorB();
        underTest.create(authorB);
        Author authorC = TestDataUtils.createTestAuthorC();
        underTest.create(authorC);

        List<Author> result = underTest.find();
        assertThat(result)
                .hasSize(3)
                .containsExactly(authorA, authorB, authorC);


    }

    @Test
    public void testUpdate() {
        Author authorA = TestDataUtils.createTestAuthorA();
        underTest.create(authorA);
        authorA.setName("UPDATED");
        underTest.update(authorA);
        Optional<Author> result = underTest.findOne(authorA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorA);
    }

    @Test
    public void testDelete() {
        Author authorA = TestDataUtils.createTestAuthorA();
        underTest.create(authorA);
        underTest.delete(authorA.getId());
        Optional<Author> result = underTest.findOne(authorA.getId());
        assertThat(result).isEmpty();
    }

}
