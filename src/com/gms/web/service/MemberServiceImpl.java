package com.gms.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.gms.web.command.Command;
import com.gms.web.dao.MemberDAOImpl;
import com.gms.web.domain.MajorBean;
import com.gms.web.domain.MemberBean;
import com.gms.web.domain.StudentBean;
import com.gms.web.service.MemberService;

public class MemberServiceImpl implements MemberService {
	public static MemberServiceImpl getInstance() {
		return new MemberServiceImpl();
		}
	private MemberServiceImpl() {}
	@Override
	public String addMember(Map<String, Object> map) {
		System.out.println("Member Service 진입");
		MemberBean m = (MemberBean)map.get("member");
		System.out.println("넘어온 회원의 이름:" + m.toString());
		@SuppressWarnings("unchecked")
		List<MajorBean> list = (List<MajorBean>)map.get("major");
		System.out.println("넘어온 수강과목:" + list);
		MemberDAOImpl.getInstance().insert(map);
		return MemberDAOImpl.getInstance().insert(map);
	}
	@Override
	public List<?> list(Command cmd) {
		return MemberDAOImpl.getInstance().selectAll(cmd);
	}
	@Override
	public String countMembers(Command cmd) {
		return String.valueOf(MemberDAOImpl.getInstance().countMembers(cmd));
	}
	@Override
	public List<?> findByName(Command cmd) {
		System.out.println("findByName(" + cmd.getSearch()+")");
		return MemberDAOImpl.getInstance().selectByName(cmd);
	}
	@Override
	public StudentBean findById(Command cmd) {
		return MemberDAOImpl.getInstance().selectById(cmd);
	}

	@Override
	public String modify(MemberBean param) {
		String msg="";
		return msg;	
	}
	@Override
	public String remove(Command cmd) {
		String msg="";
		return msg;
		
	}
	@Override
	public Map<String,Object> login(MemberBean member) {
		Map<String,Object> map = new HashMap<>();
		Command cmd = new Command();
		cmd.setSearch(member.getId());
		MemberBean m = MemberDAOImpl.getInstance().login(cmd);
		String page = (m != null) ?( (member.getPw().equals(m.getPw())) ? "main" :"login_fail") :  "join";
		if(page.equals("main")) {
			System.out.println("로그인성공");
		}else {
			System.out.println("로그인실패");
		}
		map.put("page", page);
		map.put("user", m);
		return map;
	}
}