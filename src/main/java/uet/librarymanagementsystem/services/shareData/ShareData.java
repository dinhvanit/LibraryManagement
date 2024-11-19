package uet.librarymanagementsystem.services.shareData;

import uet.librarymanagementsystem.entity.documents.Document;

public class ShareData {
    private static Document documentShare;

    public static Document getDocumentShare() {
        return documentShare;
    }

    public static void setDocumentShare(Document documentShare) {
        ShareData.documentShare = documentShare;
    }
}
