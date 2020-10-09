package com.skillink.fundme.api;

import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.skillink.fundme.dal.entity.Campaign;
import com.skillink.fundme.dto.Dashboard;
import com.skillink.fundme.dto.Mail;
import com.skillink.fundme.dto.Response;
import com.skillink.fundme.dto.Result;
import com.skillink.fundme.exception.BadRequestException;
import com.skillink.fundme.service.AppService;
import com.skillink.fundme.service.CampaignService;
import com.skillink.fundme.service.ContributionService;
import com.skillink.fundme.service.EmailServiceImpl;
import com.skillink.fundme.util.Util;

@RestController
@RequestMapping(value = "/api/v1/skilllink")
public class CampaignController {
	
	@Autowired
	CampaignService campaignService;
	
	@Autowired
	ContributionService contributionService;
	
	@Autowired
	AppService appService;
	
	@Autowired
	EmailServiceImpl emailServiceImpl;

	@RequestMapping(value = "/campaign", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Response addCampaign(@RequestBody @Validated Campaign item, HttpServletRequest request) throws Exception {

		if(Util.isEmptyString(item.getTitle()))
			throw new BadRequestException("400", "Title is missing");
		if(Util.isEmptyString(item.getCoverPhoto()))
			throw new BadRequestException("400", "Cover photo is missing");

		if(Util.isEmptyString(item.getCategory()))
			throw new BadRequestException("400", "Category is missing");
		if(Util.isEmptyString(item.getOwnership()))
			throw new BadRequestException("400", "Ownership is missing");
		if(Util.isEmptyString(item.getRaiseAs()))
			throw new BadRequestException("400", "Raise as is missing");
		if(item.getGoal() < 1)
			throw new BadRequestException("400", "Goal is missing");
		
		long userId = Util.getCurrentUserDetail().getId();
		Campaign createdItem = campaignService.create(item,userId);
		
		Response response = new Response();
		response.setId(createdItem.getId());
		
		return response;
	}
	
	
	
	
	@RequestMapping(value = "/my/campaigns", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public Result<Campaign> getAllCampaigns() throws Exception {
		Result<Campaign> result = new Result<Campaign>();
		List<Campaign> list = campaignService.getMyCampaigns();
		result.setList(list);
		return result;

	}
	
	@RequestMapping(value = "/admin/campaigns", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public Result<Campaign> getAdminCampaigns() throws Exception {
		Result<Campaign> result = new Result<Campaign>();
		List<Campaign> list = campaignService.getAdminCampaigns();
		result.setList(list);
		Dashboard dashboard = appService.getDshboardStatistics();
		result.setDashboard(dashboard);
		return result;

	}
	
	@RequestMapping(value = "/public/campaigns", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public Result<Campaign> getPublicCampaigns() throws Exception {
		Result<Campaign> result = new Result<Campaign>();
		List<Campaign> list = campaignService.getPublicCampaigns();
		result.setList(list);
		return result;

	}
	
	
	
	
	
	@RequestMapping(value = "/activate/campaign", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public Campaign activateCampaign(@RequestBody @Validated Campaign item) throws Exception {
		if(item.getId() < 1)
			throw new BadRequestException("400", "Identifier is missing");
		
		Campaign response = campaignService.activator(item);
		
		return response;

	}
	
	
	
	
	@RequestMapping(value = "/campaign/{id}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public Campaign getCampaignById(@PathVariable long id) throws Exception {

		if (id == 0)
			throw new BadRequestException("400", " ID cannot be empty");

		Campaign campaign = campaignService.getCampaignById(id);
		if (campaign == null) {
			throw new BadRequestException("400", "Campaign does not exist");
		}
		campaign.setContributions(contributionService.getContributionsByCampaignId(id));
		return campaign;
	}
	
	@RequestMapping(value = "/send/mail", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void sendMail() {

		Mail mail = new Mail();
		mail.setBody("Hello");
		mail.setMailSubject("Test Skill");
		mail.setMailTo("ionyekanna@gmail.com");
		mail.setMailFrom("unifiedorbit@gmail.com");
		try {
			emailServiceImpl.sendMail(mail);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
