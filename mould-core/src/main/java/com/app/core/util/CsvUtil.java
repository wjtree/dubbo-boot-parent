package com.app.core.util;

import com.app.core.code.Symbol;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Log4j2
public class CsvUtil {
    /**
     * 将数据写入CSV
     *
     * @param fileName
     * @param data
     */
    public static void writeCSV(final String fileName, final List<String[]> data) {
        CSVWriter writer = null;
        try {
            // 校验文件后缀名
            String csvName = StringUtils.join(FilenameUtils.getFullPath(fileName), FilenameUtils.getBaseName(fileName), Symbol.DOT.getSymbol().concat("csv"));
            // 创建文件所在目录
            File file = new File(FilenameUtils.getFullPath(csvName));
            if (!file.exists())
                file.mkdirs();

            writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(csvName), StandardCharsets.UTF_8.name()), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            writer.writeAll(data);
        } catch (Exception e) {
            log.error("将数据写入CSV出错", e);
        } finally {
            if (null != writer) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    log.error("关闭文件输出流出错", e);
                }
            }
        }
    }

    /**
     * 读取CSV数据
     *
     * @param fileName
     * @return
     */
    public static List<String[]> readCSV(final String fileName) {
        List<String[]> list = null;
        CSVReader reader = null;
        try {
            String csvName = StringUtils.join(FilenameUtils.getFullPath(fileName), FilenameUtils.getBaseName(fileName), Symbol.DOT.getSymbol().concat("csv"));

            reader = new CSVReaderBuilder(new InputStreamReader(new FileInputStream(csvName), StandardCharsets.UTF_8.name())).build();

            list = reader.readAll();
        } catch (Exception e) {
            log.error("读取CSV数据出错", e);
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("关闭文件输入流出错", e);
                }
            }
        }
        return list;
    }
}