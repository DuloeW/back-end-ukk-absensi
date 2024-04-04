package org.acme.ukk.absensi.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.ukk.absensi.entity.AbsensiEntity;
import org.acme.ukk.absensi.entity.ClassEntity;
import org.acme.ukk.absensi.entity.enums.GradeEnum;
import org.acme.ukk.absensi.entity.enums.MajorEnum;
import org.acme.ukk.absensi.exceptions.response.ResponseMessage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReportService {
    private List<AbsensiEntity> fetchAbsensi(LocalDate date) {
        return AbsensiEntity.findAllAbsensiByDate(date)
                .stream()
                .collect(Collectors.toList());
    }

    private ClassEntity fetchClass(String major, String grade) {
        var majorParams = MajorEnum.getMajorEnum(major);
        var gradeParams = GradeEnum.getGradeEnum(grade);
        return ClassEntity.findClassByMajorAndGrade(majorParams, gradeParams)
                .orElseThrow(() -> ResponseMessage.classNotFoundException(grade, major));
    }

    private List<LocalDate> getDaysOfMonth() {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfThisMonth = today.withDayOfMonth(1); // Mendapatkan tanggal pertama bulan ini
        LocalDate lastDayOfThisMonth = today.withDayOfMonth(today.lengthOfMonth()); // Mendapatkan tanggal terakhir bulan ini

        LocalDate currentDate = firstDayOfThisMonth;
        while (!currentDate.isAfter(lastDayOfThisMonth)) { // Membuat daftar tanggal dari tanggal awal hingga akhir bulan ini
            dates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }

        return dates;
    }

    public byte[] generateReport(String major, String grade) throws IOException {
        Workbook workBook = new XSSFWorkbook();
        ClassEntity classEntity = fetchClass(major, grade);
        List<LocalDate> dates = getDaysOfMonth();

        Sheet sheet = workBook.createSheet(major.toLowerCase() + "-" + grade.toLowerCase());
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 8000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workBook.createCellStyle();
        headerStyle = styleHeader(headerStyle);

        XSSFFont font = ((XSSFWorkbook) workBook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        headerStyle.setFont(font);


        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("NISN");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Nama");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Status");
        headerCell.setCellStyle(headerStyle);

        for (int i = 0; i < dates.size(); i++) {
            headerCell = header.createCell(i + 3);
            headerCell.setCellValue(dates.get(i).toString());
            headerCell.setCellStyle(headerStyle);
        }

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            sheet.setDefaultRowHeight((short) 600);
            sheet.setDefaultColumnWidth(20);
        }


        CellStyle style = workBook.createCellStyle();
        style = styleData(style);

        CellStyle liburStyle = workBook.createCellStyle();
        liburStyle = styleLibur(liburStyle);

        for (int i = 0; i < classEntity.students.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Cell cell = row.createCell(0);
            cell.setCellValue(classEntity.students.get(i).nisn);
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(classEntity.students.get(i).name);
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(classEntity.students.get(i).status.toString());
            cell.setCellStyle(style);

            for (int j = 0; j < dates.size(); j++) {
                LocalDate currentDate = dates.get(j);
                List<AbsensiEntity> absensiEntities = fetchAbsensi(currentDate);
                if (currentDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) || currentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                    cell = row.createCell(j + 3);
                    cell.setCellValue("LIBUR");
                    cell.setCellStyle(liburStyle);
                    continue;
                }
                for (AbsensiEntity absensiEntity : absensiEntities) {
                    if (absensiEntity.student.id.equals(classEntity.students.get(i).id)) {
                        cell = row.createCell(j + 3);
                        cell.setCellValue(absensiEntity.status.toString());
                        cell.setCellStyle(style);
                    }
                }
            }

        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workBook.write(outputStream);
        workBook.close();

        return outputStream.toByteArray();
    }

    private CellStyle styleData(CellStyle style) {
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setIndention((short) 1);
        return style;
    }

    private CellStyle styleLibur(CellStyle style) {
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        return style;
    }

    private CellStyle styleHeader(CellStyle headerStyle) {
        headerStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        return headerStyle;
    }
}
