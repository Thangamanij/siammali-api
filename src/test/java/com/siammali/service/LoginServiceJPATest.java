package com.siammali.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginServiceJPATest {

	@Autowired
	private LoginService loginService;

	//
	// @Rule
	// public ExpectedException exceptionRule = ExpectedException.none();
	//
/*	@Test
	public void testSaveUserDto() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setEmail("thangamanij@gmail.com");
		userDto.setFirstName("Thangamani");
		userDto.setLastName("Jayabalan");
		AppUser user = loginService.createAccount(userDto);
		System.out.println("User Created ==>" + user.toString());

	}
*/	
}