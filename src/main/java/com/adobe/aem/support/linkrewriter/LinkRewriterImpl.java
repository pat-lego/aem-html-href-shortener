package com.adobe.aem.support.linkrewriter;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;

import com.day.cq.rewriter.linkchecker.Link;
import com.day.cq.rewriter.linkchecker.LinkCheckerSettings;
import com.day.cq.rewriter.pipeline.RequestRewriter;

@Component(service = RequestRewriter.class)
@Designate(ocd = LinkRewriterImpl.LinkRewriterConfig.class)
public class LinkRewriterImpl implements RequestRewriter {

    @ObjectClassDefinition(name = "Adobe - Link Rewriter Config", description = "OSGi Service providing link rewriting capabilities")
    public @interface LinkRewriterConfig {

        @AttributeDefinition(name = "Regex", description = "Regex that if matches the href will be used to create the outgoing URL")
        String regex() default StringUtils.EMPTY;

        @AttributeDefinition(name = "Output URL", description = "The output url matching data coming from the regex url, needs to be in format like such \"/$1/dam/$2\"")
        String out() default StringUtils.EMPTY;

    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private LinkRewriterConfig config;

    @Activate
    public LinkRewriterImpl(LinkRewriterConfig config) {
        this.config = config;
    }

    @Override
    public Attributes rewrite(String arg0, Attributes arg1, LinkCheckerSettings arg2) {
        return arg1;
    }

    public boolean hasRegex() {
        try {
            Pattern.compile(config.regex());
            return true && !config.regex().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String rewriteLink(Link arg0, LinkCheckerSettings arg1) {
        Optional<String> href = Optional.ofNullable(arg0.getHref());
        if (!href.isPresent() && !hasRegex()) {
            return null;
        }
        Pattern pattern = Pattern.compile(config.regex());
        Matcher matcher = pattern.matcher(href.get());

        if (!matcher.matches()) {
            return null;
        }
        int groupSize = matcher.groupCount();
        String result = config.out();
        for (int i = 0; i < groupSize; i++) {
            result = result.replace("$" + (i+1), matcher.group(i +1));
        }
        return result;
    }

}
