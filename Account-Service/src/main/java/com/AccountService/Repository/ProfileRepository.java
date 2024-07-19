package com.AccountService.Repository;

import com.AccountService.Model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    @Query("Select p from Profile p where Aadhar_num=:Id or PAN_Num =:Id")
    Optional<Profile> findbyAadharNum(String Id);
}
