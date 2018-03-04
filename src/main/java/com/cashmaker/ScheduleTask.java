package com.cashmaker;

import com.cashmaker.bot.SenderApi;
import com.cashmaker.pojo.binom.report.CampaignDto;
import com.cashmaker.service.BinomDataService;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by asti on 03.03.2018.
 */
@Component
@Slf4j
public class ScheduleTask {

    private static List<Long> CHAT_IDS = Lists.newArrayList(247731410L, 339483084L);
    private BinomDataService binomDataService;
    private SenderApi senderApi;

    @Autowired
    public void setSenderApi(SenderApi senderApi) {
        this.senderApi = senderApi;
    }

    @Autowired
    public void setBinomDataService(BinomDataService binomDataService) {
        this.binomDataService = binomDataService;
    }

    @Scheduled(fixedRate = 30000)
    public void reportCurrentTime() {
        log.info("The time is now {}", LocalDateTime.now());
    }

    @Scheduled(fixedRate = 30000)
    public void getCurrentCampaignData() {
        List<CampaignDto> campaignsData = binomDataService.getCampaignsData();
        if (!campaignsData.isEmpty()) {
            try {

                final ImmutableMap<String, CampaignDto> campaignMap = Maps.uniqueIndex(campaignsData, campaignDto -> {
                    assert campaignDto != null;
                    return campaignDto.getId();
                });

                for (Map.Entry<String, CampaignDto> campaignDtoEntry : campaignMap.entrySet()) {
                    if (campaignDtoEntry.getValue().getLeads() != null && Integer.valueOf(campaignDtoEntry.getValue().getLeads()) > 0 && (Integer.valueOf(campaignDtoEntry.getValue().getLeads()) % 5 == 0)) {
                        final String text = getLeadText(campaignDtoEntry.getKey(), campaignDtoEntry.getValue().getName(), Integer.valueOf(campaignDtoEntry.getValue().getLeads()));
                        senderApi.sendSyncMessageForGroupRecipients(CHAT_IDS, text);
                    } else {
                        log.info("Еще мало лидов...");
                    }
                }
            } catch ( Exception e){
                log.error("Что то пошло не так в таймере...");
                log.error("", e);
            }
        }


    }

    private String getLeadText(String campaignId, String campaignName, int leadCount){
        return String.format("По кампании \'%s\'(id = %s) кол-во лидов --> %s", campaignName, campaignId, leadCount);

    }
}
