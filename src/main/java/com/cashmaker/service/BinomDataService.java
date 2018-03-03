package com.cashmaker.service;

import com.cashmaker.pojo.binom.report.CampaignDto;

import java.util.List;

/**
 * Интерфейс для доступа к данным отчета Бинома
 */
public interface BinomDataService {

    List<CampaignDto> getCampaignsData();

}
