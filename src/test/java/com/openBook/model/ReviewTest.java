package com.openBook.model;

import com.openBook.test.config.builder.ReviewBuilder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {

    private static Validator validator;

    @BeforeAll
    public static void beforeAll() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testReviewIsValid() {
        Review review = new ReviewBuilder().build();
        Set<ConstraintViolation<Review>> constraintViolationSet = validator.validate(review);
        assertThat(constraintViolationSet.size()).isEqualTo(0);
    }

    @Test
    public void testParamConstructorAndGetters() {

        Review review = new Review(1, "Review Title", "Review Body", 5, 1);

        assertEquals(1, review.getId());
        assertEquals("Review Title", review.getTitle());
        assertEquals("Review Body", review.getReviewBody());
        assertEquals(5, review.getRating());
        assertEquals(1, review.getUserId());

        assertDoesNotThrow(() -> {
            new Review(1, "Review Title", "Review Body", 5, 1);
        });
    }

    @Test
    public void testDefaultConstructor() {
        Review review = new Review();
        assertNotNull(review);
        assertEquals(0, review.getId());
        assertNull(review.getTitle());
        assertNull(review.getReviewBody());
        assertEquals(0, review.getRating());
        assertEquals(0, review.getUserId());
    }

    @Test
    public void testEqualsAndHashCode() {

        Review review1 = new ReviewBuilder()
                .withId(1L)
                .withTitle("Review Title")
                .withReviewBody("Review Body")
                .withRating(5)
                .withUserId(1L)
                .build();

        Review review2 = new ReviewBuilder()
                .withId(1L)
                .withTitle("Review Title")
                .withReviewBody("Review Body")
                .withRating(5)
                .withUserId(1L)
                .build();

        assertEquals(review1, review2);
        assertEquals(review2.hashCode(), review1.hashCode());
    }

    @Test
    public void testNotEqualsDifferentId() {

        Review review1 = new ReviewBuilder()
                .withId(1L)
                .withTitle("Review Title")
                .withReviewBody("Review Body")
                .withRating(5)
                .withUserId(1L)
                .build();

        Review review2 = new ReviewBuilder()
                .withId(2L)
                .withTitle("Review Title")
                .withReviewBody("Review Body")
                .withRating(5)
                .withUserId(1L)
                .build();

        assertNotEquals(review1, review2);
        assertNotEquals(review2.hashCode(), review1.hashCode());
    }

}