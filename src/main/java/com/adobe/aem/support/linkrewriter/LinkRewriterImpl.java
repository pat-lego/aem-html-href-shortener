package com.adobe.aem.support.linkrewriter;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;

import com.day.cq.rewriter.linkchecker.Link;
import com.day.cq.rewriter.linkchecker.LinkCheckerSettings;
import com.day.cq.rewriter.pipeline.RequestRewriter;

@Component(service = RequestRewriter.class)
public class LinkRewriterImpl implements RequestRewriter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Attributes rewrite(String arg0, Attributes arg1, LinkCheckerSettings arg2) {
        return arg1;
    }

    @Override
    public String rewriteLink(Link arg0, LinkCheckerSettings arg1) {
        Optional<String> href = Optional.ofNullable(arg0.getHref());
        if (!href.isPresent()) {
            return null;
        }
        Pattern pattern = Pattern.compile("\\/content\\/wknd\\/language-masters\\/en\\/(.+)\\/content\\/dam\\/(.+)");
        Matcher matcher = pattern.matcher(href.get());
        
        if(!matcher.matches()) {
            return null;
        }

        return "/" + matcher.group(1) + "/" + matcher.group(2);
    }

}
