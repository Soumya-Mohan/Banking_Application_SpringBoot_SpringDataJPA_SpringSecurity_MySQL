package com.UserService.controller;

import com.UserService.Model.dto.Response;
import com.UserService.Model.dto.UserprofileDto;
import com.UserService.Model.utility.Constants;
import com.UserService.Model.utility.Validation;
import com.UserService.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserProfileController {

    @Autowired
    ProfileService profileService;

    @PostMapping("/register")
    public Response createProfile(@RequestBody UserprofileDto userprofileDto) {
        log.info("creating userProfile with: {}", userprofileDto.toString());

        if(userprofileDto.equals(null)){
            return Response.builder().message(Constants.INPUT_NULL).code(Constants.INPUT_NULL_CODE).build();
        }
        if(Validation.isValid(userprofileDto.getAadhar_num(),Constants.AADHAR_REGEX)==false){
            log.info("creating userProfile with: {}:: Aadhar validation fail");
            return Response.builder().message(Constants.AADHAR_VALIDATION_FAIL).code(Constants.AADHAR_VALIDATION_FAIL_CODE).build();
        }
        if(Validation.isValid(userprofileDto.getPAN_Num(),Constants.PAN_REGEX)==false){
            log.info("creating userProfile with: {}:: PAN validation fail");
            return Response.builder().message(Constants.PAN_VALIDATION_FAIL).code(Constants.PAN_VALIDATION_FAIL_CODE).build();
        }

        return profileService.createUser(userprofileDto);

    }


    @GetMapping("/getProfileByID")
    public Response getProfileByAadharNumOrPan(@RequestParam String number){
        log.info("getting profile details using : {}", number);
        if(number.isEmpty() || number.equals(null)||number.isBlank()){
            return Response.builder().message(Constants.INPUT_NULL).code(Constants.INPUT_NULL_CODE).build();
        }
        return profileService.getProfileByAadharNumOrPan(number);
    }

    @GetMapping("/getAllByPagination")
    public Response getAllByPagination(@RequestParam int pageNo, int pageSize){
        log.info("getAllByPagination details using : {}");
        return profileService.getAllByPagination(pageNo,pageSize);

    }



}
