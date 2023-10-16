package org.example.schedulers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.example.entity.ExchangeRates;
import org.example.service.ExchangeRatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Log4j2
public class NbuScheduler {
    private final
    ExchangeRatesService exchangeRatesService;

    public NbuScheduler(ExchangeRatesService exchangeRatesService) {
        this.exchangeRatesService = exchangeRatesService;
    }

    @SneakyThrows
    @Scheduled(fixedRate = 43200000)
    public void myTask() {
        String url = "https://bank.gov.ua/NBUStatService/v1/statdirectory/dollar_info?json";
        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        String json;
        if (responseCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = reader.readLine()) != null) {
                content.append(inputLine);
            }
            reader.close();

            json = content.toString();
            System.out.println("Полученные JSON-данные:\n" + json);
            JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();

            if (jsonArray.size() > 0) {
                JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();

                double rate = jsonObject.get("rate").getAsDouble();

                System.out.println("Курс доллара: " + rate);
                log.info("Курс доллара: " + rate);
                Optional<ExchangeRates> exchangeRates = exchangeRatesService.findByName("Доллар");
                if (exchangeRates.isEmpty()) {
                    exchangeRates = Optional.of(new ExchangeRates());
                    exchangeRates.get().setName("Доллар");
                }
                exchangeRates.get().setExchange_rate(new BigDecimal(rate));
                LocalDateTime currentDateTime = LocalDateTime.now();
                exchangeRates.get().setDate_refresh(currentDateTime);
                exchangeRatesService.save(exchangeRates.get());
            }
        } else {
            log.info("Ошибка получения курса bank.gov.ua");
        }
    }
}
