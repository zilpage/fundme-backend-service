package com.skillink.fundme.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.skillink.fundme.dal.entity.Contribution;
import com.skillink.fundme.dal.entity.Role;
import com.skillink.fundme.dto.Response;
import com.skillink.fundme.dto.Result;
import com.skillink.fundme.exception.BadRequestException;
import com.skillink.fundme.service.ContributionService;
import com.skillink.fundme.util.Util;

@RestController
@RequestMapping(value = "/api/v1/skilllink")
public class ContributionController {
	
	@Autowired
	ContributionService contributionService;

	@RequestMapping(value = "/contribution", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Response addContribution(@RequestBody @Validated Contribution item, HttpServletRequest request) throws Exception {

		if(Util.isEmptyString(item.getFirstName()))
			throw new BadRequestException("400", "First name is missing");
		if(Util.isEmptyString(item.getLastName()))
			throw new BadRequestException("400", "Last name is missing");

		if(Util.isEmptyString(item.getPhoneNumber()))
			throw new BadRequestException("400", "Phone number is missing");
		
		if(item.getAmount() < 1)
			throw new BadRequestException("400", "Amount is missing");
		
		long userId = 0; //Util.getCurrentUserDetail().getId();
		Contribution createdItem = contributionService.create(item,userId);
		
		Response response = new Response();
		response.setId(createdItem.getId());
		
		return response;
	}
	
	
	
	
	@RequestMapping(value = "/contributions", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public Result<Contribution> getAllContributions() throws Exception {
		Result<Contribution> result = new Result<Contribution>();
		List<Contribution> list = contributionService.getContributions();
		result.setList(list);
		return result;

	}
	
	@RequestMapping(value = "/public/contributions", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public Result<Contribution> getPublicContributions() throws Exception {
		Result<Contribution> result = new Result<Contribution>();
		List<Contribution> list = contributionService.getPublicContributions();
		result.setList(list);
		return result;

	}
	
}
