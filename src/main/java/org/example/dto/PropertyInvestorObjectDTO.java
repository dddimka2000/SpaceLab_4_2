package org.example.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.entity.property.type.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class PropertyInvestorObjectDTO {
    public Integer id;
    public Boolean status; //
    @NotNull(message = "Заполните код объекта")
    @Pattern(regexp = "^[0-9-]+$", message = "Код объекта должен содержать только цифры и '-'")
    public String objectCode; //
    @NotNull(message = "Заполните код филлиала")
    @Min(value = 1, message = "Введите корректный код филлиала")
    @Max(value = 100000001, message = "Введите корректный код филлиала")
    public Integer branchCode;//
    @NotNull(message = "Заполните код сотрудника")
    @Min(value = 1, message = "Введите корректный код сотрудника")
    @Max(value = 100000001, message = "Введите корректный код сотрудника")
    public Integer employeeCode;//
    @NotNull(message = "Заполните цену")
    @Min(value = 1, message = "Введите корректную цену ")
    @Max(value = 100000001, message = "Введите корректную цену")
    public Integer price; //
    @Size(min = 1, max = 31, message = "Ориентир должен быть от 2 до 30 символов.")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s.,!?_-]+$", message = "Ориентир должен содержать только буквы a-z A-Z, цифры 0-9 и \"_\",\"-\".")
    public String landmark;//
    @Valid
    public PropertyObjectAddress address;//
    @NotNull(message = "Заполните поле ЖК")
    public Integer residentialComplexId;
    @NotNull(message = "Заполните поле этаж")
    @Min(value = 1, message = "Введите корректный этаж")
    @Max(value = 100000001, message = "Введите корректный этаж")
    public Integer floor;//
    @NotNull(message = "Заполните поле колличество этажей")
    @Min(value = 1, message = "Введите корректное колличество этажей")
    @Max(value = 100000001, message = "Введите корректное колличество этажей")
    public Integer floorQuantity;//
    @NotNull(message = "Заполните поле колличество комнат")
    @Min(value = 1, message = "Введите корректное колличество комнат")
    @Max(value = 100000001, message = "Введите корректное колличество комнат")
    public Integer roomQuantity;//
    @NotNull(message = "Заполните поле номер квартиры")
    @Pattern(regexp = "^[а-яА-Яa-zA-Z0-9\\s]+$", message = "Номер квартиры должен содержать только буквы a-z A-Z, цифры 0-9.")
    public String apartmentNumber;//
    @NotNull(message = "(Ру)Заполните поле имя владельца")
    public String ownerName;//
    @NotNull(message = "(Англ)Заполните поле имя владельца")
    public String ownerNameEng;//
    @NotNull(message = "(Укр)Заполните поле имя владельца")
    public String ownerNameUkr;//
    public Integer buildFinishDate;
    @Size(min = 10, max = 15, message = "Номер должен быть от 11 до 15 символов.")
    @Pattern(regexp = "\\+?[0-9]+", message = "Номер телефона должен начинаться с '+' и содержать только цифры.")
    public String ownerPhone;//
    @NotNull(message = "Заполните дату покупки")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    public LocalDate boughtDate;
    @NotNull(message = "Заполните тип собственности")
    public OwnershipType ownershipType;//
    @Size(max = 450, message = "Заметки могут иметь до 450 символов.")
    public String notes;
    @NotNull(message = "Заполните срок сдачи")
    public PropertyBuildStatus buildStatus;//
    //    @NotNull(message = "Заполните дату окончания строительства")
//    public LocalDate buildFinishDate;
    @NotNull(message = "Заполните площадь")
    @Min(value = 1, message = "Введите корректную площадь")
    @Max(value = 100001, message = "Введите корректную площадь")
    public Integer areaTotal, areaLiving, areaKitchen;//
    @NotNull(message = "Заполните тип кухни")
    public KitchenType kitchenType;//
    @NotNull(message = "Заполните тип санузла")
    public BathroomType bathroomType;//
    @NotNull(message = "Заполните тип балкона")
    public BalconyType balconyType;//
    @NotNull(message = "Заполните тип вида из окна")
    public WindowViewType windowViewType;//
    @NotNull(message = "Заполните тип плиты")
    public KitchenStoveType kitchenStoveType;//
    @NotNull(message = "Заполните тип отопления")
    public HeatingType heatingType;//
    @NotNull(message = "Заполните метраж комнат")
    @Pattern(regexp = "^[0-9+]+$", message = "Номер телефона должен начинаться с '+' и содержать только цифры.")
    public String roomMeters;//
    @Min(value = 0, message = "Введите корректную высоту потолка")
    @Max(value = 100001, message = "Введите корректную высоту потолка")
    public Double heightCeiling;//
    @Size(min = 1, max = 31, message = "(Ру)Тип стен должен быть от 2 до 30 символов.")
    public String wallMaterial;//
    @Size(min = 1, max = 31, message = "(Англ)Тип стен должен быть от 2 до 30 символов.")
    public String wallMaterialEng;//
    @Size(min = 1, max = 31, message = "(Укр)Тип стен должен быть от 2 до 30 символов.")
    public String wallMaterialUkr;//
    @NotNull(message = "Заполните состояние квартиры")
    public PropertyOrigin propertyOrigin;//
    public Boolean bargain;
    public Boolean exclusive;
    public Boolean urgent;
    public Boolean free;
    public Boolean open;
    public Boolean intermediary;
    @NotNull(message = "Заполните дату последнего контакта")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    public LocalDate lastContactDate;//
    public Boolean vnp;//
    @NotNull(message = "Заполните дату ВНП")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    public LocalDate vnpExpirationDate;//
    @Size(min = 1, max = 31, message = "Информационный ресурс должен быть от 2 до 30 символов.")
    public String informationSource;//
    @Size(min = 1, max = 1000, message = "Описание должно быть от 2 до 1000 символов.")
    public String description;
    @Size(min = 1, max = 1000, message = "Описание должно быть от 2 до 1000 символов.")
    public String descriptionEn;
    @Size(min = 1, max = 1000, message = "Описание должно быть от 2 до 1000 символов.")
    public String descriptionUk;
    @Size(min = 1, max = 31, message = "(Ру)Рекламный заголовок должен быть от 2 до 30 символов.")
    public String advertisementHeadline;
    @Size(min = 1, max = 31, message = "(Укр)Рекламный заголовок должен быть от 2 до 30 символов.")
    public String advertisementHeadlineUk;
    @Size(min = 1, max = 31, message = "(Англ)Рекламный заголовок должен быть от 2 до 30 символов.")
    public String advertisementHeadlineEn;
    @Size(min = 1, max = 1000, message = "(Ру)Рекламный текст должен быть от 2 до 1000 символов.")
    public String advertisementText;
    @Size(min = 1, max = 1000, message = "(Укр)Рекламный текст должен быть от 2 до 1000 символов.")
    public String advertisementTextUk;
    @Size(min = 1, max = 1000, message = "(Англ)Рекламный текст должен быть от 2 до 1000 символов.")
    public String advertisementTextEn;
    public Boolean advertisementEnabled;
    @Size(max = 1000, message = "Комментарий должен быть до 1000 символов.")
    public String administrationComment;//
    public List<String> filesName = new ArrayList<>();
    public List<String> picturesName = new ArrayList<>();
    public List<MultipartFile> files = new ArrayList<>();
    public List<MultipartFile> pictures = new ArrayList<>();
    public List<String> oldFiles = new ArrayList<>();
    public List<String> oldPictures = new ArrayList<>();
    @NotNull(message = "Заполните статус публикации")
    public PublicationStatus publicationStatus;
}
