package org.curriki.xwiki.plugin.asset;

import com.xpn.xwiki.api.Document;
import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;
import com.xpn.xwiki.objects.BaseObject;
import com.xpn.xwiki.objects.PropertyInterface;
import com.xpn.xwiki.objects.BaseElement;
import com.xpn.xwiki.doc.XWikiDocument;

import java.util.List;

public class CurrikiDocument extends Document {
    public CurrikiDocument(XWikiDocument doc, XWikiContext context) {
        super(doc, context);
    }

    protected void assertCanEdit() throws XWikiException {
        if (!hasAccessLevel("edit")) {
            throw new XWikiException(XWikiException.MODULE_XWIKI_ACCESS, XWikiException.ERROR_XWIKI_ACCESS_DENIED, "User needs appropriate rights");
        }
    }

    protected boolean copyProperty(BaseObject fromObj, BaseObject destObj, String key) throws XWikiException {
        PropertyInterface prop = fromObj.get(key);

        if (prop != null) {
            PropertyInterface newProp = (PropertyInterface) ((BaseElement)prop).clone();
            newProp.setObject(destObj);
            destObj.safeput(key, newProp);
            return true;
        }

        return false;
    }

    public Boolean hasA(String objectClass) {
        if (doc.getObjectNumbers(objectClass) == 0) {
            // No objects
        } else {
            // Work around a bug XWIKI-1624
            // TODO: Remove the work-around once XWIKI-1624 is fixed
            List objList = doc.getObjects(objectClass);
            for (Object obj : objList) {
                if (obj != null) {
                    return true;
                }
            }
        }

        return false;
    }

    public Integer countOfObjects(String objectClass) {
        int count = 0;
        if (doc.getObjectNumbers(objectClass) == 0) {
            // Nothing to count
        } else {
            // Work around a bug XWIKI-1624
            // TODO: Remove the work-around once XWIKI-1624 is fixed
            List objList = doc.getObjects(objectClass);
            for (Object obj : objList) {
                if (obj != null) {
                    count++;
                }
            }
        }

        return count;
    }
}
