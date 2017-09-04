package com.gms.web.service;


import java.util.List;

import com.gms.web.dao.ArticleDAOImpl;
import com.gms.web.domain.ArticleBean;

public class ArticleServiceImpl implements ArticleService {
	public static ArticleServiceImpl getInstance() {
		return new ArticleServiceImpl();
	}
	private ArticleServiceImpl() {}

	@Override
	public String write(ArticleBean bean) {
		return (ArticleDAOImpl.getInstance().insert(bean).equals("1")) ? "글이 등록되었습니다.":"등록실패";
	}

	@Override
	public String count() {
		return ArticleDAOImpl.getInstance().count();
	}

	@Override
	public List<ArticleBean> list() {
		return ArticleDAOImpl.getInstance().selectAll();
	}

	@Override
	public List<ArticleBean> findById(String id) {
		return ArticleDAOImpl.getInstance().selectById(id);
	}

	@Override
	public ArticleBean findBySeq(int seq) {
		return ArticleDAOImpl.getInstance().selectBySeq(seq);
	}

	@Override
	public String modify(ArticleBean bean) {
		return ArticleDAOImpl.getInstance().update(bean).equals("1")?"업데이트됐습니다":"다시요";
	}

	@Override
	public String remove(int seq) {
		return (ArticleDAOImpl.getInstance().delete(seq).equals("1")) ? "삭제성공했슈" : "삭제실패";
	}

}
