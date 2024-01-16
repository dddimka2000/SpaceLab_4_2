package org.example.util.validator;

import lombok.extern.log4j.Log4j2;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.example.dto.LayoutDTO;
import org.example.dto.LayoutDTOEdit;
import org.example.dto.ObjectBuilderDto;
import org.example.dto.ObjectBuilderDtoEdit;
import org.example.entity.BuilderObject;
import org.example.service.ObjectBuilderService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Log4j2
public class ObjectBuilderValidator implements Validator {
    private final long maxFileSize = 5 * 1024 * 1024; // Максимальный размер файла (5 МБ)
    private final List<String> supportedImageFormats = Arrays.asList("image/jpeg", "image/png", "image/jpg", "image/gif");
    private static final List<String> supportedFormatsFiles = Arrays.asList("application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");


    private final
    ObjectBuilderService objectBuilderService;

    public ObjectBuilderValidator(ObjectBuilderService objectBuilderService) {
        this.objectBuilderService = objectBuilderService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ObjectBuilderDto.class.isAssignableFrom(clazz) || ObjectBuilderDtoEdit.class.isAssignableFrom(clazz);
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

            // Проверяем MIME-тип файла
            if (contentType != null) {
                if (contentType.startsWith("application/vnd.ms-excel") || // Excel
                        contentType.startsWith("application/pdf") || // PDF
                        contentType.startsWith("application/msword")||
                        contentType.startsWith("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") ) { // Word
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
    public void validate(Object target, Errors errors) {
        ObjectBuilderDto entity = (ObjectBuilderDto) target;
        try {
            Optional<BuilderObject> tryFindName = objectBuilderService.findByName(entity.getNameObject());
            if (tryFindName.isPresent()) {
                errors.rejectValue("nameObject", "", "(Ру)Объект новостроя с таким именем уже существует");
            }
            Optional<BuilderObject> tryFindName2 = objectBuilderService.findByNameEnglish(entity.getNameObjectEng());
            if (tryFindName2.isPresent()) {
                errors.rejectValue("nameObject", "", "(Англ)Объект новостроя с таким именем уже существует");
            }
            Optional<BuilderObject> tryFindName3 = objectBuilderService.findByNameUkraine(entity.getNameObjectUkr());
            if (tryFindName3.isPresent()) {
                errors.rejectValue("nameObject", "", "(Укр)Объект новостроя с таким именем уже существует");
            }
            for (MultipartFile multipartFile : entity.getFiles()) {
                if (!supportedImageFormats.contains(multipartFile.getContentType())) {
                    errors.rejectValue("files", "image.format.invalid", "Неподдерживаемый формат изображения в фотографиях. Пожалуйста, выберите JPEG,PNG,JPG,GIF.");
                }
                if (multipartFile.getSize() > maxFileSize) {
                    errors.rejectValue("files", "image.size.invalid", "Файл не должен превышать 5 МБ.");
                }
            }
            if (isValidFile(entity.getPrices())) {
                errors.rejectValue("chessboardFile", "image.format.invalid", "Неподдерживаемый формат файла с ценами. Пожалуйста, выберите Pdf, Exel или Документ Microsoft Word.");
            }
            if (entity.getPrices().getSize() > maxFileSize) {
                errors.rejectValue("chessboardFile", "image.size.invalid", "Файл с ценами не должен превышать 5 МБ.");
            }
            if (isValidFile(entity.getInstallmentTerms())) {
                errors.rejectValue("installmentTerms", "image.format.invalid", "Неподдерживаемый формат файла с условиями рассрочки. Пожалуйста, выберите Pdf, Exel или Документ Microsoft Word.");
            }
            if (entity.getInstallmentTerms().getSize() > maxFileSize) {
                errors.rejectValue("installmentTerms", "image.size.invalid", "Файл с условиями рассрочки не должен превышать 5 МБ.");
            }
            if (isValidFile(entity.getChessboardFile())) {
                errors.rejectValue("chessboardFile", "image.format.invalid", "Неподдерживаемый формат файла с шахматкой. Пожалуйста, выберите Pdf, Exel или Документ Microsoft Word.");
            }
            if (entity.getChessboardFile().getSize() > maxFileSize) {
                errors.rejectValue("chessboardFile", "image.size.invalid", "Файл с шахматкой не должен превышать 5 МБ.");
            }
            log.info("success files - 1 page");
            if (entity.getLayoutDTOList().isEmpty()) {
                errors.rejectValue("file", "image.size.invalid", "Заполните хотя бы 1 планировку.");
            }
            for (LayoutDTO layoutDTO : entity.getLayoutDTOList()) {
                MultipartFile multipartFile1 = layoutDTO.getImg1Layout();
                MultipartFile multipartFile2 = layoutDTO.getImg2Layout();
                MultipartFile multipartFile3 = layoutDTO.getImg3Layout();

                if (multipartFile1 != null && !multipartFile1.isEmpty()) {
                    if (!supportedImageFormats.contains(multipartFile1.getContentType())) {
                        errors.rejectValue("img1Layout", "image.format.invalid", "Неподдерживаемый формат изображения в фотографиях  планировки. Пожалуйста, выберите JPEG,PNG,JPG,GIF.");
                    }
                    if (multipartFile1.getSize() > maxFileSize) {
                        errors.rejectValue("img1Layout", "image.size.invalid", "Файл не должен превышать 5 МБ в фотографиях планировки.");
                    }
                }
                if (multipartFile2 != null && !multipartFile2.isEmpty()) {
                    if (!supportedImageFormats.contains(multipartFile2.getContentType())) {
                        errors.rejectValue("img2Layout", "image.format.invalid", "Неподдерживаемый формат изображения в фотографиях планировки. Пожалуйста, выберите JPEG,PNG,JPG,GIF.");
                    }
                    if (multipartFile2.getSize() > maxFileSize) {
                        errors.rejectValue("img2Layout", "image.size.invalid", "Файл не должен превышать 5 МБ в фотографиях планировки.");
                    }
                }
                if (multipartFile3 != null && !multipartFile3.isEmpty()) {
                    if (!supportedImageFormats.contains(multipartFile3.getContentType())) {
                        errors.rejectValue("img3Layout", "image.format.invalid", "Неподдерживаемый формат изображения в фотографиях планировки. Пожалуйста, выберите JPEG,PNG,JPG,GIF.");
                    }
                    if (multipartFile3.getSize() > maxFileSize) {
                        errors.rejectValue("img3Layout", "image.size.invalid", "Файл не должен превышать 5 МБ в фотографиях планировки.");
                    }
                }
            }

        } catch (Exception e) {
            log.error(e);
            return;
        }
    }


    public void validateEdit(Object target, Errors errors, Integer id) {
        ObjectBuilderDtoEdit entity = (ObjectBuilderDtoEdit) target;
        try {
            Optional<BuilderObject> findById = objectBuilderService.findById(id);
            Optional<BuilderObject> tryFindName = objectBuilderService.findByName(entity.getNameObject());

            if (tryFindName.isPresent() && !findById.get().getName().equals(tryFindName.get().getName())) {
                errors.rejectValue("nameObject", "", "Объект новостроя с таким именем уже существует");
            }
            if (Optional.ofNullable(entity.getOldFiles()).isEmpty()) {
                entity.setOldFiles(new ArrayList<>());
            }
            if (Optional.ofNullable(entity.getFiles()).isEmpty()) {
                entity.setFiles(new ArrayList<>());
            }
            log.info(entity.getFiles().size());
            log.info(entity.getOldFiles().size());
            if (Optional.ofNullable(entity.getFiles()).isPresent() && entity.getFiles().size() > 0) {
                for (MultipartFile multipartFile : entity.getFiles()) {
                    if (!supportedImageFormats.contains(multipartFile.getContentType())) {
                        errors.rejectValue("files", "image.format.invalid", "Неподдерживаемый формат изображения в фотографиях. Пожалуйста, выберите JPEG,PNG,JPG,GIF.");
                    }
                    if (multipartFile.getSize() > maxFileSize) {
                        errors.rejectValue("files", "image.size.invalid", "Файл не должен превышать 5 МБ.");
                    }
                }
            } else {
                if (Optional.ofNullable(entity.getOldFiles()).isEmpty()) {
                    errors.rejectValue("files", "", "В галереее 0 картинок.");
                }
            }
            if ((entity.getOldFiles().size() + entity.getFiles().size()) > 15) {
                errors.rejectValue("files", "image.size.invalid", "Файлов больше 15 в галлерее");
            }
            if ((entity.getOldFiles().size() + entity.getFiles().size()) < 1) {
                errors.rejectValue("files", "image.size.invalid", "Добавьте хотя бы 1 фото в галлерею");
            }
            log.info("success photos - 2 page");
            if (entity.getPrices() != null && entity.getPrices().isPresent()) {
                if (isValidFile(entity.getPrices().get())) {
                    errors.rejectValue("chessboardFile", "image.format.invalid", "Неподдерживаемый формат файла с ценами. Пожалуйста, выберите Pdf, Exel или Документ Microsoft Word.");
                }
                if (entity.getPrices().get().getSize() > maxFileSize) {
                    errors.rejectValue("chessboardFile", "image.size.invalid", "Файл с ценами не должен превышать 5 МБ.");
                }
            }
            if (entity.getInstallmentTerms() != null && entity.getInstallmentTerms().isPresent()) {
                if (isValidFile(entity.getInstallmentTerms().get())) {
                    errors.rejectValue("installmentTerms", "image.format.invalid", "Неподдерживаемый формат файла с условиями рассрочки. Пожалуйста, выберите Pdf, Exel или Документ Microsoft Word.");
                }
                if (entity.getInstallmentTerms().get().getSize() > maxFileSize) {
                    errors.rejectValue("installmentTerms", "image.size.invalid", "Файл с условиями рассрочки не должен превышать 5 МБ.");
                }
            }
            if (entity.getChessboardFile() != null && entity.getChessboardFile().isPresent()) {
                if (isValidFile(entity.getChessboardFile().get())) {
                    errors.rejectValue("chessboardFile", "image.format.invalid", "Неподдерживаемый формат файла с шахматкой. Пожалуйста, выберите Pdf, Exel или Документ Microsoft Word.");
                }
                if (entity.getChessboardFile().get().getSize() > maxFileSize) {
                    errors.rejectValue("chessboardFile", "image.size.invalid", "Файл с шахматкой не должен превышать 5 МБ.");
                }
            }
            log.info("success files - 1 page");
            if (entity.getLayoutDTOList().isEmpty()) {
                errors.rejectValue("layoutDTOList", "image.size.invalid", "Заполните хотя бы 1 планировку.");
            }
            for (LayoutDTOEdit layoutDTO : entity.getLayoutDTOList()) {
                Optional<MultipartFile> multipartFile1 = layoutDTO.getImg1Layout();
                if (multipartFile1 != null && multipartFile1.isPresent()) {
                    if (!supportedImageFormats.contains(multipartFile1.get().getContentType())) {
                        errors.rejectValue("layoutDTOList", "image.format.invalid", "Неподдерживаемый формат изображения в фотографиях  планировки. Пожалуйста, выберите JPEG,PNG,JPG,GIF.");
                    }
                    if (multipartFile1.get().getSize() > maxFileSize) {
                        errors.rejectValue("layoutDTOList", "image.size.invalid", "Файл не должен превышать 5 МБ в фотографиях планировки.");
                    }
                } else {
                    if (layoutDTO.getImg1Old()==null) {
                        errors.rejectValue("layoutDTOList", "", "Заполните фотографии в планировке");
                    }
                }

                Optional<MultipartFile> multipartFile2 = layoutDTO.getImg2Layout();
                if (multipartFile2 != null && !multipartFile2.isEmpty()) {
                    if (!supportedImageFormats.contains(multipartFile2.get().getContentType())) {
                        errors.rejectValue("layoutDTOList", "image.format.invalid", "Неподдерживаемый формат изображения в фотографиях планировки. Пожалуйста, выберите JPEG,PNG,JPG,GIF.");
                    }
                    if (multipartFile2.get().getSize() > maxFileSize) {
                        errors.rejectValue("layoutDTOList", "image.size.invalid", "Файл не должен превышать 5 МБ в фотографиях планировки.");
                    }
                } else {
                    if (layoutDTO.getImg2Old()==null) {
                        errors.rejectValue("layoutDTOList", "", "Заполните фотографии в планировке");
                    }
                }

                Optional<MultipartFile> multipartFile3 = layoutDTO.getImg3Layout();
                if (multipartFile3 != null && !multipartFile3.isEmpty()) {
                    if (!supportedImageFormats.contains(multipartFile3.get().getContentType())) {
                        errors.rejectValue("layoutDTOList", "image.format.invalid", "Неподдерживаемый формат изображения в фотографиях планировки. Пожалуйста, выберите JPEG,PNG,JPG,GIF.");
                    }
                    if (multipartFile3.get().getSize() > maxFileSize) {
                        errors.rejectValue("layoutDTOList", "image.size.invalid", "Файл не должен превышать 5 МБ в фотографиях планировки.");
                    }
                } else {
                    if (layoutDTO.getImg3Old()==null) {
                        errors.rejectValue("layoutDTOList", "", "Заполните фотографии в планировке");
                    }
                }
            }
            log.info("success files - 3 page");

        } catch (Exception e) {
            log.error(e);
            errors.rejectValue("nameObject", "", e.getMessage());
            return;
        }
    }
}
