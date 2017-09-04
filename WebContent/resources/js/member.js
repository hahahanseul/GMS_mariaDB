/**
 * Member JavaScript
 */
var app=(function(){
	var init=function(ctx){
		session.init(ctx);
		onCreate();
	};
	var onCreate=function(){
		setContentView();
		location.href=ctx()+"/home.do";		
	};
	var setContentView=function(){
	};
	var ctx=function(){
		return session.getPath('ctx');
	};
	var js =function(){
		return session.getPath('js');
	};
	var img =function(){
		return session.getPath('img');
	};
	var css =function(){
		return session.getPath('css');
	};
	return {
		init:init,
		ctx: ctx,
		js : js,
		img: img,
		css: css,
		onCreate:onCreate
	};
})();
var session=(function(){
	var init=function(ctx){
		sessionStorage.setItem('ctx', ctx);
		sessionStorage.setItem('js',ctx+ '/resources/js')
		sessionStorage.setItem('img',ctx+ '/resources/img')
		sessionStorage.setItem('css',ctx+ '/resources/css')
	};
	var getPath=function(x){
		return sessionStorage.getItem(x);
	};
	return {
		init : init,
		getPath : getPath
	};
})();
var member=(function(){
	var init=function(){
		$('#loginBtn').on('click',function(){
			alert('로그인버튼 click');
			if($('#login_id').val() === ""){
				alert('ID구다사이');
				return false;
			}
			if($('#login_pw').val() === ""){
				alert('password구다사이');
				return false;
			}
			$('#login_form').attr('action',app.ctx()+"/common.do");
			$('#login_form').attr('method','post');
			return true;
		});
	};
	var mainLoad=function(){
		
	};
	return {
		init:init
	};
})();
var memberDetail=(function(){
	var init = function(){
		onCreate();
	};
	var onCreate = function(){
		setContentView();
			$('#updateBtn').on('click',function(){
				alert('정보수정 클릭됐어');
				sessionStorage.setItem('id', $('#stu_id').val());
				sessionStorage.setItem('name', $('#stu_name').text());
				sessionStorage.setItem('phone',$('#stu_phone').text());
				sessionStorage.setItem('email',$('#stu_email').text());
				sessionStorage.setItem('title',$('#stu_title').text());
				alert(sessionStorage.getItem('phone'));
				controller.moveTo('member','member_update');
			});
	};
	var setContentView = function(){
		alert('member Detail 이라능');
	};
	return {
		init : init
	};
})();
var memberUpdate=(function(){
	var init = function(){
		onCreate();
	};
	var onCreate = function(){
		setContentView();	
	};
	var setContentView = function(){
		alert('member Update라능');
		var id = sessionStorage.getItem('id');
		var phone = sessionStorage.getItem('phone');
		var email = sessionStorage.getItem('email');
		var password = $('#confirm').val();
		$('#phone').attr('placeholder',phone);
		$('#email').attr('placeholder',email);
		$('#confirmBtn').on('click',function(){
			alert('수정할 아이디::  '  + id);
			controller.updateStudent(id,$('#email').val());
		});
		
	};
	return {
		init : init
	};
})();	
var controller=(function(){
	var init=function(){
		
	};
	var moveTo = function(dir,page){
		location.href=app.ctx()+'/' + dir + ".do?action=move&page=" +page;
	};
	var logout = function(dir, page){
		location.href=app.ctx()+"/"+dir+".do?action=logout&page="+page;
	}
	var deleteTarget=function(dir){
		prompt(dir +'의 ID는?');
	}
	var list =function (dir,page,pageNumber){
		location.href=app.ctx()+"/"+dir+".do?action=list&page="+page+"&pageNumber=" + pageNumber;
	};
	var updateStudent= function(id,email){
		alert('수정할 ID는 ' + id);
		location.href=app.ctx()+"/member.do?action=update&page=member_update&id=" +id+"&email="+email;
	};
	var deleteStudent=function(id){
		alert('삭제할 ID는 ' + id);
		location.href=app.ctx()+"/member.do?action=delete&page=member_list" ;
	};
	var detailStudent=function(id){
		location.href=app.ctx()+"/member.do?action=detail&page=member_detail&id="+id;
	};
	var searchStudent= function (){
		var search=document.getElementById('search').value;
		if(search === ""){
			alert('검색어를 입력하세요');
			return false;
		};
		alert('입력한 검색어:::::   ' + search);
		location.href=app.ctx()+"/member.do?action=search&page=member_list&search="+search;
	};
	return{
		init : init,
		moveTo : moveTo,
		logout:logout,
		list:list,
		deleteTarget:deleteTarget,
		detailStudent:detailStudent,
		updateStudent:updateStudent,
		searchStudent:searchStudent
	};
})();
var main=(function(){
	var init=function(){
		onCreate();
	};
	var onCreate = function(){
		setContentView();
		$('.list-group li').eq(0).on('click',function(){
			controller.moveTo('member','member_add');
		});
		$('.list-group li').eq(1).on('click',function(){
			controller.list('member','member_list','1');
		});
		$('.list-group li').eq(2).on('click',function(){
			controller.detailStudent(prompt('당신은 현석씨입니까?'));
		});
		$('.list-group li').eq(3).on('click',function(){
			controller.moveTo('member','member_search');
		});
		$('.list-group li').eq(4).on('click',function(){
			controller.deleteTarget('member');
		});
	};
	var setContentView=function(){
		var $u1=$("#main_ul_stu");
		var $u2=$("#main_ul_grade");
		var $u3=$("#main_ul_board");
		$u1.addClass("list-group");
		$u2.addClass("list-group");
		$u3.addClass("list-group");
		$('.list-group').children().addClass("list-group-item");
	}
	return{
		init : init
	};
})();
var navbar=(function(){
	var init=function(){
		onCreate();
	};
	var onCreate = function(){
		setContentView();
		$('.dropdown-menu a').eq(0).on('click',function(){
			alert('0');
			controller.moveTo('member','member_add');
		});
		$('.dropdown-menu a').eq(1).on('click',function(){
			alert('1');
			controller.list('member','member_list','1');
		});
		$('.dropdown-menu a').eq(2).on('click',function(){
			alert('2');
			controller.moveTo('member','member_search');
		});
		$('.dropdown-menu a').eq(3).on('click',function(){
			alert('3');
			controller.moveTo('member','member_list');
		});
		$('.dropdown-menu a').eq(3).on('click',function(){
			alert('3');
			controller.moveTo('member','member_list');
		});
		$('.dropdown-menu a').eq(4).on('click',function(){
			alert('4');
			controller.deleteTarget('member');
		});
	};
	var setContentView=function(){
		var $u1=$("#nav_ul_stu");
		var $u2=$("#nav_ul_grade");
		var $u3=$("#nav_ul_board");
		$u1.addClass("dropdown-menu");
		$u2.addClass("dropdown-menu");
		$u3.addClass("dropdown-menu");
	}
	return{init : init};
})();
var home=(function(){
	var init=function(){
		onCreate();
	};
	var onCreate = function(){
		setContentView();
	};
	var setContentView=function(){
	}
	return{init : init};
})();
