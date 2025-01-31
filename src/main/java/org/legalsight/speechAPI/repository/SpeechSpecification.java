package org.legalsight.speechAPI.repository;

import org.legalsight.speechAPI.model.Speech;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class SpeechSpecification {
    public static Specification<Speech> hasAuthor(String author) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("author"), author);
    }

    public static Specification<Speech> hasSubject(String subject) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("subject"), subject);
    }

    public static Specification<Speech> hasDate(LocalDate dateFrom, LocalDate dateTo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("speechDate"), dateFrom, dateTo);
    }

    public static Specification<Speech> hasText(String speechText) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("speechText"), "%" + speechText + "%");
    }
}