package com.fsck.k9.helper;


import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class HtmlSanitizerTest {
    @Test
    public void testMetaRefreshRemoval() {
        String metaInHead = "<html>" +
                "<head><meta http-equiv=\"refresh\" content=\"1; URL=http://example.com/\"></head>" +
                "<body>Message</body>" +
                "</html>";
        assertEquals("<html><head></head><body>Message</body></html>", HtmlSanitizer.sanitize(metaInHead));

        String metaBetweenHeadAndBody = "<html>" +
                "<head></head><meta http-equiv=\"refresh\" content=\"1; URL=http://example.com/\">" +
                "<body>Message</body>" +
                "</html>";
        assertEquals("<html><head></head><body>Message</body></html>", HtmlSanitizer.sanitize(metaBetweenHeadAndBody));

        String metaInBody = "<html>" +
                "<head></head>" +
                "<body><meta http-equiv=\"refresh\" content=\"1; URL=http://example.com/\">Message</body>" +
                "</html>";
        assertEquals("<html><head></head><body>Message</body></html>", HtmlSanitizer.sanitize(metaInBody));

        String upperCaseAttributeValue = "<html>" +
                "<head><meta http-equiv=\"REFRESH\" content=\"1; URL=http://example.com/\"></head>" +
                "<body>Message</body>" +
                "</html>";
        assertEquals("<html><head></head><body>Message</body></html>", HtmlSanitizer.sanitize(upperCaseAttributeValue));

        String mixedCaseAttributeValue = "<html>" +
                "<head><meta http-equiv=\"Refresh\" content=\"1; URL=http://example.com/\"></head>" +
                "<body>Message</body>" +
                "</html>";
        assertEquals("<html><head></head><body>Message</body></html>", HtmlSanitizer.sanitize(mixedCaseAttributeValue));

        String noQuotesAroundAttribute = "<html>" +
                "<head><meta http-equiv=refresh content=\"1; URL=http://example.com/\"></head>" +
                "<body>Message</body>" +
                "</html>";
        assertEquals("<html><head></head><body>Message</body></html>", HtmlSanitizer.sanitize(noQuotesAroundAttribute));

        String spacesInAttributeValue = "<html>" +
                "<head><meta http-equiv=\"refresh \" content=\"1; URL=http://example.com/\"></head>" +
                "<body>Message</body>" +
                "</html>";
        assertEquals("<html><head></head><body>Message</body></html>", HtmlSanitizer.sanitize(spacesInAttributeValue));

        String multipleMetaTags = "<html>" +
                "<head><meta http-equiv=\"refresh\" content=\"1; URL=http://example.com/\"></head>" +
                "<body><meta http-equiv=\"refresh\" content=\"1; URL=http://example.com/\">Message</body>" +
                "</html>";
        assertEquals("<html><head></head><body>Message</body></html>", HtmlSanitizer.sanitize(multipleMetaTags));

        String unrelatedMetaTags = "<html>" +
                "<head>" +
                "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">" +
                "<meta http-equiv=\"refresh\" content=\"1; URL=http://example.com/\">" +
                "</head>" +
                "<body>Message</body>" +
                "</html>";
        assertEquals("<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\" /></head>" +
                "<body>Message</body></html>", HtmlSanitizer.sanitize(unrelatedMetaTags));
    }
}
