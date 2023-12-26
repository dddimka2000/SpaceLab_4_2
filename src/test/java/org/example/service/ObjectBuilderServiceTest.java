package org.example.service;
import static org.mockito.ArgumentMatchers.anyString;

import static org.junit.jupiter.api.Assertions.*;

import lombok.SneakyThrows;
import org.example.dto.LayoutDTO;
import org.example.dto.LayoutDTOEdit;
import org.example.dto.ObjectBuilderDto;
import org.example.dto.ObjectBuilderDtoEdit;
import org.example.entity.BuilderObject;
import org.example.entity.property.type.TypeObject;
import org.example.repository.BuilderObjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ObjectBuilderServiceTest {

    @Mock
    private BuilderObjectRepository builderObjectRepository;

    @InjectMocks
    private ObjectBuilderService objectBuilderService;
    @Mock
    private MinioService minioService;

    @Mock
    private LayoutService layoutService;
    @Mock
    private ImagesForObjectService imagesForObjectService;
    @SneakyThrows
    @Test
    void testSaveCreate() {
        // Mock data
        ObjectBuilderDto objectBuilderDto = createMockObjectBuilderDto();
        BuilderObject builderObject = createMockBuilderObject();

        // Mock MultipartFile for prices
        MockMultipartFile pricesFile = Mockito.mock(MockMultipartFile.class);
        objectBuilderDto.setPrices(pricesFile);

        // Mock MultipartFile for chessboardFile
        MockMultipartFile chessboardFile = Mockito.mock(MockMultipartFile.class);
        objectBuilderDto.setChessboardFile(chessboardFile);

        // Mock MultipartFile for installmentTerms
        MockMultipartFile installmentTermsFile = Mockito.mock(MockMultipartFile.class);
        objectBuilderDto.setInstallmentTerms(installmentTermsFile);

        // Mock LayoutDTO
        LayoutDTO layoutDTO = createMockLayoutDTO();
        objectBuilderDto.setLayoutDTOList(Collections.singletonList(layoutDTO));
        MockMultipartFile multipartFile = Mockito.mock(MockMultipartFile.class);
        layoutDTO.setImg1Layout(multipartFile);
        layoutDTO.setImg2Layout(multipartFile);
        layoutDTO.setImg3Layout(multipartFile);

// ...

        when(pricesFile.getOriginalFilename()).thenReturn("yourDesiredString");
        when(chessboardFile.getOriginalFilename()).thenReturn("yourDesiredString");
        when(installmentTermsFile.getOriginalFilename()).thenReturn("yourDesiredString");


        // Mock MinioService interactions
//        doNothing().when(minioService.putMultipartFile(multipartFile, anyString(), anyString()));
        Mockito.doNothing().when(minioService).saveFilesInMinIO(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyString(), Mockito.any());

        // Call the method
        objectBuilderService.saveCreate(objectBuilderDto, builderObject);

        // Add assertions based on the expected behavior
        Mockito.verify(minioService, Mockito.times(1)).saveFilesInMinIO(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyString(), Mockito.any());
        Mockito.verify(layoutService, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(builderObjectRepository, Mockito.times(1)).save(Mockito.any());
    }
    @SneakyThrows
    @Test
    void testSaveEdit() {
        // Mock data
        ObjectBuilderDtoEdit objectBuilderDtoEdit = new ObjectBuilderDtoEdit();
        BuilderObject builderObject = createMockBuilderObject();
        Integer id = 1;

        // Mock MultipartFile for prices
        MockMultipartFile pricesFile = Mockito.mock(MockMultipartFile.class);
        objectBuilderDtoEdit.setPrices(Optional.of(pricesFile));

        // Mock MultipartFile for chessboardFile
        MockMultipartFile chessboardFile = Mockito.mock(MockMultipartFile.class);
        objectBuilderDtoEdit.setChessboardFile(Optional.of(chessboardFile));

        // Mock MultipartFile for installmentTerms
        MockMultipartFile installmentTermsFile = Mockito.mock(MockMultipartFile.class);
        objectBuilderDtoEdit.setInstallmentTerms(Optional.of(installmentTermsFile));

        // Mock LayoutDTOEdit
        LayoutDTOEdit layoutDTOEdit = new LayoutDTOEdit();
        objectBuilderDtoEdit.setLayoutDTOList(Collections.singletonList(layoutDTOEdit));
        MockMultipartFile multipartFile = Mockito.mock(MockMultipartFile.class);
        layoutDTOEdit.setImg1Layout(Optional.of(multipartFile));
        layoutDTOEdit.setImg2Layout(Optional.of(multipartFile));
        layoutDTOEdit.setImg3Layout(Optional.of(multipartFile));

        // Mock MinioService interactions
        Mockito.doNothing().when(minioService).putMultipartFile(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.doNothing().when(minioService).saveFilesInMinIO(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyString(), Mockito.any());

        // Call the method
        objectBuilderService.saveEdit(objectBuilderDtoEdit, builderObject, id);

        // Add assertions based on the expected behavior
        Mockito.verify(minioService, Mockito.times(1)).saveFilesInMinIO(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyString(), Mockito.any());
        Mockito.verify(layoutService, Mockito.times(1)).deleteAllByBuilderObject(builderObject);
        Mockito.verify(layoutService, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(imagesForObjectService, Mockito.times(1)).findByIdObjectAndTypeObject(id, TypeObject.BY_BUILDER);
    }

    private ObjectBuilderDto createMockObjectBuilderDto() {
        // Implement mock data for ObjectBuilderDto
        return new ObjectBuilderDto();
    }

    private BuilderObject createMockBuilderObject() {
        // Implement mock data for BuilderObject
        return new BuilderObject();
    }

    private LayoutDTO createMockLayoutDTO() {
        // Implement mock data for LayoutDTO
        return new LayoutDTO();
    }
    @Test
    void count() {
        // Arrange
        when(builderObjectRepository.count()).thenReturn(10L);

        // Act
        long result = objectBuilderService.count();

        // Assert
        assertEquals(10L, result);
        verify(builderObjectRepository, times(1)).count();
    }

    @Test
    void findById() {
        // Arrange
        Integer id = 1;
        BuilderObject expectedEntity = new BuilderObject();
        when(builderObjectRepository.findById(id)).thenReturn(Optional.of(expectedEntity));

        // Act
        Optional<BuilderObject> result = objectBuilderService.findById(id);

        // Assert
        assertEquals(Optional.of(expectedEntity), result);
        verify(builderObjectRepository, times(1)).findById(id);
    }



    @Test
    void findByName() {
        // Arrange
        String name = "Example";
        BuilderObject expectedEntity = new BuilderObject();
        when(builderObjectRepository.findByName(name)).thenReturn(Optional.of(expectedEntity));

        // Act
        Optional<BuilderObject> result = objectBuilderService.findByName(name);

        // Assert
        assertEquals(Optional.of(expectedEntity), result);
        verify(builderObjectRepository, times(1)).findByName(name);
    }

    @Test
    void findByNameEnglish() {
        // Arrange
        String name = "Example";
        BuilderObject expectedEntity = new BuilderObject();
        when(builderObjectRepository.findByNameEnglish(name)).thenReturn(Optional.of(expectedEntity));

        // Act
        Optional<BuilderObject> result = objectBuilderService.findByNameEnglish(name);

        // Assert
        assertEquals(Optional.of(expectedEntity), result);
        verify(builderObjectRepository, times(1)).findByNameEnglish(name);
    }

    @Test
    void findByNameUkraine() {
        // Arrange
        String name = "Example";
        BuilderObject expectedEntity = new BuilderObject();
        when(builderObjectRepository.findByNameUkraine(name)).thenReturn(Optional.of(expectedEntity));

        // Act
        Optional<BuilderObject> result = objectBuilderService.findByNameUkraine(name);

        // Assert
        assertEquals(Optional.of(expectedEntity), result);
        verify(builderObjectRepository, times(1)).findByNameUkraine(name);
    }

    @Test
    void save() {
        // Arrange
        BuilderObject entity = new BuilderObject();

        // Act
        objectBuilderService.save(entity);

        // Assert
        verify(builderObjectRepository, times(1)).save(entity);
    }

    @Test
    void deleteById() {
        // Arrange
        Integer id = 1;
        BuilderObject builderObject = new BuilderObject();
        when(builderObjectRepository.findById(id)).thenReturn(Optional.of(builderObject));

        // Act
        objectBuilderService.deleteById(id);

        // Assert
        verify(builderObjectRepository, times(1)).delete(builderObject);
    }
    @Test
    void testFindBuilderObjectsByCriteria() {
        // Arrange
        String name = "BuilderObjectName";
        String district = "DistrictName";
        String zone = "ZoneName";
        String street = "StreetName";
        Integer floorQuantity = 5;
        Integer minPrice = 100000;
        PageRequest pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id")));
        List<BuilderObject> builderObjects = new ArrayList<>();
        Page<BuilderObject> expectedPage = new PageImpl<>(builderObjects);

        when(builderObjectRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<BuilderObject> result = objectBuilderService.findBuilderObjectsByCriteria(
                name, district, zone, street, floorQuantity, minPrice, pageable);

        // Assert
        assertEquals(expectedPage, result);
        verify(builderObjectRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void testFindBuilderObjectsPage() {
        // Arrange
        Integer pageNumber = 0;
        Integer pageSize = 10;
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        List<BuilderObject> builderObjects = new ArrayList<>();
        Page<BuilderObject> expectedPage = new PageImpl<>(builderObjects);

        when(builderObjectRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<BuilderObject> result = objectBuilderService.findBuilderObjectsPage(pageNumber, pageSize);

        // Assert
        assertEquals(expectedPage, result);
        verify(builderObjectRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testForSelect() {
        // Arrange
        String name = "BuilderObjectName";
        PageRequest pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id")));
        List<BuilderObject> builderObjects = new ArrayList<>();
        Page<BuilderObject> expectedPage = new PageImpl<>(builderObjects);

        when(builderObjectRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(expectedPage);

        // Act
        Page<BuilderObject> result = objectBuilderService.forSelect(name, pageable);

        // Assert
        assertEquals(expectedPage, result);
        verify(builderObjectRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }



}