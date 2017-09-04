package com.gms.web.service;

import java.util.List;
import com.gms.web.command.Command;
import java.util.Map;
import com.gms.web.domain.MemberBean;
import com.gms.web.domain.StudentBean;

public interface MemberService {
	public String addMember(Map<String, Object> map);
	public List<?> list(Command cmd);
	public List<?> findByName(Command cmd);
	public StudentBean findById(Command cmd);
	public String countMembers(Command cmd);
	public String modify(MemberBean member);
	public String remove(Command cmd);
	public Map<String,Object> login(MemberBean member);
	//MemberBean[] list=new MemberBean[4];
}
