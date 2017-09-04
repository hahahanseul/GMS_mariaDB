package com.gms.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.synth.SynthSeparatorUI;

import com.gms.web.command.Command;
import com.gms.web.constant.DB;
import com.gms.web.constant.SQL;
import com.gms.web.constant.Vendor;
import com.gms.web.domain.MajorBean;
import com.gms.web.domain.MemberBean;
import com.gms.web.domain.StudentBean;
import com.gms.web.factory.DatabaseFactory;

public class MemberDAOImpl implements MemberDAO {
	public static MemberDAOImpl getInstance() {return new MemberDAOImpl();}
	Connection conn;
	private MemberDAOImpl() {
		conn=null;
	}
	@Override
	public String insert(Map<String, Object> map) {
		String rs = "";
		MemberBean member = (MemberBean)map.get("member");
	/*	MajorBean major=(MajorBean)map.get("major");*/
	   @SuppressWarnings("unchecked")
       List<MajorBean> list=(List<MajorBean>)map.get("major");

		PreparedStatement pstmt=null;
		try {
			conn = DatabaseFactory.createDatabase(Vendor.ORACLE, DB.USERNAME, DB.PASSWORD).getConnection();
			conn.setAutoCommit(false);
			pstmt=conn.prepareStatement(SQL.MEMBER_INSERT);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getPw());
			pstmt.setString(4, member.getBirthday());
			pstmt.setString(5, member.getPhone());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getProfile());
			pstmt.executeUpdate();
			for(int i=0;i<list.size();i++) {
				pstmt=conn.prepareStatement(SQL.MAJOR_INSERT);
				pstmt.setString(1, list.get(i).getMajorId());
				pstmt.setString(2, list.get(i).getTitle());
				pstmt.setString(3, list.get(i).getId());
				pstmt.setString(4, list.get(i).getSubjId());
				rs=String.valueOf(pstmt.executeUpdate());
			}
			System.out.println("DB INSERT결과:" + rs);
			conn.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(conn != null){
				try{
					conn.rollback();
				}catch(SQLException ex){
					e.printStackTrace();
				}
			}
		}
		return rs;
	}

	@Override
	public List<?> selectAll(Command cmd) {
		List<StudentBean> list = new ArrayList<>();
		try {
			conn =DatabaseFactory.createDatabase(Vendor.ORACLE, DB.USERNAME, DB.PASSWORD).getConnection();
			PreparedStatement pstmt= conn.prepareStatement(SQL.STUDENT_LIST);
			pstmt.setString(1, cmd.getStartRow());
			pstmt.setString(2, cmd.getEndRow());
			ResultSet rs=pstmt.executeQuery();
			StudentBean stud = null;
			while(rs.next()) {
				stud = new StudentBean();
				stud.setNum(rs.getString(DB.NUM));
				stud.setId(rs.getString(DB.ID));
				stud.setName(rs.getString(DB.MEMBER_NAME));
				stud.setSsn(rs.getString(DB.MEMBER_SSN));
				stud.setEmail(rs.getString(DB.EMAIL));
				stud.setPhone(rs.getString(DB.PHONE));
				stud.setRegdate(rs.getString(DB.MEMBER_REGDATE));
				stud.setTitle(rs.getString(DB.TITLE));
				list.add(stud);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String countMembers(Command cmd) {
		System.out.println("count(" +cmd.getSearch()+")");
		System.out.println("count(" +cmd.getColumn()+")");
		int res =0;
		try {
			Connection conn = DatabaseFactory.createDatabase(Vendor.ORACLE, DB.USERNAME, DB.PASSWORD).getConnection();
			PreparedStatement pstmt= null;
			if(cmd.getSearch()==null) {
				System.out.println("cmd.getSearch() is null");
				pstmt = conn.prepareStatement(SQL.STUDENT_COUNT);
				pstmt.setString(1, "%");
			}else {
				System.out.println("cmd.getSearch() is not null");
				pstmt = conn.prepareStatement(SQL.STUDENT_COUNT);
				pstmt.setString(1, "%" +cmd.getSearch()+"%");
			}
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				res=Integer.parseInt(rs.getString("count"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return String.valueOf(res);
	}
	@Override
	public List<?> selectByName(Command cmd) {
		System.out.println("selectByName(" + cmd.getSearch()+")");
		System.out.println("selectByName(" + cmd.getColumn()+")");
		List<StudentBean> list = new ArrayList<>();
		try {
			PreparedStatement pstmt= DatabaseFactory.createDatabase(Vendor.ORACLE, DB.USERNAME, DB.PASSWORD).getConnection().prepareStatement(SQL.STUDENT_FINDBYNAME);
			pstmt.setString(1, "%" + cmd.getSearch()+"%");
			ResultSet rs = pstmt.executeQuery();
			StudentBean stud = null;
			while(rs.next()) {
				stud = new StudentBean();
				stud.setNum(rs.getString(DB.NUM));
				stud.setId(rs.getString(DB.ID));
				stud.setName(rs.getString(DB.MEMBER_NAME));
				stud.setSsn(rs.getString(DB.MEMBER_SSN));
				stud.setEmail(rs.getString(DB.EMAIL));
				stud.setPhone(rs.getString(DB.PHONE));
				stud.setRegdate(rs.getString(DB.MEMBER_REGDATE));
				stud.setTitle(rs.getString(DB.TITLE));
				list.add(stud);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public StudentBean selectById(Command cmd) {
		List<StudentBean> list = new ArrayList<>();
		StudentBean stud = null;
		try {
			PreparedStatement pstmt= DatabaseFactory.createDatabase(Vendor.ORACLE, DB.USERNAME, DB.PASSWORD).getConnection().prepareStatement(SQL.STUDENT_FINDBYID);
			pstmt.setString(1, cmd.getSearch());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				stud = new StudentBean();
				stud.setNum(rs.getString(DB.NUM));
				stud.setId(rs.getString(DB.ID));
				stud.setName(rs.getString(DB.MEMBER_NAME));
				stud.setSsn(rs.getString(DB.MEMBER_SSN));
				stud.setEmail(rs.getString(DB.EMAIL));
				stud.setPhone(rs.getString(DB.PHONE));
				stud.setRegdate(rs.getString(DB.MEMBER_REGDATE));
				stud.setTitle(rs.getString(DB.TITLE));
				list.add(stud);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stud;
	}

	@Override
	public String update(MemberBean member) {
		String rs="";
		try {
			PreparedStatement pstmt= DatabaseFactory.createDatabase(Vendor.ORACLE, DB.USERNAME, DB.PASSWORD).getConnection().prepareStatement(SQL.MEMBER_UPDATE);
			pstmt.setString(1, member.getPw());
			pstmt.setString(2, member.getId());
			rs=String.valueOf(pstmt.executeUpdate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public String delete(Command cmd) {
		String rs="";
		try {
			PreparedStatement pstmt= DatabaseFactory.createDatabase(Vendor.ORACLE, DB.USERNAME, DB.PASSWORD).getConnection().prepareStatement(SQL.MEMBER_DELETE);
			pstmt.setString(1, cmd.getSearch());
			rs=String.valueOf(pstmt.executeUpdate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	@Override
	public MemberBean login(Command cmd) {
		MemberBean member = null;
		try {
			PreparedStatement pstmt= DatabaseFactory.createDatabase(Vendor.ORACLE, DB.USERNAME, DB.PASSWORD).getConnection().prepareStatement(SQL.MEMBER_FINDBYID);
			pstmt.setString(1, cmd.getSearch());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				member=new MemberBean();
				member.setId(rs.getString(DB.MEMBER_ID));
				member.setName(rs.getString(DB.MEMBER_NAME));
				member.setPw(rs.getString(DB.MEMBER_PASS));
				member.setBirthday(rs.getString(DB.MEMBER_SSN));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return member;
	}

}
