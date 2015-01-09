package com.fsck.k9.helper;


import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.SimpleHtmlSerializer;
import org.htmlcleaner.TagNode;


public class HtmlSanitizer {
    private HtmlSanitizer() {}

    public static String sanitize(String html) {
        HtmlCleaner cleaner = new HtmlCleaner();
        CleanerProperties properties = setUpCleanerProperties(cleaner);

        TagNode rootNode = cleaner.clean(html);

        removeMetaRefresh(rootNode);

        SimpleHtmlSerializer htmlSerializer = new SimpleHtmlSerializer(properties);
        return htmlSerializer.getAsString(rootNode, "UTF8");
    }

    private static CleanerProperties setUpCleanerProperties(HtmlCleaner cleaner) {
        CleanerProperties properties = cleaner.getProperties();

        // See http://htmlcleaner.sourceforge.net/parameters.php for descriptions
        properties.setNamespacesAware(false);
        properties.setAdvancedXmlEscape(false);
        properties.setOmitXmlDeclaration(true);
        properties.setOmitDoctypeDeclaration(false);
        properties.setTranslateSpecialEntities(false);
        properties.setRecognizeUnicodeChars(false);

        return properties;
    }

    private static void removeMetaRefresh(TagNode rootNode) {
        for (TagNode element : rootNode.getElementListByName("meta", true)) {
            String httpEquiv = element.getAttributeByName("http-equiv");
            if (httpEquiv != null && httpEquiv.trim().equalsIgnoreCase("refresh")) {
                element.removeFromTree();
            }
        }
    }
}
