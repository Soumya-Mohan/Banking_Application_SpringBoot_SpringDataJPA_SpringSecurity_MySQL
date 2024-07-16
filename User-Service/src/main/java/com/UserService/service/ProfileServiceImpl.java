package com.UserService.service;

import com.UserService.Model.dto.Response;
import com.UserService.Model.dto.UserprofileDto;
import com.UserService.Model.entity.Profile;
import com.UserService.Model.utility.Constants;
import com.UserService.Repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static Response response = new Response();

    @Override
    public Response createUser(UserprofileDto userprofileDto) {
        log.info("Inside ProfileServiceImpl::createUser : {}");
        try {
            //checking user is already exists or not based on aadhar card number
            Optional<Profile> profileData = profileRepository.findbyAadharNum(userprofileDto.getAadhar_num());
            if (profileData.isPresent()) {
                return Response.builder().message(Constants.USER_ALREADY_FOUND).code(Constants.USER_ALREADY_FOUND_CODE).build();
            }
            Profile profile = convertToProfileEntity(userprofileDto);
            profile = profileRepository.save(profile);
            if (profile.getAadhar_num() != null) {
                response.setCode(Constants.SUCCESS_CODE);
                response.setMessage(Constants.SUCCESS);
            } else {
                throw new SQLException();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Response getProfileByAadharNumOrPan(String number) {
        Optional<Profile> profileData = profileRepository.findbyAadharNum(number);
        if (!profileData.isPresent()) {
            return Response.builder().message(Constants.DATA_NOT_FOUND).code(Constants.DATA_NOT_FOUND_CODE).build();
        }
        return Response.builder().message(Constants.SUCCESS).code(Constants.SUCCESS_CODE).data(profileData.get()).build();
    }

    /**
     * @return all profile details with pagination
     */
    @Override
    public Response getAllByPagination(int pageNo, int pageSize) {
        log.info("Inside ProfileServiceImpl::getAllByPagination : {}");
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Profile> pageable = profileRepository.findAll(paging);
        return (pageable.hasContent()) ?
                Response.builder().message(Constants.SUCCESS).code(Constants.SUCCESS_CODE).data(pageable).build()
                :
                Response.builder().message(Constants.DATA_NOT_FOUND).code(Constants.DATA_NOT_FOUND_CODE).build();
    }

    private Profile convertToProfileEntity(UserprofileDto userprofileDto) {
        Profile profile = modelMapper.map(userprofileDto, Profile.class);
        System.out.println(profile.toString());
        return profile;
    }


}
