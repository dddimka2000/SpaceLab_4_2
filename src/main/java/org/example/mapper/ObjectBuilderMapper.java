package org.example.mapper;

import org.example.dto.ObjectBuilderDto;
import org.example.dto.ObjectBuilderDtoEdit;
import org.example.entity.BuilderObject;
import org.mapstruct.Mapper;

@Mapper
public interface  ObjectBuilderMapper {
    ObjectBuilderDto builderObjectToBuilderObjectDto(BuilderObject builderObject);
    BuilderObject objectBuilderDtoToBuilderObject(ObjectBuilderDto objectBuilderDto);

    ObjectBuilderDtoEdit builderObjectToBuilderObjectDtoEdit(BuilderObject builderObject);
    BuilderObject objectBuilderDtoEditToBuilderObject(ObjectBuilderDtoEdit objectBuilderDto);
}
