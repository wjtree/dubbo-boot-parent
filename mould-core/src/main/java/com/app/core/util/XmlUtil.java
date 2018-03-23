package com.app.core.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class XmlUtil {
    /**
     * 转换不带CDDATA的XML
     *
     * @return
     * @
     */
    private static XStream getXStream() {
        // 实例化XStream基本对象
        XStream xstream = new XStream(new DomDriver(StandardCharsets.UTF_8.name(), new NoNameCoder() {
            // 不对特殊字符进行转换，避免出现重命名字段时的“双下划线”
            public String encodeNode(String name) {
                return name;
            }
        }));
        // 忽视XML与JAVABEAN转换时，XML中的字段在JAVABEAN中不存在的部分
        xstream.ignoreUnknownElements();
        return xstream;
    }

    /**
     * 转换带CDDATA的XML
     *
     * @return
     * @
     */
    private static XStream getXStreamWithCData() {
        // 实例化XStream扩展对象
        XStream xstream = new XStream(new XppDriver() {
            // 扩展xstream，使其支持CDATA块
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    // 不对特殊字符进行转换，避免出现重命名字段时的“双下划线”
                    public String encodeNode(String name) {
                        return name;
                    }

                    // 对所有xml节点的转换都增加CDATA标记
                    protected void writeText(QuickWriter writer, String text) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    }
                };
            }
        });
        // 忽视XML与JAVABEAN转换时，XML中的字段在JAVABEAN中不存在的部分
        xstream.ignoreUnknownElements();
        return xstream;
    }

    /**
     * 以压缩的方式输出XML
     *
     * @param obj
     * @return
     */
    public static String toCompressXml(Object obj) {
        XStream xstream = getXStream();
        StringWriter sw = new StringWriter();
        // 识别obj类中的注解
        xstream.processAnnotations(obj.getClass());
        // 设置JavaBean的类别名
        xstream.aliasType("xml", obj.getClass());
        xstream.marshal(obj, new CompactWriter(sw));
        return sw.toString();
    }

    /**
     * 以格式化的方式输出XML
     *
     * @param obj
     * @return
     */
    public static String toXml(Object obj) {
        XStream xstream = getXStream();
        // 识别obj类中的注解
        xstream.processAnnotations(obj.getClass());
        // 设置JavaBean的类别名
        xstream.aliasType("xml", obj.getClass());
        return xstream.toXML(obj);
    }

    /**
     * 转换成JavaBean
     *
     * @param xmlStr
     * @param cls
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBean(String xmlStr, Class<T> cls) {
        XStream xstream = getXStream();
        // 识别cls类中的注解
        xstream.processAnnotations(cls);
        // 设置JavaBean的类别名
        xstream.aliasType("xml", cls);
        T t = (T) xstream.fromXML(xmlStr);
        return t;
    }

    /**
     * 以格式化的方式输出XML
     *
     * @param obj
     * @return
     */
    public static String toXmlWithCData(Object obj) {
        XStream xstream = getXStreamWithCData();
        // 识别obj类中的注解
        xstream.processAnnotations(obj.getClass());
        // 设置JavaBean的类别名
        xstream.aliasType("xml", obj.getClass());
        return xstream.toXML(obj);
    }

    /**
     * 转换成JavaBean
     *
     * @param xmlStr
     * @param cls
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBeanWithCData(String xmlStr, Class<T> cls) {
        XStream xstream = getXStreamWithCData();
        // 识别cls类中的注解
        xstream.processAnnotations(cls);
        // 设置JavaBean的类别名
        xstream.alias("xml", cls);
        T t = (T) xstream.fromXML(xmlStr);
        return t;
    }
}