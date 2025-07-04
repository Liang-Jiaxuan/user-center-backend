package com.yupi.demo2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class Demo2ApplicationTests {

	@Test
	void testDigest() throws NoSuchAlgorithmException {
		String newPassword = DigestUtils.md5DigestAsHex(("abcd" + "mypassword").getBytes());
		System.out.println(newPassword);
	}

	@Test
	void contextLoads() {
	}

}
