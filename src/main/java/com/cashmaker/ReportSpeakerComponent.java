package com.cashmaker;

import com.cashmaker.bot.SenderApi;
import com.cashmaker.goal.GoalKeeper;
import com.cashmaker.goal.GoalService;
import com.cashmaker.goal.GoalType;
import com.cashmaker.goal.GoalsProperties;
import com.cashmaker.pojo.binom.report.CampaignDto;
import com.cashmaker.service.BinomDataService;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by asti on 04.03.2018.
 */
@Component
@Slf4j
public class ReportSpeakerComponent {

    private SenderApi senderApi;
    private GoalService goalService;
    private BinomDataService binomDataService;

    @Autowired
    public void setBinomDataService(BinomDataService binomDataService) {
        this.binomDataService = binomDataService;
    }

    @Autowired
    public void setGoalService(GoalService goalService) {
        this.goalService = goalService;
    }

    @Autowired
    public void setSenderApi(SenderApi senderApi) {
        this.senderApi = senderApi;
    }


    @PostConstruct
    public void setDefaultGoals() {
        final List<CampaignDto> campaignsData = binomDataService.getCampaignsData();
        for (CampaignDto campaignDto : campaignsData) {
            final Optional<GoalKeeper> goalsForCampaign = createGoalsForCampaign(campaignDto);
            goalsForCampaign.ifPresent(goalKeeper -> goalService.addGoal(goalKeeper));
        }
    }

    private Optional<GoalKeeper> createGoalsForCampaign(CampaignDto campaignDto) {
        Optional<GoalKeeper> goalKeeperOptional = Optional.empty();

        final String leads = campaignDto.getLeads();
        if (!Strings.isNullOrEmpty(leads)) {

            final Integer leadsValue = Integer.valueOf(leads);
            final GoalKeeper gk = new GoalKeeper();
            gk.setCampaignId(campaignDto.getId());
            gk.setGoalType(GoalType.LEAD);
            final GoalsProperties gp = new GoalsProperties();
            gp.setGoalCount(GoalType.LEAD.getDefaultGoalCount());
            if (leadsValue < GoalType.LEAD.getDefaultGoalCount()) {
                gp.setGoalFulfill(true);
            }
            gk.setGoalsProperties(gp);
            goalKeeperOptional = Optional.of(gk);
        }
        return goalKeeperOptional;
    }



    public void checkGoalsProgress(){
        final List<CampaignDto> campaignsData = binomDataService.getCampaignsData();
        for (CampaignDto campaignDto : campaignsData) {
            final Integer leadCount = Integer.valueOf(campaignDto.getLeads());
            log.info("В кампании {} кол-во лидов {}", campaignDto.getId(), campaignDto.getLeads());

            final Set<GoalKeeper> goalsForCurrCampaign = goalService.getGoalsForCampaign(campaignDto.getId());
            for (GoalKeeper goalKeeper : goalsForCurrCampaign) {
                if (!goalKeeper.getGoalsProperties().isGoalFulfill() && leadCount >= goalKeeper.getGoalsProperties().getGoalCount()){
                    goalService.markGoalAsDone(goalKeeper);
                    final String text = getLeadText(campaignDto.getId(), campaignDto.getName(), Integer.valueOf(campaignDto.getLeads()));
                    senderApi.sendSyncMessageForGroupRecipients(SenderApi.CHAT_IDS, text);
                }
            }
        }
    }


    private String getLeadText(String campaignId, String campaignName, int leadCount){
        return String.format("По кампании \'%s\'(id = %s) кол-во лидов --> %s", campaignName, campaignId, leadCount);
    }

}
