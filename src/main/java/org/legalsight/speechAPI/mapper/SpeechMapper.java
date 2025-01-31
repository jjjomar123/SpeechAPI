package org.legalsight.speechAPI.mapper;

import org.legalsight.speechAPI.dto.SpeechDTO;
import org.legalsight.speechAPI.model.Speech;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpeechMapper extends CoreMapper<Speech, SpeechDTO> {
}
