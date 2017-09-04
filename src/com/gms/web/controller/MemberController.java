package com.gms.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gms.web.command.Command;
import com.gms.web.constant.Action;
import com.gms.web.domain.MajorBean;
import com.gms.web.domain.MemberBean;
import com.gms.web.domain.StudentBean;
import com.gms.web.proxy.PageProxy;
import com.gms.web.service.MemberService;
import com.gms.web.service.MemberServiceImpl;
import com.gms.web.proxy.BlockHandler;
import com.gms.web.util.DispatcherServlet;
import com.gms.web.proxy.PageHandler;
import com.gms.web.util.ParamsIterator;
import com.gms.web.util.Separator;

@WebServlet("/member.do")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unchecked")
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MemberController 진입");
		Separator.init(request);
		MemberBean member = new MemberBean();
		MemberService service=MemberServiceImpl.getInstance();
		Map<?,?> map = new HashMap<>();
		PageProxy pxy = new PageProxy(request);
		Command cmd = new Command();
		pxy.setPageSize(5);
		pxy.setBlockSize(5);
		switch (Separator.cmd.getAction()) {
		case Action.MOVE:
			DispatcherServlet.send(request, response);
			break;
		case Action.JOIN:
			System.out.println("=== join 진입 ===");	
			map=ParamsIterator.execute(request);
			member.setId((String)map.get("join_id"));
			member.setPw((String)map.get("join_pw"));
			member.setName((String)map.get("join_name"));
			member.setBirthday((String)map.get("join_birth"));
			member.setGender((String)map.get("gender"));
			member.setEmail((String)map.get("join_email"));
			member.setMajor((String)map.get("major"));
			member.setPhone((String)map.get("join_phone"));
			member.setProfile("profile.jpg");
			String[] subjects=((String)map.get("subject")).split(",");
			List<MajorBean> list=new ArrayList<>();
			MajorBean major=null;
			for(int i=0;i<subjects.length;i++){
				major=new MajorBean();
				major.setMajorId(String.valueOf((int)((Math.random() * 9999) + 1000)));
				major.setId((String)map.get("join_id"));
				major.setTitle((String)map.get("join_name"));
				major.setSubjId(subjects[i]);
				list.add(major);
			}
			Map<String,Object>tempMap=new HashMap<>();
			tempMap.put("member", member);
			tempMap.put("major", list);
			String rs=service.addMember(tempMap);
			Separator.cmd.setDir("common");
			Separator.cmd.process();
			System.out.println("컨트롤러 insert결과:" + rs);
			DispatcherServlet.send(request, response);
			break;
		default:System.out.println("FAIL..");break;
		case Action.LIST:
			System.out.println("MemberController <list> 진입");
			pxy.setTheNumberOfRows(Integer.parseInt(service.countMembers(cmd)));
			pxy.setPageNumber(Integer.parseInt(request.getParameter("pageNumber")));
			pxy.execute(BlockHandler.attr(pxy),service.list(PageHandler.attr(pxy)));
			DispatcherServlet.send(request, response);
			break;
		case Action.SEARCH:
			map=ParamsIterator.execute(request);
			cmd=PageHandler.attr(pxy);
			cmd.setColumn("name");
			cmd.setSearch(String.valueOf(map.get("search")));
			pxy.setTheNumberOfRows(Integer.parseInt(service.countMembers(cmd)));
			cmd.setPageNumber(request.getParameter("pageNumber"));
			cmd.setStartRow(PageHandler.attr(pxy).getStartRow());
			cmd.setEndRow(PageHandler.attr(pxy).getEndRow());
			pxy.setPageNumber(Integer.parseInt(cmd.getPageNumber()));
			pxy.execute(BlockHandler.attr(pxy),service.findByName(cmd));
			DispatcherServlet.send(request, response);
			break;
		case Action.UPDATE:
			map=ParamsIterator.execute(request);
			System.out.println("수정할 id:   "+ map.get("id"));
			System.out.println("수정할 email:   "+ map.get("email"));
			cmd.setSearch(request.getParameter("id"));
			//service.modify( service.findById(cmd));
			DispatcherServlet.send(request, response);
			break;
		case Action.DELETE:
			System.out.println("멤버delete진입");
			//service.remove(request.getParameter("id"));
			response.sendRedirect(request.getContextPath() + "/member.do?action=list&page=member_list&pageNumber=1");
			break;
		case Action.DETAIL:
			System.out.println("멤버detail진입");
			cmd.setSearch(request.getParameter("id"));
			request.setAttribute("student", service.findById(cmd));
			DispatcherServlet.send(request, response);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		System.out.println("MemberController Post 진입");
	}

}
