package com.example.dusTmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//TODO: memberVO, boardVO auditing 적용 시킬것.
@SpringBootApplication
@EnableJpaAuditing  //JpaAuditing 활성화
@PropertySource(value = {"classpath:config.properties"})
public class DusTmqApplication {

	public static void main(String[] args) {
		SpringApplication.run(DusTmqApplication.class, args);
	}


//	/**
//	 *  사용자 언어 환경을 설정해주기 위한 bean 설정
//	 */
//	@Bean
//	public LocaleResolver localeResolver(){
//		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
//		sessionLocaleResolver.setDefaultLocale(Locale.KOREA);
//		return sessionLocaleResolver;
//	}

//	@Bean
//	public MessageSource messageSource(){
//		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//		messageSource.setBasenames("message","message_ko", "message_en","nonce");
//		messageSource.setDefaultEncoding("utf-8");
//		return messageSource;
//	}

}
