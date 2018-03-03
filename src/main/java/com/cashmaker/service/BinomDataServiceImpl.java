package com.cashmaker.service;

import com.cashmaker.pojo.binom.report.CampaignDto;
import com.cashmaker.util.JacksonJsonUtils;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class BinomDataServiceImpl implements BinomDataService {

    String url = "http://s3.pornolub.xyz/lo.php?page=Campaigns&api_key=7000001c460aa20372437e5468f731e97df02b3";
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public List<CampaignDto> getCampaignsData() {
        log.info("Начинаю получение данных по ссылке [ {} ]", url);
        List<CampaignDto> rawCampaignData = Collections.EMPTY_LIST;
        try {
//            ResponseEntity<List<CampaignDto>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CampaignDto>>() {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
//            rawCampaignData = responseEntity.getBody();
            rawCampaignData = parseBodyFromText(responseEntity.getBody());
            log.info("Получены данные: {}", new Gson().toJson(rawCampaignData));
        } catch (Exception e){
            log.error("Не получилось полчить данные!\n", e);
        }
        return rawCampaignData;
    }

    private static List<CampaignDto> parseBodyFromText(String bodyStr) throws Exception {
        List<CampaignDto> dtoList = Collections.emptyList();


        CollectionType RECOGNIZE_TEXT_LIST = JacksonJsonUtils
                .getObjectMapper()
                .getTypeFactory()
                .constructCollectionType(List.class, CampaignDto.class);

        if (!Strings.isNullOrEmpty(bodyStr)) {
            try {
                dtoList = JacksonJsonUtils.getObjectMapper().readValue(bodyStr, RECOGNIZE_TEXT_LIST);
                log.debug("Преобразовал {} в {}", bodyStr, dtoList);
            } catch (Exception e) {
                log.error("Не смог размапить ответ от Веринта сервиса", e);
                throw new IllegalStateException("Ошибка парсинга ответа от Веринта. ", e);
            }
        }
        return dtoList;
    }

}
