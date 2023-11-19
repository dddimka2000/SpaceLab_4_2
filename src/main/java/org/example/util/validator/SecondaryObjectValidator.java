package org.example.util.validator;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.example.dto.ObjectBuilderDtoEdit;
import org.example.dto.PropertySecondaryObjectDTO;
import org.example.service.BranchServiceImpl;
import org.example.service.PropertySecondaryObjectService;
import org.example.service.PropertySecondaryObjectService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Component
@Log4j2
public class SecondaryObjectValidator implements Validator {
    private final long maxFileSize = 5 * 1024 * 1024; // Максимальный размер файла (5 МБ)
    private final List<String> supportedImageFormats = Arrays.asList("image/jpeg", "image/png", "image/jpg", "image/gif");
    private static final List<String> supportedFormatsFiles = Arrays.asList("application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");


    private final
    PropertySecondaryObjectService propertySecondaryObjectService;

    public SecondaryObjectValidator(PropertySecondaryObjectService propertySecondaryObjectService, BranchServiceImpl branchService) {
        this.propertySecondaryObjectService = propertySecondaryObjectService;
        this.branchService = branchService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PropertySecondaryObjectDTO.class.isAssignableFrom(clazz) || ObjectBuilderDtoEdit.class.isAssignableFrom(clazz);
    }
    private final
    BranchServiceImpl branchService;

    @Override
    public void validate(Object target, Errors errors) {
        PropertySecondaryObjectDTO entity = (PropertySecondaryObjectDTO) target;
        if(propertySecondaryObjectService.findByCode(entity.getObjectCode()).isPresent()){
            errors.rejectValue("objectCode", "", "Объект с таким кодом уже существует");
        }
        try {
            branchService.getById(entity.getBranchCode());
        }catch (EntityNotFoundException | NullPointerException e){
            errors.rejectValue("branchCode", "", "Филлиала с таким идом не существует");
        }
        if (entity.getFiles() != null && entity.getFiles().size() > 0) {
            for (MultipartFile multipartFile : entity.getFiles()) {
                if (!supportedFormatsFiles.contains(multipartFile.getContentType())) {
                    errors.rejectValue("files", "", "Неподдерживаемый формат файлов");
                }
                if (multipartFile.getSize() > maxFileSize) {
                    errors.rejectValue("files", "image.size.invalid", "Файл не должен превышать 5 МБ.");
                }
            }
        } else {
            errors.rejectValue("files", "image.size.invalid", "Минимум 1 файла в объекте");
        }
        if (entity.getPictures() != null && entity.getPictures().size() > 0) {
            for (MultipartFile multipartFile : entity.getPictures()) {
                if (!supportedImageFormats.contains(multipartFile.getContentType())) {
                    errors.rejectValue("pictures", "", "Неподдерживаемый формат изображения в фотографиях. Пожалуйста, выберите JPEG,PNG,JPG,GIF.");
                }
                if (multipartFile.getSize() > maxFileSize) {
                    errors.rejectValue("pictures", "image.size.invalid", "Фотография не должна превышать 5 МБ.");
                }
            }
        } else {
            errors.rejectValue("pictures", "image.size.invalid", "Минимум 1 фото в объекте");
        }
    }

    public void validateEdit(Object target, Errors errors, String code) {
        PropertySecondaryObjectDTO entity = (PropertySecondaryObjectDTO) target;
        if(!entity.getObjectCode().equals(code)&& propertySecondaryObjectService.findByCode(entity.getObjectCode()).isPresent()){
            errors.rejectValue("objectCode", "", "Объект с таким кодом уже существует");
        }
        try {
            branchService.getById(entity.getBranchCode());
        }catch (EntityNotFoundException e){
            errors.rejectValue("branchCode", "", "Филлиала с таким идом не существует");
        }
        if (entity.getFiles() != null && entity.getFiles().size() > 0) {
            for (MultipartFile multipartFile : entity.getFiles()) {
                if (!supportedFormatsFiles.contains(multipartFile.getContentType())) {
                    errors.rejectValue("files", "", "Неподдерживаемый формат файлов");
                }
                if (multipartFile.getSize() > maxFileSize) {
                    errors.rejectValue("files", "image.size.invalid", "Файл не должен превышать 5 МБ.");
                }
            }
        }
        if (entity.getPictures() != null && entity.getPictures().size() > 0) {
            for (MultipartFile multipartFile : entity.getPictures()) {
                if (!supportedImageFormats.contains(multipartFile.getContentType())) {
                    errors.rejectValue("pictures", "", "Неподдерживаемый формат изображения в фотографиях. Пожалуйста, выберите JPEG,PNG,JPG,GIF.");
                }
                if (multipartFile.getSize() > maxFileSize) {
                    errors.rejectValue("pictures", "image.size.invalid", "Фотография не должна превышать 5 МБ.");
                }
            }
        }
    }
}