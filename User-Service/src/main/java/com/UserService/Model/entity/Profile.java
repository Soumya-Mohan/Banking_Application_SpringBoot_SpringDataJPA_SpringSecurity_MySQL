package com.UserService.Model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProfile;
    private String fName;
    private String lName;
    private String mName;
    private Date DOB;
    private String PAN_Num;
    private String Aadhar_num;
    private String p_mobile_num;
    private String p_email_id;
    private String p_add1;
    private String p_add2;
    private String p_pincode;
    private String p_state;
    private String p_district;
    private String c_add1;
    private String c_add2;
    private String c_pincode;
    private String c_state;
    private String c_district;
    private String occupation;
    private String Occupation_id;
    private String s_mobile_num;
    private String s_email_id;
    @CreationTimestamp
    private Timestamp createdTime;
    @CreationTimestamp
    private Timestamp lastModifiedTime;


}
