package org.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.entity.*;
import org.example.entity.property.type.PropertyObjectAddress;
import org.example.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

/*

fixme

expand this if you have time pls

 */

@Log4j2
@Component
@RequiredArgsConstructor
public class DefaultInitializer implements CommandLineRunner {

    private final BannerRepository bannerRepository;
    private final BranchRepository branchRepository;
    private final BuilderObjectRepository builderObjectRepository;
    private final BuyerRepository buyerRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final LayoutRepository layoutRepository;
    private final PageRepository pageRepository;
    private final RealtorContactRepository realtorContactRepository;
    private final RealtorFeedBackRepository realtorFeedBackRepository;
    private final RealtorRepository realtorRepository;
    private final RegionRepository regionRepository;
    private final StreetRepository streetRepository;
    private final TopozoneRepository topozoneRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        log.info(" === DEFAULT INIT ===");
        if(userRepository.findAll().isEmpty()) {
            log.info("adding default user");
            UserEntity user = new UserEntity()
                                    .setEmail("admin@gmail.com")
                                    .setPassword(passwordEncoder.encode("admin"))
                                    .setName("Admin")
                                    .setMiddleName("Admin")
                                    .setSurname("Admin")
                                    .setRoles(Set.of(UserRole.DIRECTOR));
            userRepository.save(user);
            log.info("default user added");
        }

        if(realtorRepository.findAll().isEmpty()) {
            log.info("adding default realtor");
            Realtor realtor = new Realtor()
                                .setEmail("realtor@gmail.com")
                                .setName("Realtor")
                                .setMiddleName("Realtor")
                                .setSurname("Realtor")
                                .setBirthdate(LocalDate.now())
                                .setCode(420)
                                .setPassword(passwordEncoder.encode("realtor"));
            realtorRepository.save(realtor);
            log.info("default realtor added");
        }

        if(branchRepository.findAll().isEmpty()) {
            log.info("adding default branch");
            Branch branch = new Branch()
                            .setName("Branch")
                            .setEmail("email@gmail.com")
                            .setCode(200)
                            .setAddress("Address")
                            .setTelephone("380123456789");
            branchRepository.save(branch);
            log.info("default branch added");
        }

        if(buyerRepository.findAll().isEmpty()) {
            log.info("adding default buyer");
            Buyer buyer = new Buyer()
                    .setEmail("buyer@gmail.com")
                    .setBirthdate(LocalDate.now())
                    .setName("Buyer")
                    .setMiddleName("Buyer")
                    .setSurname("Buyer")
                    .setBranch(branchRepository.findFirstBy().orElse(null))
                    .setComment("Comment")
                    .setLastContactDate(LocalDate.now())
                    .setPassport("AB123456")
                    .setPhone("380123456789")
                    .setRealtor(realtorRepository.findFirstBy().orElse(null));

            buyerRepository.save(buyer);
            log.info("default buyer added");
        }

        if(builderObjectRepository.findAll().isEmpty()) {
            log.info("adding default builder object");
            BuilderObject bo = new BuilderObject()
                    .setName("BuilderObject")
                    .setPhone("380123456789")
                    .setAddress(new PropertyObjectAddress())
                    .setFloorQuantity(10);
            builderObjectRepository.save(bo);
            log.info("default builder object saved");
        }

        // todo
        // .....

        log.info(" === INIT COMPLETED ===");

    }
}
