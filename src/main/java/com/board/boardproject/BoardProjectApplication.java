package com.board.boardproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BoardProjectApplication {
	static {
		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
				new javax.net.ssl.HostnameVerifier(){

					public boolean verify(String hostname,
										  javax.net.ssl.SSLSession sslSession) {
						if (hostname.equals("localhost")) {
							return true;
						}
						return false;
					}
				});
	}

	private static final String PROPERTIES = "spring.config.location=classpath:/ssl.properties";

	public static void main(String[] args) {
		// ssl properties 파일을 프로젝트 실행 시 호출할 수 있도록 함.
		new SpringApplicationBuilder(BoardProjectApplication.class)
				.properties(PROPERTIES)
				.run(args);
		// SpringApplication.run(BoardProjectApplication.class, args);
	}

}
