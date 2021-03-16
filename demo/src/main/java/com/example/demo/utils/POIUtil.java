package com.example.demo.utils;

import com.example.demo.domain.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.charts.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.charts.XSSFChartLegend;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

//导入导出
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

    //导出尿流检测信息
    public static void exportTest(String[] info, String[] date, List<List<Test>> test, List<List<List<Flow>>> flow, HttpServletResponse response) {
        //创建一个Excel文档
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建标题行和数据样式
        XSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        XSSFCellStyle centerStyle = workbook.createCellStyle();
        centerStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        centerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        centerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        centerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        XSSFCellStyle cenStyle = workbook.createCellStyle();
        cenStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        cenStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cenStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        cenStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        XSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 设置边框
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setWrapText(true);

        XSSFCellStyle ztStyle = workbook.createCellStyle();
        ztStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        ztStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 设置边框
        ztStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        ztStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        ztStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        ztStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 创建字体对象
        Font ztFont = workbook.createFont();
        ztFont.setFontName("宋体");
        ztFont.setBold(true);
        ztFont.setFontHeightInPoints((short) 11);
        ztStyle.setFont(ztFont);

        //创建表单
        XSSFSheet sheet = workbook.createSheet("尿流检测信息");
        sheet.setColumnWidth(0, 8 * 256);
        sheet.setColumnWidth(1, 8 * 256);
        sheet.setColumnWidth(2, 8 * 256);
        sheet.setColumnWidth(3, 8 * 256);
        sheet.setColumnWidth(4, 8 * 256);
        sheet.setColumnWidth(5, 8 * 256);
        sheet.setColumnWidth(6, 8 * 256);
        sheet.setColumnWidth(7, 8 * 256);
        sheet.setColumnWidth(8, 8 * 256);
        sheet.setColumnWidth(9, 8 * 256);
        sheet.setColumnWidth(10, 8 * 256);
        sheet.setColumnWidth(11, 8 * 256);
        /* Create a drawing canvas on the sheet */
        XSSFDrawing xlsx_drawing = sheet.createDrawingPatriarch();
        //创建大表头
        Row r;
        Cell c;
        r = sheet.createRow(0);
        c = r.createCell(0);
        c.setCellValue("个人信息");
        c.setCellStyle(centerStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
        r = sheet.createRow(1);
        c = r.createCell(0);
        c.setCellValue("姓名：" + info[0]);
        c.setCellStyle(headerStyle);
        r.createCell(1).setCellStyle(headerStyle);
        r.createCell(2).setCellStyle(headerStyle);
        r.createCell(3).setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));
        c = r.createCell(4);
        c.setCellValue("性别：" + info[1]);
        c.setCellStyle(headerStyle);
        r.createCell(5).setCellStyle(headerStyle);
        r.createCell(6).setCellStyle(headerStyle);
        r.createCell(7).setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 7));
        c = r.createCell(8);
        c.setCellValue("年龄：" + info[2]);
        c.setCellStyle(headerStyle);
        r.createCell(9).setCellStyle(headerStyle);
        r.createCell(10).setCellStyle(headerStyle);
        r.createCell(11).setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 8, 11));
        r = sheet.createRow(2);
        c = r.createCell(0);
        c.setCellValue("尿流检测信息");
        c.setCellStyle(titleStyle);
        sheet.createRow(3).createCell(0).setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 0, 11));

        for (int i = 0; i < date.length; i++) {
            //空行
            int last = sheet.getPhysicalNumberOfRows();
            if (i != 0) {
                sheet.createRow(last).createCell(0);
                sheet.createRow(last + 1).createCell(0);
                sheet.createRow(last + 2).createCell(0);
                sheet.createRow(last + 3).createCell(0);
                sheet.addMergedRegion(new CellRangeAddress(last, last + 3, 0, 11));
            }
            int first = sheet.getPhysicalNumberOfRows();
            //创建标题行
            Row r0 = sheet.createRow(first);
            Cell c0 = r0.createCell(0);
            c0.setCellValue("日期：" + date[i]);
            c0.setCellStyle(cenStyle);
            sheet.createRow(first + 1).createCell(0).setCellStyle(cenStyle);
            sheet.addMergedRegion(new CellRangeAddress(first, first + 1, 0, 11));

            List<Test> tests = test.get(i);
            for (int j = 0; j < tests.size(); j++) {
                Test tempTest = tests.get(j);
                int end = sheet.getPhysicalNumberOfRows();
                if (j != 0) {
                    sheet.createRow(end).createCell(0);
                    sheet.createRow(end + 1).createCell(0);
                    sheet.addMergedRegion(new CellRangeAddress(end, end + 1, 0, 11));
                }
                int begin = sheet.getPhysicalNumberOfRows();
                Cell c1;
                Cell c2;
                Cell c3;
                Cell c4;
                Row r1 = sheet.createRow(begin);
                c1 = r1.createCell(0);
                c1.setCellValue("基本信息");
                c1.setCellStyle(ztStyle);
                r1.createCell(1).setCellStyle(ztStyle);
                r1.createCell(2).setCellStyle(ztStyle);
                r1.createCell(3).setCellStyle(ztStyle);
                r1.createCell(4).setCellStyle(ztStyle);
                r1.createCell(5).setCellStyle(ztStyle);
                r1.createCell(6).setCellStyle(ztStyle);
                r1.createCell(7).setCellStyle(ztStyle);
                r1.createCell(8).setCellStyle(ztStyle);
                r1.createCell(9).setCellStyle(ztStyle);
                r1.createCell(10).setCellStyle(ztStyle);
                r1.createCell(11).setCellStyle(ztStyle);
                sheet.addMergedRegion(new CellRangeAddress(begin, begin, 0, 11));
                Row r2 = sheet.createRow(begin + 1);
                c1 = r2.createCell(0);
                c1.setCellValue("检测次数：第" + String.valueOf(j + 1) + "次");
                c1.setCellStyle(cellStyle);
                r2.createCell(1).setCellStyle(cellStyle);
                r2.createCell(2).setCellStyle(cellStyle);
                r2.createCell(3).setCellStyle(cellStyle);
                sheet.addMergedRegion(new CellRangeAddress(begin + 1, begin + 1, 0, 3));
                c2 = r2.createCell(4);
                c2.setCellValue("检测时间：" + tempTest.getTestTime());
                c2.setCellStyle(cellStyle);
                r2.createCell(5).setCellStyle(cellStyle);
                r2.createCell(6).setCellStyle(cellStyle);
                r2.createCell(7).setCellStyle(cellStyle);
                sheet.addMergedRegion(new CellRangeAddress(begin + 1, begin + 1, 4, 7));
                c3 = r2.createCell(8);
                c3.setCellValue("检测地点：" + tempTest.getTestPlace());
                c3.setCellStyle(cellStyle);
                r2.createCell(9).setCellStyle(cellStyle);
                r2.createCell(10).setCellStyle(cellStyle);
                r2.createCell(11).setCellStyle(cellStyle);
                sheet.addMergedRegion(new CellRangeAddress(begin + 1, begin + 1, 8, 11));

                Row r3 = sheet.createRow(begin + 2);
                c1 = r3.createCell(0);
                c1.setCellValue("参数");
                c1.setCellStyle(ztStyle);
                r3.createCell(1).setCellStyle(ztStyle);
                r3.createCell(2).setCellStyle(ztStyle);
                r3.createCell(3).setCellStyle(ztStyle);
                r3.createCell(4).setCellStyle(ztStyle);
                r3.createCell(5).setCellStyle(ztStyle);
                r3.createCell(6).setCellStyle(ztStyle);
                r3.createCell(7).setCellStyle(ztStyle);
                r3.createCell(8).setCellStyle(ztStyle);
                r3.createCell(9).setCellStyle(ztStyle);
                r3.createCell(10).setCellStyle(ztStyle);
                r3.createCell(11).setCellStyle(ztStyle);
                sheet.addMergedRegion(new CellRangeAddress(begin + 2, begin + 2, 0, 11));
                Row r4 = sheet.createRow(begin + 3);
                c1 = r4.createCell(0);
                c1.setCellValue("尿量(VV)：" + tempTest.getVv() + "ml");
                c1.setCellStyle(cellStyle);
                r4.createCell(1).setCellStyle(cellStyle);
                r4.createCell(2).setCellStyle(cellStyle);
                sheet.addMergedRegion(new CellRangeAddress(begin + 3, begin + 3, 0, 2));
                c2 = r4.createCell(3);
                c2.setCellValue("最大尿流率(Qmax)：" + tempTest.getQmax() + "ml/s");
                c2.setCellStyle(cellStyle);
                r4.createCell(4).setCellStyle(cellStyle);
                r4.createCell(5).setCellStyle(cellStyle);
                sheet.addMergedRegion(new CellRangeAddress(begin + 3, begin + 3, 3, 5));
                c3 = r4.createCell(6);
                c3.setCellValue("尿流时间(FT)：" + tempTest.getFt() + "s");
                c3.setCellStyle(cellStyle);
                r4.createCell(7).setCellStyle(cellStyle);
                r4.createCell(8).setCellStyle(cellStyle);
                sheet.addMergedRegion(new CellRangeAddress(begin + 3, begin + 3, 6, 8));
                c4 = r4.createCell(9);
                c4.setCellValue("达峰时间(TQmax)：" + tempTest.getTqmax() + "s");
                c4.setCellStyle(cellStyle);
                r4.createCell(10).setCellStyle(cellStyle);
                r4.createCell(11).setCellStyle(cellStyle);
                sheet.addMergedRegion(new CellRangeAddress(begin + 3, begin + 3, 9, 11));

                Row r5 = sheet.createRow(begin + 4);
                c1 = r5.createCell(0);
                c1.setCellValue("结论");
                c1.setCellStyle(ztStyle);
                r5.createCell(1).setCellStyle(ztStyle);
                r5.createCell(2).setCellStyle(ztStyle);
                r5.createCell(3).setCellStyle(ztStyle);
                r5.createCell(4).setCellStyle(ztStyle);
                r5.createCell(5).setCellStyle(ztStyle);
                r5.createCell(6).setCellStyle(ztStyle);
                r5.createCell(7).setCellStyle(ztStyle);
                r5.createCell(8).setCellStyle(ztStyle);
                r5.createCell(9).setCellStyle(ztStyle);
                r5.createCell(10).setCellStyle(ztStyle);
                r5.createCell(11).setCellStyle(ztStyle);
                sheet.addMergedRegion(new CellRangeAddress(begin + 4, begin + 4, 0, 11));
                Row r6 = sheet.createRow(begin + 5);
                c1 = r6.createCell(0);
                c1.setCellValue(tempTest.getConclusion());
                c1.setCellStyle(cellStyle);
                r6.createCell(1).setCellStyle(cellStyle);
                r6.createCell(2).setCellStyle(cellStyle);
                r6.createCell(3).setCellStyle(cellStyle);
                r6.createCell(4).setCellStyle(cellStyle);
                r6.createCell(5).setCellStyle(cellStyle);
                r6.createCell(6).setCellStyle(cellStyle);
                r6.createCell(7).setCellStyle(cellStyle);
                r6.createCell(8).setCellStyle(cellStyle);
                r6.createCell(9).setCellStyle(cellStyle);
                r6.createCell(10).setCellStyle(cellStyle);
                r6.createCell(11).setCellStyle(cellStyle);
                sheet.addMergedRegion(new CellRangeAddress(begin + 5, begin + 5, 0, 11));

                for (int count = 0; count < 14; count++) {
                    sheet.createRow(begin + 6 + count).createCell(0).setCellStyle(cellStyle);
                }
                sheet.addMergedRegion(new CellRangeAddress(begin + 6, begin + 19, 0, 11));

                XSSFSheet tempSheet = workbook.createSheet("临时表");
                Row row = tempSheet.createRow(0);
                row.createCell(0).setCellValue("时间");
                row.createCell(1).setCellValue("尿量率");
                List<Flow> flows = flow.get(i).get(j);
                int length = flows.size();
                for (int k = 0; k < length; k++) {
                    Flow tempFlow = flows.get(k);
                    row = tempSheet.createRow(k + 1);
                    row.createCell(0).setCellValue(Double.parseDouble(tempFlow.getTime()));
                    row.createCell(1).setCellValue(Double.parseDouble(tempFlow.getRate()));
                }
                // 八个参数，前四个表示图片离起始单元格和结束单元格边缘的位置，
                // 后四个表示起始和结束单元格的位置，注意excel的起始位置是0
                XSSFClientAnchor anchor = xlsx_drawing.createAnchor(0, 0, 0, 0, 0, begin + 6, 12, begin + 20);
                /* Create the chart object based on the anchor point */
                XSSFChart line_chart = xlsx_drawing.createChart(anchor);
                /*
                 * Define legends for the line chart and set the position of the legend
                 */
                XSSFChartLegend legend = line_chart.getOrCreateLegend();
                legend.setPosition(LegendPosition.BOTTOM);
                /* Create data for the chart */
                LineChartData data = line_chart.getChartDataFactory().createLineChartData();
                /* Define chart AXIS */
                ChartAxis bottomAxis = line_chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
                bottomAxis.setMinimum(0);
                ValueAxis leftAxis = line_chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
                leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
                //添加数据
                ChartDataSource<Number> xs = DataSources.fromNumericCellRange(tempSheet, new CellRangeAddress(1, length, 0, 0));
                ChartDataSource<Number> ys = DataSources.fromNumericCellRange(tempSheet, new CellRangeAddress(1, length, 1, 1));

                LineChartSeries series = data.addSeries(xs, ys);
                series.setTitle("尿流曲线");//设置序列名称
                line_chart.plot(data, new ChartAxis[]{bottomAxis, leftAxis});
                workbook.removeSheetAt(1);
            }
        }
        try {
            response.setHeader("Content-disposition", "attachment;fileName=" + URLEncoder.encode("尿流检测信息.xls", "utf-8"));
            response.setContentType("application/octet-stream;charset=utf-8");
            OutputStream ouput = response.getOutputStream();
            workbook.write(ouput);
            ouput.flush();
            ouput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //导出医生账户
    public static void exportUser(List<User> users, HttpServletResponse response) {
        //创建一个Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建数据样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setWrapText(true);
        //创建表单
        HSSFSheet sheet = workbook.createSheet("医生账户");
        sheet.setColumnWidth(0, 15 * 256);
        sheet.setColumnWidth(1, 20 * 256);
        //创建表头
        HSSFRow r = sheet.createRow(0);
        HSSFCell c0 = r.createCell(0);
        c0.setCellValue("账号");
        c0.setCellStyle(cellStyle);
        HSSFCell c1 = r.createCell(1);
        c1.setCellValue("密码");
        c1.setCellStyle(cellStyle);
        //遍历集合进行存储数据
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            HSSFCell cell;
            cell = row.createCell(0);
            cell.setCellValue(user.getUsername());
            cell.setCellStyle(cellStyle);
            cell = row.createCell(1);
            cell.setCellValue(user.getPassword());
            cell.setCellStyle(cellStyle);
        }
        try {
            response.setHeader("Content-disposition", "attachment;fileName=" + URLEncoder.encode("医生账户.xls", "utf-8"));
            response.setContentType("application/octet-stream;charset=utf-8");
            OutputStream ouput = response.getOutputStream();
            workbook.write(ouput);
            ouput.flush();
            ouput.close();
        } catch (
                IOException e) {
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
