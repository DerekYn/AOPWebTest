package com.test.aop;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Component
/*
   XML에서 빈을 만드는 대신에 클래스명 앞에 @Component 어노테이션을 적어주면
     해당 클래스(지금은 AOPController)는 bean으로 자동 등록된다.
     그리고 bean의 이름(첫글자는 소문자)은 해당 클래스명(지금은 AOPController)이 된다.
     지금은 bean의 이름은 aOPController 이 된다.
 */
public class AOPController {

	@Autowired
	ICore core;
	
	@RequestMapping(value="/get.action", method={RequestMethod.GET}) 
	public String get(HttpServletRequest request) {
		
		Random rnd = new Random();
		
		int seq = rnd.nextInt(3) + 1;
		// rnd.nextInt(3) 은 0,1,2 중에 하나를 랜덤하게 가져오는 것이다.
		// rnd.nextInt(10) 은 0,1,2,3,4,5,6,7,8,9 중에 하나를 랜덤하게 가져오는 것이다.
		
		String data = core.get(seq);
		
		request.setAttribute("data", data);
		
		return "aop";
	}
	
	/////////////////////////////////////////////////////////
	
	// 로그인한 회원만 사용하는 전용 기능에 대해서 
	// 인증받지 못한 사용자는 특정 페이지로 리디렉트로 시키는 보조업무를 구현한다.
	
	// 시작페이지
	@RequestMapping(value="/index.action", method={RequestMethod.GET}) 
	public String index() {
		
		return "index";
	}
	
	
	// 이 페이지를 통해서 접속하면 인증을 받았다고 가정한다.
	@RequestMapping(value="/auth/member.action", method={RequestMethod.GET}) 
	public String authmember(HttpSession session) {
		
		// 로그인 성공!!
		session.setAttribute("loginuser", "hongkd");
		
		return "auth/member";
	}
	
	
	// 이 페이지를 통해서 접속하면 인증을 받지 않았다고 가정한다.
	@RequestMapping(value="/auth/anonymous.action", method={RequestMethod.GET}) 
	public String authanonymous(HttpSession session) {
		
		// 로그아웃!!
		// session.removeAttribute("loginuser");
		session.invalidate();
		
		return "auth/anonymous";
	}
	
	
	// 회원전용 페이지 1
	@RequestMapping(value="/member/info.action", method={RequestMethod.GET}) 
	public String memberinfo(HttpServletRequest request, HttpServletResponse response) { 
				
		return "member/info";
	}
	
	
	// 회원전용 페이지 2
	@RequestMapping(value="/member/my.action", method={RequestMethod.GET}) 
	public String membermy(HttpServletRequest request, HttpServletResponse response) {
				
		return "member/my";
	}
	
	
	////////////////////  퀴즈    /////////////////////////////
	
	@RequestMapping(value="/quiz/quiz1.action", method={RequestMethod.GET}) 
	public String quiz1(HttpServletRequest request) {
		
		String name = core.getName();
		
		request.setAttribute("name", name);
		
		return "quiz/quiz1";
	}
	
	
	@RequestMapping(value="/quiz/quiz2.action", method={RequestMethod.GET}) 
	public String quiz2(HttpServletRequest request) {
		
		String address = core.getAddress();
		
		request.setAttribute("address", address);
		
		return "quiz/quiz2";
	}
	
	
	
	
}
