package org.example.util.validator;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.example.dto.ObjectBuilderDtoEdit;
import org.example.dto.PropertySecondaryObjectDTO;
import org.example.entity.Realtor;
import org.example.service.BranchServiceImpl;
import org.example.service.PropertySecondaryObjectService;
import org.example.service.PropertySecondaryObjectService;
import org.example.service.RealtorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
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

    public SecondaryObjectValidator(PropertySecondaryObjectService propertySecondaryObjectService, BranchServiceImpl branchService, RealtorServiceImpl realtorService) {
        this.propertySecondaryObjectService = propertySecondaryObjectService;
        this.branchService = branchService;
        this.realtorService = realtorService;
    }
    public static boolean isValidPhoto(MultipartFile file) {
        try {
            InputStream fileInputStream = file.getInputStream();
            ContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            Parser parser = new AutoDetectParser();
            ParseContext context = new ParseContext();

            parser.parse(fileInputStream, handler, metadata, context);

            String contentType = metadata.get("Content-Type");
            if (contentType != null) {
                if (contentType.startsWith("image/jpeg") ||
                        contentType.startsWith("image/png") ||
                        contentType.startsWith("image/jpg") ||
                        contentType.startsWith("image/gif")
                ) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
    public static boolean isValidFile(MultipartFile file) {
        try {
            InputStream fileInputStream = file.getInputStream();
            ContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            Parser parser = new AutoDetectParser();
            ParseContext context = new ParseContext();

            parser.parse(fileInputStream, handler, metadata, context);

            String contentType = metadata.get("Content-Type");
            if (contentType != null) {
                if (
//                        contentType.startsWith("application/vnd.ms-excel") || // Excel
                        contentType.startsWith("application/pdf") // PDF
//                                || contentType.startsWith("application/msword")||
//                        contentType.startsWith("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                ) { // Word
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return PropertySecondaryObjectDTO.class.isAssignableFrom(clazz) || ObjectBuilderDtoEdit.class.isAssignableFrom(clazz);
    }

    private final
    BranchServiceImpl branchService;
    final
    RealtorServiceImpl realtorService;

    @Override
    public void validate(Object target, Errors errors) {
        PropertySecondaryObjectDTO entity = (PropertySecondaryObjectDTO) target;
        Realtor realtor = new Realtor();
        try {
            realtor = realtorService.getByCode(entity.getEmployeeCode());
        } catch (EntityNotFoundException | NullPointerException ex) {
            errors.rejectValue("employeeCode", "", "Кода данного сотрудника не существует");
        }
        if (propertySecondaryObjectService.findByCode(entity.getObjectCode()).isPresent()) {
            errors.rejectValue("objectCode", "", "Объект с таким кодом уже существует");
        }
        try {
            branchService.getByCode(entity.getBranchCode());
        } catch (EntityNotFoundException | NullPointerException e) {
            errors.rejectValue("branchCode", "", "Филиала с таким идом не существует");
        }
        if (entity.getFiles() != null && entity.getFiles().size() > 0) {
            for (MultipartFile multipartFile : entity.getFiles()) {
                if (!supportedFormatsFiles.contains(multipartFile.getContentType())) {
                    errors.rejectValue("files", "", "Неподдерживаемый формат файлов");
                }
                if (multipartFile.getSize() > maxFileSize) {
                    errors.rejectValue("files", "image.size.invalid", "Файл не должен превышать 5 МБ.");
                }
                if (isValidFile(multipartFile)) {
                    errors.rejectValue("files", "", "Попытка занести файл с измененным расширением");
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

                if (isValidPhoto(multipartFile)) {
                    errors.rejectValue("pictures", "", "Попытка занести фото с измененным расширением");
                }
            }
        } else {
            errors.rejectValue("pictures", "image.size.invalid", "Минимум 1 фото в объекте");
        }
    }

    public void validateEdit(Object target, Errors errors, String code) {
        PropertySecondaryObjectDTO entity = (PropertySecondaryObjectDTO) target;
        Realtor realtor = new Realtor();
        try {
            realtor = realtorService.getByCode(entity.getEmployeeCode());
        } catch (EntityNotFoundException ex) {
            errors.rejectValue("employeeCode", "", "Кода данного сотрудника не существует");
        }
        if (!entity.getObjectCode().equals(code) && propertySecondaryObjectService.findByCode(entity.getObjectCode()).isPresent()) {
            errors.rejectValue("objectCode", "", "Объект с таким кодом уже существует");
        }
        try {
            branchService.getByCode(entity.getBranchCode());
        } catch (EntityNotFoundException e) {
            errors.rejectValue("branchCode", "", "Филиала с таким идом не существует");
        }
        if (entity.getFiles() != null && entity.getFiles().size() > 0) {
            for (MultipartFile multipartFile : entity.getFiles()) {
                if (!supportedFormatsFiles.contains(multipartFile.getContentType())) {
                    errors.rejectValue("files", "", "Неподдерживаемый формат файлов");
                }
                if (multipartFile.getSize() > maxFileSize) {
                    errors.rejectValue("files", "image.size.invalid", "Файл не должен превышать 5 МБ.");
                }
                if (isValidFile(multipartFile)) {
                    errors.rejectValue("files", "", "Попытка занести файл с измененным расширением");
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
                if (isValidPhoto(multipartFile)) {
                    errors.rejectValue("pictures", "", "Попытка занести фото с измененным расширением");
                }
            }
        }
        int picturesNum=0;
        if (entity.getPictures() != null) {
            picturesNum=entity.getPictures().size();
        }
        int oldPicturesNum=0;
        if (entity.getOldPictures() != null){
            picturesNum=entity.getOldPictures().size();
        }
        if(picturesNum+oldPicturesNum==0){
            errors.rejectValue("pictures", "image.size.invalid", "Минимум 1 фото в объекте");
        }
    }
}
