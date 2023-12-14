package org.example.schedulers;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.example.entity.StreetExelEntity;
import org.example.repository.AddressExelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Component
@Log4j2
public class ExcelComponent {

    private final AddressExelRepository addressRepository;
    public ExcelComponent(AddressExelRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
    @PostConstruct
    public void processScheduledTask() {
        try {
            Resource resource = new ClassPathResource("static/exelAddress/exelEdit.xlsx");
            byte[] fileBytes = Files.readAllBytes(Paths.get(resource.getURI()));
            log.info("Start reading files...");
            processExcelFile(fileBytes);
            log.info("File ready");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("File was not found");
        }
    }
    @Transactional
    public void processExcelFile(byte[] file) throws IOException {
        if (addressRepository.count()<100) {
            List<StreetExelEntity> streetExelEntities = addressRepository.findAll();
            if (streetExelEntities.size() > 0) {
                addressRepository.deleteAll(streetExelEntities);
            }
            log.info("Delete old streets");
            Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(file));
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.rowIterator();
            rowIterator.next();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String region = getCellValueAsString(row.getCell(0));

                String area = getCellValueAsString(row.getCell(1));

                String newArea = getCellValueAsString(row.getCell(2));

                String oTGName = getCellValueAsString(row.getCell(3));

                String locality = getCellValueAsString(row.getCell(4));
                Cell number = row.getCell(5);
                Integer zipCode = null;
                if (number != null) {
                    zipCode = (int) number.getNumericCellValue();
                }

                String street = getCellValueAsString(row.getCell(6));
                List<String> houseNumbersList = new ArrayList<>();
                Cell houseNumbersCell = row.getCell(7);
                if (houseNumbersCell != null) {
                    houseNumbersList.addAll(Arrays.asList(String.valueOf(houseNumbersCell).split(",")));
                }

                StreetExelEntity address = new StreetExelEntity();
                address.setRegion(region);
                address.setArea(area);
                address.setNewArea(newArea);
                address.setOTGName(oTGName);
                address.setLocality(locality);
                address.setZipCode(zipCode);
                address.setStreetName(street);
                address.setHouseNumbers(houseNumbersList);
                addressRepository.save(address);
                log.info(address.getId()+" uploaded");
            }
            workbook.close();
        }
    }
    private String getCellValueAsString(Cell cell) {
        return cell != null ? cell.getStringCellValue() : "";
    }
}
