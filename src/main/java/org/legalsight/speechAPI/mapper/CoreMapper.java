package org.legalsight.speechAPI.mapper;

import org.legalsight.speechAPI.dto.CoreDTO;
import org.legalsight.speechAPI.model.CoreEntity;

public interface CoreMapper<Entity extends CoreEntity, DTO extends CoreDTO> {

  Entity toEntity(DTO dto);

  DTO toDTO(Entity entity);
}
