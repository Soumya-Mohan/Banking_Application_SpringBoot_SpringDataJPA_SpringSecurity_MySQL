package com.AccountService.Model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserprofileDto implements Serializable {

    @JsonProperty("firstName")
    private String fName;
    @JsonProperty("lastName")
    private String lName;
    @JsonProperty("middleName")
    private String mName;
    @JsonFormat(pattern = "dd-mm-yyyy", shape = JsonFormat.Shape.ANY)
    private Date DOB;
    @JsonProperty("panNum")
    private String PAN_Num;
    @JsonProperty("aadharNum")
    private String Aadhar_num;
    @JsonProperty("primaryMobileNum")
    private String p_mobile_num;
    @JsonProperty("primaryEmailId")
    private String p_email_id;
    @JsonProperty("primaryAdd1")
    private String p_add1;
    @JsonProperty("primaryAdd2")
    private String p_add2;
    @JsonProperty("primaryPinCode")
    private String p_pincode;
    @JsonProperty("primaryState")
    private String p_state;
    @JsonProperty("primaryDistrict")
    private String p_district;
    @JsonProperty("communicationAdd1")
    private String c_add1;
    @JsonProperty("communicationAdd2")
    private String c_add2;
    @JsonProperty("communicationPinCode")
    private String c_pincode;
    @JsonProperty("communicationState")
    private String c_state;
    @JsonProperty("communicationDistrict")
    private String c_district;
    @JsonProperty("occupation")
    private String occupation;
    @JsonProperty("occupationId")
    private String Occupation_id;
    @JsonProperty("secondaryMobileNum")
    private String s_mobile_num;
    @JsonProperty("secondaryEmailId")
    private String s_email_id;

}
