package vip.anjun.flyingsaucer.noteContentViewModule;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextReplacedElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.render.RenderingContext;

import java.awt.*;
import java.io.IOException;

public class RichMediaReplaceElement implements ITextReplacedElement {
    private String filePath = "";

    public RichMediaReplaceElement(String filePath) {
        this.filePath = filePath;
    }

    private Point _location = new Point(0, 0);

    @Override
    public void paint(RenderingContext c, ITextOutputDevice outputDevice, BlockBox box) {
        java.awt.Rectangle contentBounds = box.getContentAreaEdge(box.getAbsX(), box.getAbsY(), c);
        PdfContentByte cb = outputDevice.getCurrentPage();

        PdfWriter writer = outputDevice.getWriter();
        PdfFileSpecification fs = null;
        try {
            fs = PdfFileSpecification.fileEmbedded(writer, filePath, "foxdog.mpg", null);
            // 当前位置
            int height1 = c.getPage().getHeight(c);
            float currentHeight = writer.getVerticalPosition(true);
            int height = box.getHeight();
            PdfAnnotation annotation = PdfAnnotation.createScreen(writer,
                    new Rectangle(contentBounds.x, currentHeight - contentBounds.height, 400f, currentHeight), "Fox and Dog", fs,
                    "video/mpeg", true);
//            cb.addAnnotation(annotation,true);
            writer.addAnnotation(annotation);


            cb.setRGBColorStroke(0x00, 0x00, 0xFF);
            cb.rectangle(100, 100, 100, 100);
            cb.stroke();
            cb.resetRGBColorStroke();

            outputDevice.drawString("richmedia中文",contentBounds.x,contentBounds.y,null);

            outputDevice.drawRect(contentBounds.x,contentBounds.y,2000,2000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getIntrinsicWidth() {
        return 200;
    }

    @Override
    public int getIntrinsicHeight() {
        return 200;
    }

    @Override
    public Point getLocation() {
        return _location;
    }

    @Override
    public void setLocation(int x, int y) {
        _location = new Point(x, y);
    }

    @Override
    public void detach(LayoutContext c) {

    }

    @Override
    public boolean isRequiresInteractivePaint() {
        return false;
    }

    @Override
    public boolean hasBaseline() {
        return false;
    }

    @Override
    public int getBaseline() {
        return 0;
    }
}
