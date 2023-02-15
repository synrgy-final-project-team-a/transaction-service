package com.synergy.transaction.util;

import net.sf.jasperreports.engine.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

@Service
public class ReportService {
    private static Logger logger = LoggerFactory.getLogger(ReportService.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    public byte[] generate_pdf(Map<String, Object> parameters, String reportName) throws SQLException {
        try {
            JasperReport report = JasperCompileManager.compileReport(reportName);
            System.out.println("reportreport=" + reportName);
            JasperPrint jasperPrint = JasperFillManager
                    .fillReport
                            (report,
                                    parameters,
                                    jdbcTemplate.getDataSource().getConnection());
//            JasperPrint jasperPrint = JasperFillManager
//                    .fillReport
//                            (reportName,
//                                    parameters,
//                                    dataSource.getConnection());
//            JasperExportManager.exportReportToPdfFile(jasperPrint, "./cdn/out.pdf");
            JasperExportManager.exportReportToPdfFile(jasperPrint, "Invoice.pdf");
            byte[] result = JasperExportManager.exportReportToPdf(jasperPrint);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            //close koneksi
            jdbcTemplate.getDataSource().getConnection().close();
        }
    }
}
