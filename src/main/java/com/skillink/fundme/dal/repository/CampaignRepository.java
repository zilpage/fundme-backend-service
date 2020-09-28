package com.skillink.fundme.dal.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.skillink.fundme.dal.entity.Campaign;


public interface CampaignRepository extends JpaRepository<Campaign, Long>{

	
}
