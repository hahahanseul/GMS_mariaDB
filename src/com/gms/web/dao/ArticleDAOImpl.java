package com.gms.web.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gms.web.constant.DB;
import com.gms.web.constant.SQL;
import com.gms.web.constant.Vendor;
import com.gms.web.domain.ArticleBean;
import com.gms.web.factory.DatabaseFactory;

public class ArticleDAOImpl implements ArticleDAO {
	public static ArticleDAOImpl getInstance() {
		return new ArticleDAOImpl();
	}
	private ArticleDAOImpl() {}
	@Override
	public String insert(ArticleBean bean) {
		String rs = "";
		try {
			PreparedStatement pstmt = DatabaseFactory.createDatabase(Vendor.ORACLE, DB.USERNAME, DB.PASSWORD).getConnection().prepareStatement(SQL.BOARD_INSERT);
			pstmt.setString(1,bean.getId());
			pstmt.setString(2,bean.getTitle());
			pstmt.setString(3,bean.getContent());
			rs = String.valueOf(pstmt.executeUpdate());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public List<ArticleBean> selectAll() {
		List<ArticleBean> list = new ArrayList<>();
		try {
			ResultSet rs = DatabaseFactory.createDatabase(Vendor.ORACLE, DB.USERNAME, DB.PASSWORD).getConnection().prepareStatement(SQL.BOARD_LIST).executeQuery();
			ArticleBean article=null;
			while(rs.next()) {
				article = new ArticleBean();
				article.setArticleSeq(Integer.parseInt(rs.getString(DB.BOARD_SEQ)));
				article.setId(rs.getString(DB.BOARD_ID));
				article.setTitle(rs.getString(DB.TITLE));
				article.setContent(rs.getString(DB.BOARD_CONTENT));
				article.setRegdate(rs.getString(DB.BOARD_ID));
				article.setId(rs.getString(DB.BOARD_REGDATE));
				article.setHitcount(rs.getInt(DB.BOARD_HITCNT));
				
				list.add(article);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<ArticleBean> selectById(String id) {
		List<ArticleBean> list = new ArrayList<>();
		try {
			PreparedStatement pstmt = DatabaseFactory.createDatabase(Vendor.ORACLE, DB.USERNAME, DB.PASSWORD).getConnection().prepareStatement(SQL.BOARD_FINDBYID);
			pstmt.setString(1,id);
			ResultSet rs =pstmt.executeQuery();
			ArticleBean article=null;
			while(rs.next()) {
				article = new ArticleBean();
				article.setArticleSeq(Integer.parseInt(rs.getString(DB.BOARD_SEQ)));
				article.setId(rs.getString(DB.BOARD_ID));
				article.setTitle(rs.getString(DB.TITLE));
				article.setContent(rs.getString(DB.BOARD_CONTENT));
				article.setRegdate(rs.getString(DB.BOARD_ID));
				article.setId(rs.getString(DB.BOARD_REGDATE));
				article.setHitcount(rs.getInt(DB.BOARD_HITCNT));
				
				list.add(article);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public ArticleBean selectBySeq(int seq) {
		ArticleBean article = new ArticleBean();
		try {
			PreparedStatement pstmt = DatabaseFactory.createDatabase(Vendor.ORACLE, DB.USERNAME, DB.PASSWORD).getConnection().prepareStatement(SQL.BOARD_FINDBYSEQ);
			pstmt.setString(1,String.valueOf(seq));
			ResultSet rs= pstmt.executeQuery();
			if(rs.next()) {
				article.setArticleSeq(Integer.parseInt(rs.getString(DB.BOARD_SEQ)));
				article.setId(rs.getString(DB.BOARD_ID));
				article.setTitle(rs.getString(DB.TITLE));
				article.setContent(rs.getString(DB.BOARD_CONTENT));
				article.setRegdate(rs.getString(DB.BOARD_ID));
				article.setId(rs.getString(DB.BOARD_REGDATE));
				article.setHitcount(rs.getInt(DB.BOARD_HITCNT));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return article;
	}
	@Override
	public String count() {
		String res ="";
		try {
			ResultSet rs =DatabaseFactory.createDatabase(Vendor.ORACLE, DB.USERNAME, DB.PASSWORD).getConnection().prepareStatement(SQL.BOARD_COUNT).executeQuery();
			if(rs.next()) {
				res=rs.getString("count");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	@Override
	public String update(ArticleBean bean) {
		String rs ="";
		ArticleBean temp= selectBySeq(bean.getArticleSeq());
		String title = (bean.getTitle().equals(""))? temp.getTitle() :bean.getTitle(); 
		String content = (bean.getContent().equals(""))? temp.getContent() :bean.getContent(); 
		try {
			PreparedStatement pstmt = DatabaseFactory.createDatabase(Vendor.ORACLE, DB.USERNAME, DB.PASSWORD).getConnection().prepareStatement(SQL.BOARD_UPDATE);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, String.valueOf(bean.getArticleSeq()));
			rs = String.valueOf(pstmt.executeUpdate());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public String delete(int seq) {
		String rs="";
		try {
			PreparedStatement pstmt = DatabaseFactory.createDatabase(Vendor.ORACLE, DB.USERNAME, DB.PASSWORD).getConnection().prepareStatement(SQL.BOARD_DELETE);
			pstmt.setString(1, String.valueOf(seq));
			rs=String.valueOf(pstmt.executeUpdate());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
}
