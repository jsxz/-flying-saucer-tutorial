package vip.anjun.flyingsaucer.noteContentViewModule;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.EmptyReplacedElement;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextReplacedElement;
import org.xhtmlrenderer.pdf.ITextReplacedElementFactory;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.render.RenderingContext;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
@Slf4j
public class ENMLReplacedElementFactory implements ReplacedElementFactory {
    private final ITextReplacedElementFactory delegate;

    public ENMLReplacedElementFactory(ITextReplacedElementFactory delegate) {
        this.delegate = delegate;
    }

    @Override
    public ReplacedElement createReplacedElement(LayoutContext c, BlockBox box, UserAgentCallback uac, int cssWidth, int cssHeight) {
        ReplacedElement toReturn = null;

//        log.info("Element:" + box.getElement().getNodeName());
//        log.info( "Element content:" + box.getElement().getNodeValue());
        Element element = box.getElement();
        if ("enmedia".equals(element.getNodeName())) {
            String src = element.getAttribute("src");
            toReturn = new RichMediaReplaceElement(src);
            return toReturn;
        }

        if (null == toReturn) {
//            LOG.log(Level.INFO, "delegating to next factory:" + box.getElement().getNodeName());
            toReturn = delegate.createReplacedElement(c, box, uac, cssWidth, cssHeight);
        }
        return toReturn;
    }

    @Override
    public void reset() {
        delegate.reset();
    }

    @Override
    public void remove(Element e) {
        delegate.remove(e);
    }

    @Override
    public void setFormSubmissionListener(FormSubmissionListener listener) {
        delegate.setFormSubmissionListener(listener);
    }
}
