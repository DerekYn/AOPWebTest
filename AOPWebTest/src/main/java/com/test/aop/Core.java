package com.test.aop;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
/*
   XML에서 빈을 만드는 대신에 클래스명 앞에 @Component 어노테이션을 적어주면
     해당 클래스(지금은 AOPController)는 bean으로 자동 등록된다.
     그리고 bean의 이름(첫글자는 소문자)은 해당 클래스명(지금은 AOPController)이 된다.
     지금은 bean의 이름은 aOPController 이 된다.
 */
public class Core implements ICore {

	// DB(DAO)로 데이터를 가져왔다고 가정하에 진행하겠습니다.
	
	@Override
	public String get(int seq) {
		
		String data = "";
		
		if(seq == 1)
			data = "한석규";
		else if(seq == 2)
			data = "두석규";
		else if(seq == 3)
			data = "세석규";
		
		return data;
	}

	
	private int num;
	private String name;
	private String address;
	
	public void setNum(int num) {
		this.num = num;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String getName() {
		
		// 랜덤한 값 1~5까지 값을 발생
		
		/*
		    랜덤한 값이  1,2,4,5 이라면  
		  name 은 "일지매","이지매","사지매","오지매"
		  
		    랜덤한 값이 3 이라면
		  name 은 "three man"
		 */
		
		return name;
	}

	@Override
	public String getAddress() {
		
		// 랜덤한 값 1~5까지 값을 발생
		
		/*
		   랜덤한 값이 1,2,4,5 이라면  
		  address 는 "일산시","이산시","사산시","오산시"
		  
		   랜덤한 값이 3 이라면
		  address 는  "three city"
		 */
		
		return address;
	}

}
