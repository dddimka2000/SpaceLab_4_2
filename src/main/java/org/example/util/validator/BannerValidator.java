package org.example.util.validator;

import lombok.extern.log4j.Log4j2;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.example.dto.BannerDto;
import org.example.dto.BannerSlideDto;
import org.example.dto.ObjectBuilderDto;
import org.example.dto.ObjectBuilderDtoEdit;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@Log4j2
public class BannerValidator implements Validator {
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
        return BannerDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BannerDto bannerDto = (BannerDto) target;
        int size = bannerDto.getSlides().size();
        List<Integer> list = new ArrayList<>();
        list.addAll(bannerDto.getSlides().stream().map(s -> s.getQueue()).collect(Collectors.toList()));
        AtomicInteger num = new AtomicInteger(0);
        try {
            bannerDto.getSlides().stream().forEach(s -> {
                int elementToRemove = s.getQueue();
                int index = list.indexOf(elementToRemove);
                if (index != -1) {
                    list.remove(index);
                }
                if (list.contains(s.getQueue())) {
                    errors.rejectValue("slides[" + num.get() + "].queue", "", "Один из элементов в очереди повторяется больше 1 раза");
                }


                num.incrementAndGet();
                if (s.getQueue() > size) {
                    errors.rejectValue("slides[" + num.get() + "].queue", "", "Очередь вышла за рамки");
                }
                if (s.getImgPath() == null) {
                        errors.rejectValue("slides[" + num.get() + "].imgPath", "", "Отсутствует фото у слайда");

                }else {
                    if(isValidPhoto(s.getImgPath())){
                        errors.rejectValue("slides[" + num.get() + "].imgPath", "", "Попытка загрузить файл с подменой разрешения");
                    }
                }

            });
        } catch (ConcurrentModificationException e) {
        }

    }
}
