package org.legalsight.speechAPI.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.legalsight.speechAPI.dto.SearchFilterDTO;
import org.legalsight.speechAPI.dto.SpeechDTO;
import org.legalsight.speechAPI.error.SpeechNotFoundException;
import org.legalsight.speechAPI.mapper.SpeechMapper;
import org.legalsight.speechAPI.model.Speech;
import org.legalsight.speechAPI.repository.SpeechRepository;
import org.legalsight.speechAPI.repository.SpeechSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional(readOnly = true)
public class SpeechService {

    private final SpeechRepository speechRepository;
    private final SpeechMapper speechMapper;

    @Transactional
    public SpeechDTO save(SpeechDTO SpeechDTO) {
        log.debug("Creating speech `{}`", SpeechDTO);
        Speech speech = speechMapper.toEntity(SpeechDTO);
        speechRepository.save(speech);
        return SpeechDTO;
    }

    public SpeechDTO findSpeechById(String id) {
        log.debug("Finding speech by id `{}`", id);
        Speech speech = speechRepository.findById(id)
                .orElseThrow(() -> new SpeechNotFoundException("Not found", HttpStatus.NOT_FOUND.value()));
        return speechMapper.toDTO(speech);
    }

    @Transactional
    public SpeechDTO update(SpeechDTO speechDTO, String id) {
        log.debug("Updating speech `{}`", id);
        Speech speech = speechMapper.fromSpeechRequest(speechDTO);
        speech.setId(id);
        speechRepository.save(speech);
        return speechDTO;
    }

    @Transactional
    public String delete(String id) {
        if (speechRepository.existsById(id)) {
            log.debug("Deleting speech with id `{}`", id);
            speechRepository.deleteById(id);
            return "Speech deleted";
        }
        log.debug("Speech with id `{}` does not exist", id);
        return "Speech not found";
    }

    public Page<SpeechDTO> search(SearchFilterDTO searchFilterDTO) {
        log.debug("Searching speeches with filter `{}`", searchFilterDTO);

        Pageable pageable = PageRequest.of(searchFilterDTO.getPage(), searchFilterDTO.getSize());
        Speech speech = searchFilterDTO.getSpeech();

        Specification<Speech> spec = Specification.where(null);

        if (speech.getAuthor() != null) {
            spec = spec.and(SpeechSpecification.hasAuthor(speech.getAuthor()));
        }
        if (speech.getSubject() != null) {
            spec = spec.and(SpeechSpecification.hasSubject(speech.getSubject()));
        }
        if (searchFilterDTO.getDateFrom() != null && searchFilterDTO.getDateTo() != null) {
            spec = spec.and(
                    SpeechSpecification.hasDate(searchFilterDTO.getDateFrom(), searchFilterDTO.getDateTo()));
        }
        if (speech.getSpeechText() != null) {
            spec = spec.and(SpeechSpecification.hasText(speech.getSpeechText()));
        }

        Page<Speech> speechPage = speechRepository.findAll(spec, pageable);
        List<SpeechDTO> speechDTOs = speechPage.getContent().stream()
                .map(speechMapper::toDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(speechDTOs, pageable, speechPage.getTotalElements());
    }
}