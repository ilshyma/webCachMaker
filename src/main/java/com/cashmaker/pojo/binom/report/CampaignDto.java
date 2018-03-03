package com.cashmaker.pojo.binom.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CampaignDto {
    private String id;
    private String name;
    private String status;
    private String create_date;
    private String start_date;
    private String keyword;
    private String ts_id;
    private String group_id;
    private String rotation_id;
    private String current_cpc;
    private String auto_cpc;
    private String use_id;
    private String camp_tokens;
    private String domain_id;
    private String domain_name;
    private String domain_ssl;
    private String rotation_name;
    private String group_name;
    private String ts_name;
    private String is_note;
    private String tokens_url;
    private String e_token;
    private String c_token;
    private String clicks;
    private String lp_clicks;
    private String lp_views;
    private String unique_clicks;
    private String unique_camp_clicks;
    private String bots;
    private String leads;
    private String revenue;
    private String cost;
    private String profit;
    private String roi;
    private String lp_ctr;
    private String cr;
    private String epc;
    private String cpc;
    private String url;
    private String event_1;
    private String event_2;
    private String event_3;
    private String event_4;
    private String event_5;
    private String event_6;
    private String event_7;
    private String event_8;
    private String event_9;
    private String event_10;

}
