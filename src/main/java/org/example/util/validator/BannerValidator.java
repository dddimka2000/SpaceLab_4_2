package org.example.util.validator;

import lombok.extern.log4j.Log4j2;
import org.example.dto.BannerDto;
import org.example.dto.BannerSlideDto;
import org.example.dto.ObjectBuilderDto;
import org.example.dto.ObjectBuilderDtoEdit;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@Log4j2
public class BannerValidator implements Validator {
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
                //queue
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
                //photo
                if (s.getImgPath() == null) {
                    if (s.getOldImgPath() == null) {
                        errors.rejectValue("slides[" + num.get() + "].imgPath", "", "Отсутствует фото у слайда");
                    }
                }

            });
        } catch (ConcurrentModificationException e) {
        }

    }
}
