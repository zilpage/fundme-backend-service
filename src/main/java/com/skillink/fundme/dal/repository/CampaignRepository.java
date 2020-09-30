package com.skillink.fundme.dal.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.skillink.fundme.dal.entity.Campaign;

import java.util.List;


public interface CampaignRepository extends JpaRepository<Campaign, Long>{

    Campaign getById(long id);
    List<Campaign> findCampaignByUserId(long userId);




}
