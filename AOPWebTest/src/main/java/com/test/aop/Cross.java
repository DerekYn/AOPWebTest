package com.test.aop;

import java.io.IOException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
/*
   XML에서 빈을 만드는 대신에 클래스명 앞에 @Component 어노테이션을 적어주면
     해당 클래스(지금은 Cross)는 bean으로 자동 등록된다.
     그리고 bean의 이름(첫글자는 소문자)은 해당 클래스명(지금은 Cross)이 된다.
     지금은 bean의 이름은 cross 이 된다.
 */
public class Cross {

	// 로그인 되어진 회원만 접근이 가능하도록 하는 메소드들을  Pointcut으로 선언한다.
	// - AOPController.memberinfo() : 회원 전용 정보 페이지
	// - AOPController.membermy()   : 회원 전용 개인 페이지
	@Pointcut("execution(* com.test.aop.AOPController.member*(..))")
	public void member() { }
	
	
	// - 인증받지 못한(로그인 안한 사용자)사용자는 회원 전용 페이지에 접속할 수 없다.
	@Before("member()")
	public void memberbefore(JoinPoint joinPoint) {
		
		System.out.println("회원전용 페이지 요청에 한해서 실행합니다.");
		
		// 로그인 유무를 확인하기 위해서 request(첫번째 파라미터)를 통해 session을 얻어온다.
		HttpServletRequest request = (HttpServletRequest)joinPoint.getArgs()[0];
		HttpSession session = request.getSession();
		
		System.out.println(">>> 확인용 로그인유저 ID : " + session.getAttribute("loginuser"));  
		
		// 보조업무 구현
		// - 해당 요청자가 인증받지 못한 상태(로그인을 안했으면)이라면 
		//   회원 전용 페이지에 접근할 수 없기에 다른 페이지로 강제 이동시킨다.
		HttpServletResponse response = (HttpServletResponse)joinPoint.getArgs()[1];
		
	
		if(session.getAttribute("loginuser") == null) {
			try {
				 String msg = "먼저 로그인 하세요~~";
				 String loc = "/aop/index.action";
				
				 request.setAttribute("msg", msg);
				 request.setAttribute("loc", loc);
				 
				 RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/msg.jsp"); 
				 dispatcher.forward(request, response);
				
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}// end of if-------------------------------
	
	
	/*	
		if(session.getAttribute("loginuser") == null) {
			
			try {
				 response.sendRedirect("/aop/index.action");
			} catch (IOException e) {
				 e.printStackTrace();
			}
			
		}// end of if-------------------------------
	*/	
		
	}
	
	
   ////////////////////퀴즈    /////////////////////////////
   @Pointcut("execution(* com.test.aop.Core.get*())")
   public void quiz() { }	
   	
   
   @Before("quiz()")
   public void quizbefore(JoinPoint joinPoint) {
	   
	   Random rnd = new Random();
	   int seq = rnd.nextInt(5) + 1;
	   // seq는 1,2,3,4,5 난수발생값을 가진다.
	   
	   String name = "";
	   String address = "";
	   
	   switch (seq) {
		case 1:
			name = "일지매";
			address = "일산시";
			break;
			
		case 2:
			name = "이지매";
			address = "이산시";
			break;
			
		case 3:
			name = "three man";
			address = "three city";
			break;	
			
		case 4:
			name = "사지매";
			address = "사산시";
			break;
			
		case 5:
			name = "오지매";
			address = "오산시";
			break;
		
	   }
	   
	   Core core = (Core)joinPoint.getTarget();
	   
	   core.setName("번호: "+seq+", 성명: "+name);
	   core.setAddress("번호: "+seq+", 주소: "+address);
	   
   }
   
	
}
