package vip.anjun.flyingsaucer;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.file.FileSystems;



@Slf4j
public class HtmlTopdf {
    private static final String UTF_8 = "UTF-8";
    private static final String baseDir = "workdir";
    public static void main(String[] args) {
        createPdf(baseDir+"/test.html",baseDir+"/test.pdf" );
    }


    private static void createPdf(String htmlFileName,String out) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding(UTF_8);
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("name", "中文");


        String renderedHtmlContent = templateEngine.process("template", context);



        OutputStream outputStream = null;
        try {
            String xHtml = convertToXhtml(renderedHtmlContent);
            outputStream = new FileOutputStream(out);
            ITextRenderer renderer = new ITextRenderer();
            renderer.getFontResolver().addFont("SIMSUN.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            String baseUrl = FileSystems
                    .getDefault()
                    .getPath("src", "main", "resources")
                    .toUri()
                    .toURL()
                    .toString();
//            renderer.setDocumentFromString(xHtml, baseUrl);

            String htmlStr = FileUtils.readFileToString(new File(htmlFileName), "utf-8");

            String s = convertToXhtml(htmlStr);
            FileWriter fw = new FileWriter("test.html");
            fw.write(s);
            fw.close();
            renderer.setDocumentFromString(s, baseUrl);

            renderer.layout();
            renderer.createPDF(outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static String convertToXhtml(String html) throws UnsupportedEncodingException {
        Tidy tidy = new Tidy();
        tidy.setInputEncoding(UTF_8);
        tidy.setOutputEncoding(UTF_8);
        tidy.setXHTML(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes(UTF_8));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parseDOM(inputStream, outputStream);
        return outputStream.toString(UTF_8);
    }
}
