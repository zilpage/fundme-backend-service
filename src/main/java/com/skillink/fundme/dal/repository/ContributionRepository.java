package com.skillink.fundme.dal.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.skillink.fundme.dal.entity.Contribution;


public interface ContributionRepository extends JpaRepository<Contribution, Long>{

	
}
