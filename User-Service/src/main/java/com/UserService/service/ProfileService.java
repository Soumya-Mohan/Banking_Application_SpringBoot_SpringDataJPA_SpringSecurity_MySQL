package com.UserService.service;

import com.UserService.Model.dto.Response;
import com.UserService.Model.dto.UserprofileDto;

public interface ProfileService {
    Response createUser(UserprofileDto userprofileDto);

    Response getProfileByAadharNumOrPan(String number);

    Response getAllByPagination(int pageNo, int pageSize);
}
