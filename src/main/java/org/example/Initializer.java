package org.example;

import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class Initializer implements CommandLineRunner {
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final RegionRepository regionRepository;
    private final TopozoneRepository topozoneRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (topozoneRepository.count() == 0) {
            saveTopozoneEntities();
        }

        if (regionRepository.count() == 0) {
            saveRegionEntities();
        }

        if (cityRepository.count() == 0) {
            saveCityEntities();
        }

        if (districtRepository.count() == 0) {
            saveDistrictEntities();
        }

        if (userRepository.count() == 0) {
            UserEntity user = new UserEntity();
            user.setName("admin");
            user.setPassword("admin");
            user.setEmail("admin@gmail.com");
            userRepository.save(user);
        }
        createTriggerBeforeDeleteRealtor();
        createTriggerBeforeDeleteBuilderObject();
    }

    @Transactional
    public void saveRegionEntities() {
        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setNameRegion("Киевская область");
        regionRepository.save(regionEntity);
        regionEntity = new RegionEntity();
        regionEntity.setNameRegion("Львовская область");
        regionRepository.save(regionEntity);
        regionEntity = new RegionEntity();
        regionEntity.setNameRegion("Харьковская область");
        regionRepository.save(regionEntity);
        regionEntity = new RegionEntity();
        regionEntity.setNameRegion("Одесская область");
        regionRepository.save(regionEntity);
        regionEntity = new RegionEntity();
        regionEntity.setNameRegion("Днепропетровская область");
        regionRepository.save(regionEntity);
    }

    @Transactional
    public void saveCityEntities() {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setNameCity("Киев");
        cityEntity.setRegion(regionRepository.findById(1).get());
        cityRepository.save(cityEntity);
        cityEntity = new CityEntity();
        cityEntity.setNameCity("Бровары");
        cityEntity.setRegion(regionRepository.findById(1).get());
        cityRepository.save(cityEntity);
        cityEntity = new CityEntity();
        cityEntity.setNameCity("Львов");
        cityEntity.setRegion(regionRepository.findById(2).get());
        cityRepository.save(cityEntity);
        cityEntity = new CityEntity();
        cityEntity.setNameCity("Дрогобич");
        cityEntity.setRegion(regionRepository.findById(2).get());
        cityRepository.save(cityEntity);
        cityEntity = new CityEntity();
        cityEntity.setNameCity("Харьков");
        cityEntity.setRegion(regionRepository.findById(3).get());
        cityRepository.save(cityEntity);
        cityEntity = new CityEntity();
        cityEntity.setNameCity("Изюм");
        cityEntity.setRegion(regionRepository.findById(3).get());
        cityRepository.save(cityEntity);
        cityEntity = new CityEntity();
        cityEntity.setNameCity("Одесса");
        cityEntity.setRegion(regionRepository.findById(4).get());
        cityRepository.save(cityEntity);
        cityEntity = new CityEntity();
        cityEntity.setNameCity("Ильичевск");
        cityEntity.setRegion(regionRepository.findById(4).get());
        cityRepository.save(cityEntity);
        cityEntity = new CityEntity();
        cityEntity.setNameCity("Днепр");
        cityEntity.setRegion(regionRepository.findById(5).get());
        cityRepository.save(cityEntity);
        cityEntity = new CityEntity();
        cityEntity.setNameCity("Павлоград");
        cityEntity.setRegion(regionRepository.findById(5).get());
        cityRepository.save(cityEntity);
    }

    @Transactional
    public void saveDistrictEntities() {
        DistrictEntity districtEntity = new DistrictEntity();
        districtEntity.setNameDistrict("Печерский район");
        districtEntity.setCity(cityRepository.findById(1).get());
        districtRepository.save(districtEntity);
        districtEntity = new DistrictEntity();
        districtEntity.setNameDistrict("Голосеевский район");
        districtEntity.setCity(cityRepository.findById(1).get());
        districtRepository.save(districtEntity);
        districtEntity = new DistrictEntity();
        districtEntity.setNameDistrict("Броварской район");
        districtEntity.setCity(cityRepository.findById(2).get());
        districtRepository.save(districtEntity);
        districtEntity = new DistrictEntity();
        districtEntity.setNameDistrict("Кагарлыкский район");
        districtEntity.setCity(cityRepository.findById(2).get());
        districtRepository.save(districtEntity);
        districtEntity = new DistrictEntity();
        districtEntity.setNameDistrict("Железнодорожный район");
        districtEntity.setCity(cityRepository.findById(3).get());
        districtRepository.save(districtEntity);
        districtEntity = new DistrictEntity();
        districtEntity.setNameDistrict("Франковский район");
        districtEntity.setCity(cityRepository.findById(3).get());
        districtRepository.save(districtEntity);
        districtEntity = new DistrictEntity();
        districtEntity.setNameDistrict("Дрогобычский район");
        districtEntity.setCity(cityRepository.findById(4).get());
        districtRepository.save(districtEntity);
        districtEntity = new DistrictEntity();
        districtEntity.setNameDistrict("Новый район");
        districtEntity.setCity(cityRepository.findById(4).get());
        districtRepository.save(districtEntity);
        districtEntity = new DistrictEntity();
        districtEntity.setNameDistrict("Шевченковский район");
        districtEntity.setCity(cityRepository.findById(5).get());
        districtRepository.save(districtEntity);
        districtEntity = new DistrictEntity();
        districtEntity.setNameDistrict("Киевский район");
        districtEntity.setCity(cityRepository.findById(5).get());
        districtRepository.save(districtEntity);
        districtEntity = new DistrictEntity();
        districtEntity.setNameDistrict("Центральный район");
        districtEntity.setCity(cityRepository.findById(6).get());
        districtRepository.save(districtEntity);
        districtEntity = new DistrictEntity();
        districtEntity.setNameDistrict("Киевский район");
        districtEntity.setCity(cityRepository.findById(6).get());
        districtRepository.save(districtEntity);
        districtEntity = new DistrictEntity();
        districtEntity.setNameDistrict("Приморский район");
        districtEntity.setCity(cityRepository.findById(7).get());
        districtRepository.save(districtEntity);
        districtEntity = new DistrictEntity();
        districtEntity.setNameDistrict("Малиновский район");
        districtEntity.setCity(cityRepository.findById(7).get());
        districtRepository.save(districtEntity);
        districtEntity = new DistrictEntity();
        districtEntity.setNameDistrict("Ильичевский район");
        districtEntity.setCity(cityRepository.findById(8).get());
        districtRepository.save(districtEntity);
        districtEntity = new DistrictEntity();
        districtEntity.setNameDistrict("Малиновский район");
        districtEntity.setCity(cityRepository.findById(8).get());
        districtRepository.save(districtEntity);
        districtEntity = new DistrictEntity();
        districtEntity.setNameDistrict("Жовтневый район");
        districtEntity.setCity(cityRepository.findById(9).get());
        districtRepository.save(districtEntity);
        districtEntity = new DistrictEntity();
        districtEntity.setNameDistrict("Амур-Нижнеднепровский район");
        districtEntity.setCity(cityRepository.findById(9).get());
        districtRepository.save(districtEntity);
        districtEntity = new DistrictEntity();
        districtEntity.setNameDistrict("Павлоградский район");
        districtEntity.setCity(cityRepository.findById(10).get());
        districtRepository.save(districtEntity);
        districtEntity = new DistrictEntity();
        districtEntity.setNameDistrict("Павлоградский район");
        districtEntity.setCity(cityRepository.findById(10).get());
        districtRepository.save(districtEntity);
    }

    @Transactional
    public void saveTopozoneEntities() {
        TopozoneEntity topozoneEntity = new TopozoneEntity();
        topozoneEntity.setName("Фонтан");
        topozoneRepository.save(topozoneEntity);
        topozoneEntity = new TopozoneEntity();
        topozoneEntity.setName("Колодец");
        topozoneRepository.save(topozoneEntity);
        topozoneEntity = new TopozoneEntity();
        topozoneEntity.setName("Река");
        topozoneRepository.save(topozoneEntity);
        topozoneEntity = new TopozoneEntity();
        topozoneEntity.setName("Свалка");
        topozoneRepository.save(topozoneEntity);
        topozoneEntity = new TopozoneEntity();
        topozoneEntity.setName("Другие дома");
        topozoneRepository.save(topozoneEntity);
        topozoneEntity = new TopozoneEntity();
        topozoneEntity.setName("Трасса");
        topozoneRepository.save(topozoneEntity);
        topozoneEntity = new TopozoneEntity();
        topozoneEntity.setName("Газон");
        topozoneRepository.save(topozoneEntity);
    }
    private void createTriggerBeforeDeleteRealtor() {
        String triggerSql = "CREATE TRIGGER IF NOT EXISTS before_delete_realtor\n" +
                "BEFORE DELETE ON realtor\n" +
                "FOR EACH ROW\n" +
                "BEGIN\n" +
                "    UPDATE property_commercial_object\n" +
                "    SET realtor_id = NULL\n" +
                "    WHERE realtor_id = OLD.id;\n" +
                "\n" +
                "    UPDATE property_house_object\n" +
                "    SET realtor_id = NULL\n" +
                "    WHERE realtor_id = OLD.id;\n" +
                "\n" +
                "    UPDATE property_investor_object\n" +
                "    SET realtor_id = NULL\n" +
                "    WHERE realtor_id = OLD.id;\n" +
                "\n" +
                "    UPDATE property_secondary_object\n" +
                "    SET realtor_id = NULL\n" +
                "    WHERE realtor_id = OLD.id;\n" +
                "END;";
        jdbcTemplate.execute(triggerSql);
    }
    private void createTriggerBeforeDeleteBuilderObject() {
        String triggerSql = "CREATE TRIGGER IF NOT EXISTS before_delete_builder_object\n" +
                "BEFORE DELETE ON builder_objects\n" +
                "FOR EACH ROW\n" +
                "BEGIN\n" +
                "    DELETE FROM property_commercial_object\n" +
                "    WHERE builder_object_id = OLD.id;\n" +
                "\n" +
                "    DELETE FROM property_house_object\n" +
                "    WHERE builder_object_id = OLD.id;\n" +
                "\n" +
                "    DELETE FROM property_investor_object\n" +
                "    WHERE builder_object_id = OLD.id;\n" +
                "\n" +
                "    DELETE FROM property_secondary_object\n" +
                "    WHERE builder_object_id = OLD.id;\n" +
                "END;";
        jdbcTemplate.execute(triggerSql);
    }
}