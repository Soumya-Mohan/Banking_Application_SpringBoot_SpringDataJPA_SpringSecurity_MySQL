package com.UserService.service;

import com.UserService.Model.dto.Response;
import com.UserService.Model.dto.UserprofileDto;
import com.UserService.Model.entity.Profile;
import com.UserService.Model.utility.Constants;
import com.UserService.Repository.ProfileRepository;
import com.UserService.exception.ResourceConflict;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
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

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public Response createUser(UserprofileDto userprofileDto) {
        log.info("Inside ProfileServiceImpl::createUser : {}");
        try {
            //checking user is already exists or not based on aadhar card number
            profileRepository.findbyAadharNum(userprofileDto.getAadhar_num())
                    .ifPresent(account -> {
                        log.error("Account already exists on the server");
                        throw new ResourceConflict("Account already exists on the server");
                    });
            Profile profile = convertToProfileEntity(userprofileDto);
            profile = profileRepository.save(profile);
            if (profile.getAadhar_num() != null) {
                log.info("Inside ProfileServiceImpl::createUser == the account is created and sending data to Kafka Consumer");
                kafkaTemplate.send(Constants.CREATE_NEW_ACCOUNT_ID_TOPIC, profile.getAadhar_num());
                log.info("Inside ProfileServiceImpl::createUser == the profile data sent to Kafka Consumer");
                response.setCode(Constants.SUCCESS_CODE);
                response.setMessage(Constants.SUCCESS);
            } else {
                throw new SQLException("date not saved");
            }


        } catch (Exception e) {
            log.error("Exception in ProfileServiceImpl::createUser message == {}", e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public Response getProfileByAadharNumOrPan(String number) {
        profileRepository.findbyAadharNum(number)
                .ifPresentOrElse((profile) ->
                        {
                            log.info("Inside ProfileServiceImpl::getProfileByAadharNumOrPan ==nThe account details are fetched");
                            response.setMessage(Constants.SUCCESS);
                            response.setCode(Constants.SUCCESS_CODE);
                            response.setData(profile);
                        },
                        () -> {
                            log.info("The Given account details are not found in the database");
                            response.setMessage(Constants.DATA_NOT_FOUND);
                            response.setCode(Constants.DATA_NOT_FOUND_CODE);
                        }
                );

        return response;
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
