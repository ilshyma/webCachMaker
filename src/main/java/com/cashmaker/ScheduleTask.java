package com.cashmaker;

import com.cashmaker.bot.SenderApi;
import com.cashmaker.service.BinomDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by asti on 03.03.2018.
 */
@Component
@Slf4j
public class ScheduleTask {

    private BinomDataService binomDataService;
    private SenderApi senderApi;
    private ReportSpeakerComponent reportSpeakerComponent;

    @Autowired
    public void setReportSpeakerComponent(ReportSpeakerComponent reportSpeakerComponent) {
        this.reportSpeakerComponent = reportSpeakerComponent;
    }

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
//
//    @Scheduled(fixedRate = 30000)
//    public void getCurrentCampaignData() {
//        List<CampaignDto> campaignsData = binomDataService.getCampaignsData();
//        if (!campaignsData.isEmpty()) {
//            try {
//
//                final ImmutableMap<String, CampaignDto> campaignMap = Maps.uniqueIndex(campaignsData, campaignDto -> {
//                    assert campaignDto != null;
//                    return campaignDto.getId();
//                });
//
//                for (Map.Entry<String, CampaignDto> campaignDtoEntry : campaignMap.entrySet()) {
//                    if (campaignDtoEntry.getValue().getLeads() != null && Integer.valueOf(campaignDtoEntry.getValue().getLeads()) > 0 && (Integer.valueOf(campaignDtoEntry.getValue().getLeads()) % 5 == 0)) {
//
//                    } else {
//                        log.info("Еще мало лидов...");
//                    }
//                }
//            } catch ( Exception e){
//                log.error("Что то пошло не так в таймере...");
//                log.error("", e);
//            }
//        }
//    }

    @Scheduled(fixedRate = 30000)
    public void startBinomLeadsWork(){
        reportSpeakerComponent.checkGoalsProgress();
    }




}
