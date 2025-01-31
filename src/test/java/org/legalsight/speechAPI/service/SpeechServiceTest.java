package org.legalsight.speechAPI.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.legalsight.speechAPI.dto.SearchFilterDTO;
import org.legalsight.speechAPI.dto.SpeechDTO;
import org.legalsight.speechAPI.model.Speech;
import org.legalsight.speechAPI.repository.SpeechRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@Transactional
class SpeechServiceTest {

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @Autowired
    private SpeechService speechService;

    @Autowired
    private SpeechRepository speechRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");
    }

    @BeforeEach
    void setUp() {
        speechRepository.deleteAllInBatch();
        assertEquals(0, speechRepository.count()); // Ensure database is clean
    }

    @Test
    void testSaveSpeech() {
        SpeechDTO requestDTO = new SpeechDTO();
        requestDTO.setAuthor("John Doe");
        requestDTO.setSubject("Law and Order");
        requestDTO.setSpeechText("This is a test speech.");
        requestDTO.setSpeechDate(LocalDate.now());

        SpeechDTO savedSpeech = speechService.save(requestDTO);

        assertNotNull(savedSpeech);
        assertEquals("John Doe", savedSpeech.getAuthor());
        assertEquals("Law and Order", savedSpeech.getSubject());
    }

    @Test
    void testFindSpeechById() {
        Speech speech = new Speech();
        speech.setAuthor("Jane Doe");
        speech.setSubject("Legal Ethics");
        speech.setSpeechText("This is another test speech.");
        speech.setSpeechDate(LocalDate.now());
        speechRepository.saveAndFlush(speech);

        SpeechDTO foundSpeech = speechService.findSpeechById(speech.getId());

        assertNotNull(foundSpeech);
        assertEquals("Jane Doe", foundSpeech.getAuthor());
        assertEquals("Legal Ethics", foundSpeech.getSubject());
    }

    @Test
    void testUpdateSpeech() {
        Speech speech = new Speech();
        speech.setAuthor("Alice");
        speech.setSubject("Corporate Law");
        speech.setSpeechText("This is a test speech.");
        speech.setSpeechDate(LocalDate.now());
        speechRepository.saveAndFlush(speech);

        SpeechDTO updateDTO = new SpeechDTO();
        updateDTO.setAuthor("Alice Updated");
        updateDTO.setSubject("Updated Corporate Law");
        updateDTO.setSpeechText("Updated speech text.");
        updateDTO.setSpeechDate(LocalDate.now());

        SpeechDTO updatedSpeech = speechService.update(updateDTO, speech.getId());

        assertNotNull(updatedSpeech);
        assertEquals("Alice Updated", updatedSpeech.getAuthor());
        assertEquals("Updated Corporate Law", updatedSpeech.getSubject());
    }

    @Test
    void testDeleteSpeech() {
        Speech speech = new Speech();
        speech.setAuthor("Bob");
        speech.setSubject("Criminal Law");
        speech.setSpeechText("This is a test speech.");
        speech.setSpeechDate(LocalDate.now());
        speechRepository.saveAndFlush(speech);

        String result = speechService.delete(speech.getId());

        assertEquals("Speech deleted", result);
        assertFalse(speechRepository.existsById(speech.getId()));
    }

    @Test
    void testSearchSpeeches() {
        Speech speech1 = new Speech();
        speech1.setAuthor("John Doe");
        speech1.setSubject("Law and Order");
        speech1.setSpeechText("This is a test speech.");
        speech1.setSpeechDate(LocalDate.now());
        speechRepository.saveAndFlush(speech1);

        Speech speech2 = new Speech();
        speech2.setAuthor("Jane Doe");
        speech2.setSubject("Legal Ethics");
        speech2.setSpeechText("This is another test speech.");
        speech2.setSpeechDate(LocalDate.now());
        speechRepository.saveAndFlush(speech2);

        SearchFilterDTO filter = new SearchFilterDTO();
        Speech searchSpeech = new Speech();
        searchSpeech.setAuthor("John Doe");
        filter.setSpeech(searchSpeech);
        filter.setPage(0);
        filter.setSize(10);

        Page<SpeechDTO> result = speechService.search(filter);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("John Doe", result.getContent().get(0).getAuthor());
    }
}
