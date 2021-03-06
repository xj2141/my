package com.example.demo.utils;

import com.example.demo.domain.Flow;
import com.example.demo.domain.PreRecord;
import com.example.demo.domain.SufRecord;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class POIUtil {
    //导出排尿日志
    public static void exportRecord(String[] info, List<PreRecord> pre, List<List<SufRecord>> suf, HttpServletResponse response) {
        //创建一个Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建标题行和数据样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        HSSFCellStyle centerStyle = workbook.createCellStyle();
        centerStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        centerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        centerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        centerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 设置边框
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setWrapText(true);
        //创建表单
        HSSFSheet sheet = workbook.createSheet("排尿日志");
        sheet.setColumnWidth(0, 5 * 256);
        sheet.setColumnWidth(1, 15 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 10 * 256);
        sheet.setColumnWidth(4, 10 * 256);
        sheet.setColumnWidth(5, 20 * 256);
        //创建大表头
        HSSFRow r;
        HSSFCell c;
        r = sheet.createRow(0);
        c = r.createCell(0);
        c.setCellValue("个人信息");
        c.setCellStyle(centerStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
        r = sheet.createRow(1);
        c = r.createCell(0);
        c.setCellValue("姓名：" + info[0]);
        c.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));
        c = r.createCell(2);
        c.setCellValue("性别：" + info[1]);
        c.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 3));
        c = r.createCell(4);
        c.setCellValue("年龄：" + info[2]);
        c.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 5));
        r = sheet.createRow(2);
        c = r.createCell(0);
        c.setCellValue("排尿日志");
        c.setCellStyle(titleStyle);
        sheet.createRow(3).createCell(0).setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 0, 5));

        for (int i = 0; i < pre.size(); i++) {
            //空行
            int last = sheet.getPhysicalNumberOfRows();
            if (last != 4) {
                sheet.createRow(last).createCell(0);
                sheet.createRow(last + 1).createCell(0);
                sheet.addMergedRegion(new CellRangeAddress(last, last + 1, 0, 5));
            }
            PreRecord preRecord = pre.get(i);
            int first = sheet.getPhysicalNumberOfRows();
            //创建标题行
            HSSFRow r0 = sheet.createRow(first);
            HSSFCell c0 = r0.createCell(0);
            c0.setCellValue("日期：" + preRecord.getRecordDate());
            c0.setCellStyle(cellStyle);
            r0.createCell(1).setCellStyle(cellStyle);
            r0.createCell(2).setCellStyle(cellStyle);
            r0.createCell(3).setCellStyle(cellStyle);
            r0.createCell(4).setCellStyle(cellStyle);
            r0.createCell(5).setCellStyle(cellStyle);
            sheet.addMergedRegion(new CellRangeAddress(first, first, 0, 5));

            HSSFRow r1 = sheet.createRow(first + 1);
            HSSFCell c1 = r1.createCell(0);
            c1.setCellValue("起床时间：" + preRecord.getWakeTime());
            c1.setCellStyle(cellStyle);
            r1.createCell(1).setCellStyle(cellStyle);
            r1.createCell(2).setCellStyle(cellStyle);
            sheet.addMergedRegion(new CellRangeAddress(first + 1, first + 1, 0, 2));
            HSSFCell c2 = r1.createCell(3);
            c2.setCellValue("入睡时间：" + preRecord.getSleepTime());
            c2.setCellStyle(cellStyle);
            r1.createCell(4).setCellStyle(cellStyle);
            r1.createCell(5).setCellStyle(cellStyle);
            sheet.addMergedRegion(new CellRangeAddress(first + 1, first + 1, 3, 5));

            HSSFRow r2 = sheet.createRow(first + 2);
            HSSFCell d0 = r2.createCell(0);
            d0.setCellValue("次数");
            d0.setCellStyle(cellStyle);
            HSSFCell d1 = r2.createCell(1);
            d1.setCellValue("排尿时间");
            d1.setCellStyle(cellStyle);
            HSSFCell d2 = r2.createCell(2);
            d2.setCellValue("尿量");
            d2.setCellStyle(cellStyle);
            HSSFCell d3 = r2.createCell(3);
            d3.setCellValue("是否尿急");
            d3.setCellStyle(cellStyle);
            HSSFCell d4 = r2.createCell(4);
            d4.setCellValue("是否漏尿");
            d4.setCellStyle(cellStyle);
            HSSFCell d5 = r2.createCell(5);
            d5.setCellValue("备注");
            d5.setCellStyle(cellStyle);

            List<SufRecord> list = suf.get(i);
            //遍历集合进行存储数据
            for (int j = 0; j < list.size(); j++) {
                SufRecord sufRecord = list.get(j);
                HSSFRow row = sheet.createRow(j + first + 3);
                HSSFCell temp;
                temp = row.createCell(0);
                temp.setCellValue(j + 1);
                temp.setCellStyle(cellStyle);
                temp = row.createCell(1);
                temp.setCellValue(sufRecord.getFlowTime());
                temp.setCellStyle(cellStyle);
                temp = row.createCell(2);
                temp.setCellValue(sufRecord.getCapacity());
                temp.setCellStyle(cellStyle);
                temp = row.createCell(3);
                temp.setCellValue(sufRecord.getFlowFastYN());
                temp.setCellStyle(cellStyle);
                temp = row.createCell(4);
                temp.setCellValue(sufRecord.getFlowLeakYN());
                temp.setCellStyle(cellStyle);
                temp = row.createCell(5);
                temp.setCellValue(sufRecord.getRemark());
                temp.setCellStyle(cellStyle);
            }
        }
        try {
            response.setHeader("Content-disposition", "attachment;fileName=" + URLEncoder.encode("排尿日志.xls", "utf-8"));
            response.setContentType("application/octet-stream;charset=utf-8");
            OutputStream ouput = response.getOutputStream();
            workbook.write(ouput);
            ouput.flush();
            ouput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取尿流检测数据
    public static List<Flow> importFlow(MultipartFile file) {
        List<Flow> list = new ArrayList<>();
        Flow flow = null;
        try {
            //创建一个workbook对象
            HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
            //获取表单
            HSSFSheet sheet = workbook.getSheetAt(0);
            //获取表单中的行数
            int numberOfRows = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < numberOfRows; i++) {
                //跳过标题行
                if (i == 0) {
                    continue;//跳过标题行
                }
                //获取行
                HSSFRow row = sheet.getRow(i);
                if (row == null) {
                    continue;//防止数据中间有空行
                }
                flow = new Flow();
                flow.setTime(String.valueOf(row.getCell(0).getNumericCellValue()));
                flow.setRate(String.valueOf(row.getCell(1).getNumericCellValue()));
                list.add(flow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
