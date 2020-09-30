package com.skillink.fundme.service;

import com.skillink.fundme.dal.entity.Campaign;
import com.skillink.fundme.dal.entity.Role;
import com.skillink.fundme.dal.entity.User;
import com.skillink.fundme.dal.repository.CampaignRepository;
import com.skillink.fundme.dal.repository.UserRepository;
import com.skillink.fundme.dto.Result;
import com.skillink.fundme.util.LoggerUtil;
import com.skillink.fundme.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Rabiu Ademoh
 */

@Service
public class UserService {

    Logger logger  = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CampaignRepository campaignRepository;

    @Value("${maxLoginAttempt}")
    private String maxLoginAttempt;

    /**
     * Create User
     */
    public User create(User user) throws Exception {
        try{
           // user.setStatus(false);
            user.set_enabled(true);
            user.setLoginFailedCount(0);

            String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);

            User newUser = userRepository.save(user);

            return newUser;

        } catch (Exception ex){
            LoggerUtil.logError(logger,ex);
            throw ex;
        }

    }

    /**
     * Get All User
     */
    public Result<User> getAll(int pageNum, int pageSize) throws Exception {
        try {
            Result<User> user = new Result<>();
            Pageable page = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, ("id"));
            Page<User> pageResult = userRepository.findAll(page);

            user.setCurrentPageNumber(pageNum + 1);
            user.setCurrentPageSize(pageSize);
            user.setList(pageResult.getContent());
            user.setNoOfRecords(pageResult.getTotalElements());

            return user;

        } catch (Exception ex){
            LoggerUtil.logError(logger,ex);
            throw ex;
        }
    }

    /**
     * Get User By Id
     */
    public User getUserById(Long id){
        try {
            User user = null;
            user = userRepository.getById(id);
            return user;

        } catch (Exception ex) {
            LoggerUtil.logError(logger,ex);
            throw ex;
        }

    }

    /**
     * Get Campaigns By UserId
     */
    public List<Campaign> getCampaignsByUserId(Long userId){
        try {
            List<Campaign> campaignList = new ArrayList<Campaign>();
            campaignList = campaignRepository.findCampaignByUserId(userId);
            return campaignList;

        } catch (Exception ex) {
            LoggerUtil.logError(logger,ex);
            throw ex;
        }
    }


    public User login(String username, String password) throws Exception {
        try {
            User user = userRepository.findByUsername(username);
            if (null == user) {
                return null;
            } else {
                if (user.is_locked()) {
                    throw new BadCredentialsException("You have been locked. Kindly contact the administrator");
                }

                if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
                    user.setLoginFailedCount(0);

                    user.setLastLogin(Util.getDateStringByFormat("yyyy-MM-dd HH:mm", Util.getCurrentTimeStamp()));
                    userRepository.save(user);
                    return user;
                } else {
                    int loginFailedCount = 0;
                    loginFailedCount = user.getLoginFailedCount();

                    user.setLoginFailedCount(loginFailedCount + 1);
                    if (loginFailedCount + 1 >= Integer.parseInt(maxLoginAttempt)) {
                        user.set_locked(true);
                    } else {
                        user.set_locked(false);
                    }
                    userRepository.save(user);
                    return null;
                }
            }

        } catch (Exception ex) {
            LoggerUtil.logError(logger, ex);
            throw ex;
        }
    }

    /**
     * Login
     */

   /* public User login(String username, String password) throws Exception {
        try{
            User user = find
        }
    }

*/




}
