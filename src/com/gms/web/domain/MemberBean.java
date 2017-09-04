package com.gms.web.domain;
import java.io.Serializable;

import lombok.Data;


@Data
public class MemberBean implements Serializable{
	private static final long serialVersionUID=1L;
	private String id, pw, name,ssn, regdate,birthday,gender,email,phone,major,profile; 

}