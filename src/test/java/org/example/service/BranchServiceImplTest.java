package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import org.example.dto.BranchDto;
import org.example.entity.Branch;
import org.example.mapper.BranchMapper;
import org.example.repository.BranchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

// Add appropriate annotations for your testing framework
@ExtendWith(MockitoExtension.class)

public class BranchServiceImplTest {

    @Mock
    private MinioService minioService;

    @Mock
    private BranchMapper branchMapper;

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private BranchServiceImpl branchService;

    @Test
    void testAdd() throws Exception {
        // Arrange
        BranchDto branchDto = new BranchDto();
        Branch branch = new Branch();
        when(branchMapper.toEntity(branchDto, minioService)).thenReturn(branch);
        when(branchRepository.save(branch)).thenReturn(branch);

        // Act
        branchService.add(branchDto);

        // Assert
        verify(branchMapper, times(1)).toEntity(branchDto, minioService);
        verify(branchRepository, times(1)).save(branch);
    }
    @Test
    void testAddWithId() throws Exception {
        // Arrange
        BranchDto branchDto = new BranchDto();
        branchDto.setId(1);
        Branch branch = new Branch();
        branch.setId(1);
        lenient().when(branchMapper.toEntity(branchDto, minioService)).thenReturn(branch);
        when(branchRepository.save(branch)).thenReturn(branch);
        when(branchRepository.findById(1)).thenReturn(Optional.of(branch));
        doNothing().when(branchMapper).updateEntityFromDto(eq(branchDto), eq(branch), any());


        // Act
        branchService.add(branchDto);

        // Assert
        verify(branchMapper, times(1)).updateEntityFromDto(eq(branchDto), eq(branch), any());
        verify(branchRepository, times(1)).save(branch);
    }

    @Test
    void testCount() {
        // Arrange
        when(branchRepository.count()).thenReturn(10L);

        // Act
        long result = branchService.count();

        // Assert
        assertEquals(10L, result);
        verify(branchRepository, times(1)).count();
    }

    @Test
    void testGetById() {
        // Arrange
        Branch branch = new Branch();
        when(branchRepository.findById(1)).thenReturn(java.util.Optional.of(branch));

        // Act
        Branch result = branchService.getById(1);

        // Assert
        assertNotNull(result);
        verify(branchRepository, times(1)).findById(1);
    }

    @Test
    void testGetAll() {
        // Arrange
        PageRequest pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id")));

        when(branchRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(Page.empty());

        // Act
        Page<Branch> result = branchService.getAll(0, "code", "name", "address");

        // Assert
        assertNotNull(result);
        verify(branchRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void testForSelect() {
        // Arrange
        PageRequest pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id")));
        String name = "name";

        // Use any() for the Specification argument during stubbing
        when(branchRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(Page.empty());

        // Act
        Page<Branch> result = branchService.forSelect(name, pageable);

        // Assert
        assertNotNull(result);
        verify(branchRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void testDeleteById() {
        // Act
        branchService.deleteById(1);

        // Assert
        verify(branchRepository, times(1)).deleteById(1);
    }

    @Test
    void testSave() {
        // Arrange
        Branch branch = new Branch();

        // Act
        branchService.save(branch);

        // Assert
        verify(branchRepository, times(1)).save(branch);
    }

    @Test
    void testCountByCode() {
        // Arrange
        when(branchRepository.countByCode(123)).thenReturn(5);

        // Act
        int result = branchService.countByCode(123);

        // Assert
        assertEquals(5, result);
        verify(branchRepository, times(1)).countByCode(123);
    }
}
