package com.synergy.transaction;

import com.synergy.transaction.util.ReportService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@OpenAPIDefinition(servers = {@Server(url = "/api", description = "Default Server URL")})
public class TransactionApplication {
	@Autowired
	public ReportService reportService;

	public static void main(String[] args) {
		SpringApplication.run(TransactionApplication.class, args);
	}


//	@Test
//	public void tets() throws SQLException {
//		Map<String, Object> parameters33 = new HashMap<>();
//		parameters33.put("idUser", 1L);
////        String pathUrl = "E:\\binar\\binar synergy batCh 5\\Project\\apps\\report\\baranglist.jrxml";
////                String pathUrl = "E:\\binar\\binar synergy batCh 5\\Project\\apps\\report\\Supplier2.jrxml";
//		String pathUrl = "./report/Blank_A4.jrxml";
//		reportService.generate_pdf(parameters33, pathUrl);
//
//	}
}
