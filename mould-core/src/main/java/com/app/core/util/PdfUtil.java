package com.app.core.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Log4j2
public class PdfUtil {
    /**
     * 将文本内容写入PDF文件
     *
     * @param fileName 文件全路径
     * @param content  文件内容
     */
    public static void writePDF(final String fileName, final String content) {
        Document document = null;
        OutputStream out = null;
        try {
            // 检查文件目录是否存在，不存在则创建目录
            File file = new File(FilenameUtils.getFullPath(fileName));
            if (!file.exists())
                file.mkdirs();

            // 初始化文件输出流
            out = new FileOutputStream(fileName);
            // 初始化ITextPDF文件流
            document = new Document();

            // 装载文件输出流
            PdfWriter.getInstance(document, out);
            document.open();

            Paragraph graph = new Paragraph(content);
            document.add(graph);

            if (log.isInfoEnabled())
                log.info("生成PDF文件成功，文件路径：{}", fileName);
        } catch (Exception e) {
            log.error("将文本内容写入PDF文件出错", e);
        } finally {
            // 关闭PDF文件流
            if (document != null)
                document.close();

            // 关闭文件输出流
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}