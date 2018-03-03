package com.cashmaker.service;

import com.cashmaker.pojo.binom.report.CampaignDto;
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
            ResponseEntity<List<CampaignDto>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<CampaignDto>>() {
            });
            rawCampaignData = responseEntity.getBody();
            log.info("Получены данные: {}", new Gson().toJson(rawCampaignData));
        } catch (Exception e){
            log.error("Не получилось полчить данные!\n", e);
        }
        return rawCampaignData;

    }

}
