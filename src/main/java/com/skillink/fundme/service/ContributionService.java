package com.skillink.fundme.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.skillink.fundme.dal.entity.Contribution;
import com.skillink.fundme.dal.repository.ContributionRepository;
import com.skillink.fundme.util.LoggerUtil;
import com.skillink.fundme.util.Util;

/**
 * @author Rabiu Ademoh
 */

@Service
public class ContributionService {

    Logger logger  = LoggerFactory.getLogger(ContributionService.class);

    @Autowired
    ContributionRepository contributionRepository;
    
    @Autowired
	JdbcTemplate jdbcTemplate;

   public Contribution create(Contribution contribution, long userId) throws Exception
    {
        try {
            contribution.setUserId(userId);
            Contribution contribution1 = contributionRepository.save(contribution);
            return  contribution1;
        }catch (Exception ex){
            LoggerUtil.logError(logger,ex);
            throw ex;
        }


    }
   
   
   public List<Contribution> getContributions() throws Exception
   {
       try {
    	   String sql = "SELECT * FROM tbl_contribution  where user_id = ?  order by id desc ";
    	   long userId = Util.getCurrentUserDetail().getId();
			RowMapper<Contribution> rowMapper = new BeanPropertyRowMapper<Contribution>(Contribution.class);
			List<Contribution> list = jdbcTemplate.query(sql, rowMapper, userId);
			return list;
       }catch (Exception ex){
           LoggerUtil.logError(logger,ex);
           throw ex;
       }


   }
   
   
   public List<Contribution> getPublicContributions() throws Exception
   {
       try {
    	   String sql = "SELECT * FROM tbl_contribution  order by id desc ";
			RowMapper<Contribution> rowMapper = new BeanPropertyRowMapper<Contribution>(Contribution.class);
			List<Contribution> list = jdbcTemplate.query(sql, rowMapper);
			return list;
       }catch (Exception ex){
           LoggerUtil.logError(logger,ex);
           throw ex;
       }


   }
   
   
   public List<Contribution> getContributionsByCampaignId(long id) throws Exception
   {
       try {
    	   String sql = "SELECT * FROM tbl_contribution  where campaign_id = ?  order by id desc ";
    	   
			RowMapper<Contribution> rowMapper = new BeanPropertyRowMapper<Contribution>(Contribution.class);
			List<Contribution> list = jdbcTemplate.query(sql, rowMapper, id);
			return list;
       }catch (Exception ex){
           LoggerUtil.logError(logger,ex);
           throw ex;
       }


   }
   
}
