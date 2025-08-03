package com.example.app.database.jpa.book.repositories;

import com.example.app.database.jpa.book.TestDataUtils;
import com.example.app.database.jpa.book.domain.entities.AuthorEntity;
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
public class AuthorEntityRepositoryIntegrationTests {

    private final AuthorRepository underTest;

    @Autowired
    public AuthorEntityRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtils.createTestAuthorEntityA();
        underTest.save(authorEntity);
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);
    }

    @Test
    public void testAuthorFindMany() {
        AuthorEntity authorEntityA = TestDataUtils.createTestAuthorEntityA();
        underTest.save(authorEntityA);
        AuthorEntity authorEntityB = TestDataUtils.createTestAuthorEntityB();
        underTest.save(authorEntityB);
        AuthorEntity authorEntityC = TestDataUtils.createTestAuthorEntityC();
        underTest.save(authorEntityC);

        Iterable<AuthorEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(authorEntityA, authorEntityB, authorEntityC);
    }

    @Test
    public void testUpdate() {
        AuthorEntity authorEntityA = TestDataUtils.createTestAuthorEntityA();
        underTest.save(authorEntityA);
        authorEntityA.setName("UPDATED");
        underTest.save(authorEntityA);
        Optional<AuthorEntity> result = underTest.findById(authorEntityA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntityA);
    }

    @Test
    public void testDelete() {
        AuthorEntity authorEntityA = TestDataUtils.createTestAuthorEntityA();
        underTest.save(authorEntityA);
        underTest.deleteById(authorEntityA.getId());
        Optional<AuthorEntity> result = underTest.findById(authorEntityA.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testGetAuthorsWithAgeLessThan() {
        AuthorEntity testAuthorEntityA = TestDataUtils.createTestAuthorEntityA();
        underTest.save(testAuthorEntityA);
        AuthorEntity testAuthorEntityB = TestDataUtils.createTestAuthorEntityB();
        underTest.save(testAuthorEntityB);
        AuthorEntity testAuthorEntityC = TestDataUtils.createTestAuthorEntityC();
        underTest.save(testAuthorEntityC);

        Iterable<AuthorEntity> result = underTest.ageLessThan(50);
        assertThat(result)
                .hasSize(2)
                .containsExactly(testAuthorEntityB, testAuthorEntityC);
    }
}