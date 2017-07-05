package cn.gson.crm;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class CrmApplication {

	public static void main(String[] args) {
		System.out.println(DigestUtils.sha256Hex("0000"));
		SpringApplication.run(CrmApplication.class, args);
	}
}
