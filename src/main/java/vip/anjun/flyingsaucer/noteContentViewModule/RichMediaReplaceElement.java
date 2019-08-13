package vip.anjun.flyingsaucer.noteContentViewModule;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfFileSpecification;
import com.itextpdf.text.pdf.PdfWriter;
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
            float currentHeight = writer.getVerticalPosition(true);
            int height = box.getHeight();
            writer.addAnnotation(PdfAnnotation.createScreen(writer,
                    new Rectangle(contentBounds.x, currentHeight-contentBounds.height, 400f, currentHeight), "Fox and Dog", fs,
                    "video/mpeg", true));


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
