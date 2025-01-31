package org.legalsight.speechAPI.repository;

import org.legalsight.speechAPI.model.Speech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeechRepository extends JpaRepository<Speech, String>, JpaSpecificationExecutor<Speech> {
}