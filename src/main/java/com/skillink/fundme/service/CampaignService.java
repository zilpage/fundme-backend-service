package com.skillink.fundme.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.skillink.fundme.dal.entity.Campaign;
import com.skillink.fundme.dal.repository.CampaignRepository;
import com.skillink.fundme.util.LoggerUtil;
import com.skillink.fundme.util.Util;

/**
 * @author Rabiu Ademoh
 */

@Service
public class CampaignService {

    Logger logger  = LoggerFactory.getLogger(CampaignService.class);

    @Autowired
    CampaignRepository campaignRepository;
    
    @Autowired
	JdbcTemplate jdbcTemplate;

   public Campaign create(Campaign campaign, long userId) throws Exception
    {
        try {
            campaign.setUserId(userId);
            campaign.setEnabled(false);
            Campaign campaign1 = campaignRepository.save(campaign);
            return  campaign1;
        }catch (Exception ex){
            LoggerUtil.logError(logger,ex);
            throw ex;
        }


    }
   
   public Campaign activator(Campaign campaign) throws Exception
   {
       try {
    	   Campaign camp = campaignRepository.getById(campaign.getId());
    	   camp.setEnabled(campaign.isEnabled());
    	   camp = campaignRepository.save(camp);
           return  camp;
       }catch (Exception ex){
           LoggerUtil.logError(logger,ex);
           throw ex;
       }


   }
   
   public List<Campaign> getAdminCampaigns() throws Exception
   {
       try {
    	   String sql = "SELECT * FROM tbl_campaign  order by id desc ";
			RowMapper<Campaign> rowMapper = new BeanPropertyRowMapper<Campaign>(Campaign.class);
			List<Campaign> list = jdbcTemplate.query(sql, rowMapper);
			return list;
       }catch (Exception ex){
           LoggerUtil.logError(logger,ex);
           throw ex;
       }


   }
   
   public List<Campaign> getMyCampaigns() throws Exception
   {
       try {
    	   String sql = "SELECT * FROM tbl_campaign  where user_id = ?  order by id desc ";
    	   long userId = Util.getCurrentUserDetail().getId();
			RowMapper<Campaign> rowMapper = new BeanPropertyRowMapper<Campaign>(Campaign.class);
			List<Campaign> list = jdbcTemplate.query(sql, rowMapper, userId);
			return list;
       }catch (Exception ex){
           LoggerUtil.logError(logger,ex);
           throw ex;
       }


   }
   
   
   public List<Campaign> getPublicCampaigns() throws Exception
   {
       try {
    	   String sql = "SELECT * FROM tbl_campaign  where  enabled = true order by id desc ";
			RowMapper<Campaign> rowMapper = new BeanPropertyRowMapper<Campaign>(Campaign.class);
			List<Campaign> list = jdbcTemplate.query(sql, rowMapper);
			return list;
       }catch (Exception ex){
           LoggerUtil.logError(logger,ex);
           throw ex;
       }


   }
   
   public Campaign getCampaignById(long id) throws Exception
   {
       try {
    	   String sql = "SELECT * FROM tbl_campaign  where  id = ? ";
			RowMapper<Campaign> rowMapper = new BeanPropertyRowMapper<Campaign>(Campaign.class);
			List<Campaign> list = jdbcTemplate.query(sql, rowMapper,id);
			if(list.size() == 1)
				return list.get(0);
			return null;
       }catch (Exception ex){
           LoggerUtil.logError(logger,ex);
           throw ex;
       }


   }
   
   
}
