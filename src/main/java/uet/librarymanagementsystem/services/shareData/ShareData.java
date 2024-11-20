package uet.librarymanagementsystem.services.shareData;

import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.transactions.Transaction;

public class ShareData {
    private static Document documentShare;
    private static Transaction transactionShare;

    public static Document getDocumentShare() {
        return documentShare;
    }

    public static void setDocumentShare(Document documentShare) {
        ShareData.documentShare = documentShare;
    }

    public static Transaction getTransactionShare() {
        return transactionShare;
    }

    public static void setTransactionShare(Transaction transactionShare) {
        ShareData.transactionShare = transactionShare;
    }
}
