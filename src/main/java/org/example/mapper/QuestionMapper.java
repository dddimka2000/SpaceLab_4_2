package org.example.mapper;

import org.example.dto.BranchDto;
import org.example.dto.QuestionDto;
import org.example.entity.Branch;
import org.example.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    void updateEntityFromDto(QuestionDto questionDto, @MappingTarget Question question);
}
