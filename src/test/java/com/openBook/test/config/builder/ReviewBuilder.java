package com.openBook.test.config.builder;

import com.openBook.model.Review;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class ReviewBuilder {

    private Long id;
    private String title;
    private String reviewBody;
    private int rating;
    private Long userId;

    public ReviewBuilder() {
        id = new Random().nextLong();
        title = RandomStringUtils.random(20, true, false);
        reviewBody = RandomStringUtils.randomAlphanumeric(200);
        rating = new Random().nextInt(6);
        userId = new Random().nextLong();
    }

    public Review build() {
        Review review = new Review();
        review.setId(id);
        review.setTitle(title);
        review.setReviewBody(reviewBody);
        review.setRating(rating);
        review.setUserId(userId);
        return review;
    }

    public ReviewBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ReviewBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public ReviewBuilder withReviewBody(String reviewBody) {
        this.reviewBody = reviewBody;
        return this;
    }

    public ReviewBuilder withRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public ReviewBuilder withUserId(Long userId) {
        this.userId = userId;
        return this;
    }
}
