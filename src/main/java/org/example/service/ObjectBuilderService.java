package org.example.service;

import io.minio.errors.*;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.example.dto.LayoutDTO;
import org.example.dto.LayoutDTOEdit;
import org.example.dto.ObjectBuilderDto;
import org.example.dto.ObjectBuilderDtoEdit;
import org.example.entity.BuilderObject;
import org.example.entity.ImagesForObject;
import org.example.entity.Layout;
import org.example.entity.property.type.TypeObject;
import org.example.mapper.LayoutMapper;
import org.example.repository.BuilderObjectRepository;
import org.example.specification.BuilderObjectSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ObjectBuilderService {
    private final
    BuilderObjectRepository builderObjectRepository;
    private final String imagesBucketName = "images";
    private final String filesBucketName = "files";
    final
    StringTrim stringTrim;
    final
    ImagesForObjectService imagesForObjectService;
    final
    MinioService minioService;
    final
    LayoutService layoutService;


    public ObjectBuilderService(BuilderObjectRepository builderObjectRepository, LayoutService layoutService, MinioService minioService, ImagesForObjectService imagesForObjectService, StringTrim stringTrim) {
        this.builderObjectRepository = builderObjectRepository;
        this.layoutService = layoutService;
        this.minioService = minioService;
        this.imagesForObjectService = imagesForObjectService;
        this.stringTrim = stringTrim;
    }

    public long count() {
        return builderObjectRepository.count();
    }

    public Optional<BuilderObject> findById(Integer id) {
        log.info("ObjectBuilderService-findById start");
        Optional<BuilderObject> entity = builderObjectRepository.findById(id);
        log.info("ObjectBuilderService-findById successfully");
        return entity;
    }

    public Page<BuilderObject> findBuilderObjectsByCriteria(String name, String district, String zone, String street, Integer floorQuantity, Integer minPrice, Pageable pageable) {
        Specification<BuilderObject> spec = new BuilderObjectSpecification(name, district, zone, street, floorQuantity, minPrice);
        return builderObjectRepository.findAll(spec, pageable);
    }

    public Optional<BuilderObject> findByName(String name) {
        log.info("ObjectBuilderService-findByName start");
        Optional<BuilderObject> entity = builderObjectRepository.findByName(name);
        log.info("ObjectBuilderService-findByName successfully");
        return entity;
    }

    public Optional<BuilderObject> findByNameEnglish(String name) {
        log.info("ObjectBuilderService-findByNameEnglish start");
        Optional<BuilderObject> entity = builderObjectRepository.findByNameEnglish(name);
        log.info("ObjectBuilderService-findByNameEnglish successfully");
        return entity;
    }

    public Optional<BuilderObject> findByNameUkraine(String name) {
        log.info("ObjectBuilderService-findByNameUkraine start");
        Optional<BuilderObject> entity = builderObjectRepository.findByNameUkraine(name);
        log.info("ObjectBuilderService-findByNameUkraine successfully");
        return entity;
    }

    public void save(BuilderObject entity) {
        log.info("ObjectBuilderService-save start");
        builderObjectRepository.save(entity);
        log.info("ObjectBuilderService-save successfully");
    }

    public void saveEdit(ObjectBuilderDtoEdit objectBuilderDtoEdit, BuilderObject builderObject, Integer id) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, IllegalAccessException {
        String uuidFile = UUID.randomUUID().toString();
        String resultFilename;
        if (objectBuilderDtoEdit.getPrices() != null && objectBuilderDtoEdit.getPrices().isPresent()) {
            resultFilename = uuidFile + "." + objectBuilderDtoEdit.getPrices().get().getOriginalFilename();
            minioService.putMultipartFile(objectBuilderDtoEdit.getPrices().get(), filesBucketName, resultFilename);
            builderObject.setFilePrices(resultFilename);
        }
        if (objectBuilderDtoEdit.getChessboardFile() != null && objectBuilderDtoEdit.getChessboardFile().isPresent()) {
            uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + objectBuilderDtoEdit.getChessboardFile().get().getOriginalFilename();
            minioService.putMultipartFile(objectBuilderDtoEdit.getChessboardFile().get(), filesBucketName, resultFilename);
            builderObject.setFileCheckerboard(resultFilename);
        }
        if (objectBuilderDtoEdit.getInstallmentTerms() != null && objectBuilderDtoEdit.getInstallmentTerms().isPresent()) {
            uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + objectBuilderDtoEdit.getInstallmentTerms().get().getOriginalFilename();
            minioService.putMultipartFile(objectBuilderDtoEdit.getInstallmentTerms().get(), filesBucketName, resultFilename);
            builderObject.setFileInstallmentTerms(resultFilename);
        }
        stringTrim.trimStringFields(builderObject);
        stringTrim.trimStringFields(builderObject.getPromotion());
        stringTrim.trimStringFields(builderObject.getAddress());

        save(builderObject);
        //Delete old Layouts
        layoutService.deleteAllByBuilderObject(builderObject);
        for (LayoutDTOEdit dto : objectBuilderDtoEdit.getLayoutDTOList()) {
            Layout layout = LayoutMapper.INSTANCE.toEntity(dto);
            stringTrim.trimStringFields(layout);
            if (dto.getImg1Layout() != null && !dto.getImg1Layout().isEmpty()) {
                uuidFile = UUID.randomUUID().toString();
                resultFilename = uuidFile + "." + dto.getImg1Layout().get().getOriginalFilename();
                minioService.putMultipartFile(dto.getImg1Layout().get(), imagesBucketName, resultFilename);
                layout.setImg1(resultFilename);
            } else {
                layout.setImg1(dto.getImg1Old());
            }

            if (dto.getImg2Layout() != null && !dto.getImg2Layout().isEmpty()) {
                uuidFile = UUID.randomUUID().toString();
                resultFilename = uuidFile + "." + dto.getImg2Layout().get().getOriginalFilename();
                minioService.putMultipartFile(dto.getImg2Layout().get(), imagesBucketName, resultFilename);
                layout.setImg2(resultFilename);
            } else {
                layout.setImg2(dto.getImg2Old());
            }

            if (dto.getImg3Layout() != null && !dto.getImg3Layout().isEmpty()) {
                uuidFile = UUID.randomUUID().toString();
                resultFilename = uuidFile + "." + dto.getImg3Layout().get().getOriginalFilename();
                minioService.putMultipartFile(dto.getImg3Layout().get(), imagesBucketName, resultFilename);
                layout.setImg3(resultFilename);
            } else {
                layout.setImg3(dto.getImg3Old());
            }

            layout.setBuilderObject(builderObject);
            layoutService.save(layout);
        }


        List<ImagesForObject> namesImages = imagesForObjectService.findByIdObjectAndTypeObject(id, TypeObject.BY_BUILDER).stream().filter(path -> !objectBuilderDtoEdit.getOldFiles().contains(path.getPath())).collect(Collectors.toList());
        //delete old images
        namesImages.stream().forEach(s -> imagesForObjectService.deleteById(s.getIdImage()));
        //delete old images in MinIO
        namesImages.stream().forEach(s -> {
            try {

                minioService.deleteImg(s.getPath(), imagesBucketName);
            } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                     InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException |
                     IOException e) {
                throw new RuntimeException(e);
            }
        });
        //save new images in MinIO, ImagesForObjectService
        minioService.saveFilesInMinIO(builderObject, objectBuilderDtoEdit.getFiles(), objectBuilderDtoEdit, imagesBucketName);
    }

    @SneakyThrows
    public void saveCreate(ObjectBuilderDto objectBuilderDto, BuilderObject builderObject) {
        log.info("ObjectBuilderService-create start");
        String uuidFile = UUID.randomUUID().toString();

        String resultFilename = uuidFile + "." + objectBuilderDto.getPrices().getOriginalFilename();
        minioService.putMultipartFile(objectBuilderDto.getPrices(), filesBucketName, resultFilename);
        builderObject.setFilePrices(resultFilename);

        uuidFile = UUID.randomUUID().toString();
        resultFilename = uuidFile + "." + objectBuilderDto.getChessboardFile().getOriginalFilename();
        minioService.putMultipartFile(objectBuilderDto.getChessboardFile(), filesBucketName, resultFilename);
        builderObject.setFileCheckerboard(resultFilename);

        uuidFile = UUID.randomUUID().toString();
        resultFilename = uuidFile + "." + objectBuilderDto.getInstallmentTerms().getOriginalFilename();
        minioService.putMultipartFile(objectBuilderDto.getInstallmentTerms(), filesBucketName, resultFilename);
        builderObject.setFileInstallmentTerms(resultFilename);
        stringTrim.trimStringFields(builderObject);
        stringTrim.trimStringFields(builderObject.getPromotion());
        stringTrim.trimStringFields(builderObject.getAddress());

        save(builderObject);
        for (LayoutDTO dto : objectBuilderDto.getLayoutDTOList()) {
            Layout layout = LayoutMapper.INSTANCE.toEntity(dto);
            stringTrim.trimStringFields(layout);
            //files to MinIO and add UUID
            uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + dto.getImg1Layout().getOriginalFilename();
            minioService.putMultipartFile(dto.getImg1Layout(), imagesBucketName, resultFilename);
            layout.setImg1(resultFilename);
            uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + dto.getImg2Layout().getOriginalFilename();
            minioService.putMultipartFile(dto.getImg2Layout(), imagesBucketName, resultFilename);
            layout.setImg2(resultFilename);
            uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + dto.getImg3Layout().getOriginalFilename();
            minioService.putMultipartFile(dto.getImg3Layout(), imagesBucketName, resultFilename);
            layout.setImg3(resultFilename);
            layout.setBuilderObject(builderObject);
            layoutService.save(layout);
        }
        //add pictures to MinIO
        minioService.saveFilesInMinIO(builderObject, objectBuilderDto.getFiles(), objectBuilderDto, imagesBucketName);
        log.info("ObjectBuilderService-create successfully");
    }

    public void deleteById(Integer id) {
        log.info("ObjectBuilderService-deleteById start: " + id);
        Optional<BuilderObject> builderObject = builderObjectRepository.findById(id);
        if (builderObject.isPresent()) {
            builderObjectRepository.delete(builderObject.get());
            log.info("ObjectBuilderService-deleteById successfully: " + id);
        }
    }

    public Page<BuilderObject> findBuilderObjectsPage(Integer pageNumber, Integer pageSize) {
        log.info("ObjectBuilderService-findAllQuestionPages start");
        Page<BuilderObject> page = null;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        page = builderObjectRepository.findAll(pageable);
        log.info("ObjectBuilderService with " + pageNumber + " have been found");
        log.info("ObjectBuilderService-findAllQuestionPages successfully");
        return page;
    }

    public Page<BuilderObject> forSelect(String name, Pageable pageable) {
        return builderObjectRepository.findAll(Specification.where(BuilderObjectSpecification.nameContains(name)), pageable);
    }
}
