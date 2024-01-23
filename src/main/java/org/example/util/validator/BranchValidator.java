package org.example.util.validator;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.example.dto.BranchDto;
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
public class BranchValidator implements Validator {
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "pdf");
    private static final long MAX_FILE_SIZE = 20 * 1024 * 1024;
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

    @Override
    public boolean supports(Class<?> clazz) {
        return BranchDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BranchDto branchDto = (BranchDto) target;
        if(branchDto.getId() == null || !branchDto.getImgPath().isEmpty()) {
            validateFile(branchDto.getImgPath(), "imgPath", errors);
            validateFileExtension(branchDto.getImgPath(), "imgPath", errors);
            validateFileSize(branchDto.getImgPath(), "imgPath", errors);
        }
    }

    private void validateFile(MultipartFile file, String fieldName, Errors errors) {
        if (file == null || file.isEmpty() || file.getOriginalFilename().isBlank()) {
            errors.rejectValue(fieldName, "file.required", "Файл не може бути порожнім");
            return;
        }
    }
    private void validateFileExtension(MultipartFile file, String fieldName, Errors errors) {
        if (file != null && !file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename != null) {
                String extension = getFileExtension(originalFilename);
                if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
                    errors.rejectValue(fieldName, "file.extension.invalid", "Допустимі розширення файлів: jpg, jpeg, png, pdf");
                }
                if (isValidPhoto(file)) {
                    errors.rejectValue(fieldName, "file.extension.invalid", "Попытка внести файл с измененным разрешением");
                }
            }
        }
    }

    private void validateFileSize(MultipartFile file, String fieldName, Errors errors) {
        if (file != null && !file.isEmpty()) {
            if (file.getSize() > MAX_FILE_SIZE) {
                errors.rejectValue(fieldName, "file.size.exceeded", "Максимальний розмір файлу: 20MB");
            }
        }
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1);
        }
        return "";
    }
}
