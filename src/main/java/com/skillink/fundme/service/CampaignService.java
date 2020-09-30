package com.skillink.fundme.service;

import com.skillink.fundme.dal.entity.Campaign;
import com.skillink.fundme.dal.repository.CampaignRepository;
import com.skillink.fundme.util.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Rabiu Ademoh
 */

@Service
public class CampaignService {

    Logger logger  = LoggerFactory.getLogger(CampaignService.class);

    @Autowired
    CampaignRepository campaignRepository;

   public Campaign create(Campaign campaign, Long userId) throws Exception
    {
        try {
            campaign.setUserId(userId);
            Campaign campaign1 = campaignRepository.save(campaign);
            return  campaign1;
        }catch (Exception ex){
            LoggerUtil.logError(logger,ex);
            throw ex;
        }


    }
}
