package com.example.icoach.service;

import com.example.icoach.model.Donation;
import com.example.icoach.model.VolunteerApplication;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportService {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public byte[] exportDonationsToPdf(List<Donation> donations) throws Exception {
        Document doc = new Document(PageSize.A4.rotate());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(doc, out);
        doc.open();

        com.lowagie.text.Font titleFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 16, com.lowagie.text.Font.BOLD);
        com.lowagie.text.Font headerFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 10, com.lowagie.text.Font.BOLD, Color.WHITE);
        com.lowagie.text.Font cellFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 9);

        Paragraph title = new Paragraph("Iowa Community Center - Donation Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        doc.add(title);
        doc.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1.5f, 2f, 2f, 1.5f, 1.5f, 2f, 1.5f});

        String[] headers = {"Receipt#", "Donor Name", "Email", "Amount", "Type", "Status", "Date"};
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, headerFont));
            cell.setBackgroundColor(new Color(27, 58, 107));
            cell.setPadding(5);
            table.addCell(cell);
        }

        for (Donation d : donations) {
            table.addCell(new Phrase(d.getReceiptNumber() != null ? d.getReceiptNumber() : "", cellFont));
            table.addCell(new Phrase(d.getDonorFullName(), cellFont));
            table.addCell(new Phrase(d.getDonorEmail() != null ? d.getDonorEmail() : "", cellFont));
            table.addCell(new Phrase(d.getAmount() != null ? "$" + d.getAmount() : "", cellFont));
            table.addCell(new Phrase(d.getDonationType().name(), cellFont));
            table.addCell(new Phrase(d.getStatus().name(), cellFont));
            table.addCell(new Phrase(d.getCreatedAt() != null ? d.getCreatedAt().format(FMT) : "", cellFont));
        }

        doc.add(table);
        doc.close();
        return out.toByteArray();
    }

    public byte[] exportDonationsToExcel(List<Donation> donations) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Donations");

        org.apache.poi.ss.usermodel.CellStyle headerStyle = workbook.createCellStyle();
        org.apache.poi.ss.usermodel.Font hFont = workbook.createFont();
        hFont.setBold(true);
        headerStyle.setFont(hFont);
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        org.apache.poi.ss.usermodel.Row header = sheet.createRow(0);
        String[] cols = {"Receipt#", "First Name", "Last Name", "Email", "Phone", "Amount", "Type", "Status", "Purpose", "Date"};
        for (int i = 0; i < cols.length; i++) {
            org.apache.poi.ss.usermodel.Cell cell = header.createCell(i);
            cell.setCellValue(cols[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowIdx = 1;
        for (Donation d : donations) {
            org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(d.getReceiptNumber() != null ? d.getReceiptNumber() : "");
            row.createCell(1).setCellValue(d.getDonorFirstName() != null ? d.getDonorFirstName() : "");
            row.createCell(2).setCellValue(d.getDonorLastName() != null ? d.getDonorLastName() : "");
            row.createCell(3).setCellValue(d.getDonorEmail() != null ? d.getDonorEmail() : "");
            row.createCell(4).setCellValue(d.getDonorPhone() != null ? d.getDonorPhone() : "");
            row.createCell(5).setCellValue(d.getAmount() != null ? d.getAmount().doubleValue() : 0);
            row.createCell(6).setCellValue(d.getDonationType().name());
            row.createCell(7).setCellValue(d.getStatus().name());
            row.createCell(8).setCellValue(d.getPurpose() != null ? d.getPurpose() : "");
            row.createCell(9).setCellValue(d.getCreatedAt() != null ? d.getCreatedAt().format(FMT) : "");
        }

        for (int i = 0; i < cols.length; i++) sheet.autoSizeColumn(i);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();
    }

    public byte[] exportVolunteersToExcel(List<VolunteerApplication> volunteers) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Volunteers");

        org.apache.poi.ss.usermodel.Row header = sheet.createRow(0);
        String[] cols = {"First Name", "Last Name", "Email", "Phone", "Status", "Hours Logged", "Date Applied"};
        for (int i = 0; i < cols.length; i++) {
            header.createCell(i).setCellValue(cols[i]);
        }

        int rowIdx = 1;
        for (VolunteerApplication v : volunteers) {
            org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(v.getFirstName() != null ? v.getFirstName() : "");
            row.createCell(1).setCellValue(v.getLastName() != null ? v.getLastName() : "");
            row.createCell(2).setCellValue(v.getEmail() != null ? v.getEmail() : "");
            row.createCell(3).setCellValue(v.getPhone() != null ? v.getPhone() : "");
            row.createCell(4).setCellValue(v.getStatus().name());
            row.createCell(5).setCellValue(v.getHoursLogged() != null ? v.getHoursLogged() : 0);
            row.createCell(6).setCellValue(v.getCreatedAt() != null ? v.getCreatedAt().format(FMT) : "");
        }

        for (int i = 0; i < cols.length; i++) sheet.autoSizeColumn(i);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return out.toByteArray();
    }
}
