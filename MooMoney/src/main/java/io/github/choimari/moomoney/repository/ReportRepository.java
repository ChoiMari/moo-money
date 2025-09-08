package io.github.choimari.moomoney.repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ReportRepository {

    /** 월별 보고서 TXT 저장 */
    public void saveMonthlyReportTxt(String email, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(email + "_monthly_report.txt"))) {
            writer.write(content);
        }
    }

    /** 월별 보고서 CSV 저장 */
    public void saveMonthlyReportCsv(String email, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(email + "_monthly_report.csv"))) {
            writer.write(content.replace(" - ", ",")); // 간단 CSV 변환
        }
    }

    /** 카테고리별 보고서 TXT 저장 */
    public void saveCategoryReportTxt(String email, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(email + "_category_report.txt"))) {
            writer.write(content);
        }
    }

    /** 카테고리별 보고서 CSV 저장 */
    public void saveCategoryReportCsv(String email, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(email + "_category_report.csv"))) {
            writer.write(content.replace(" - ", ",")); // 간단 CSV 변환
        }
    }
}
