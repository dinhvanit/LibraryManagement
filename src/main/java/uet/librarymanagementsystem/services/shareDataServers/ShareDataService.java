package uet.librarymanagementsystem.services.shareDataServers;

import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.transactions.Transaction;

public class ShareDataService {
    private static Document documentShare;
    private static Transaction transactionShare;

    public static Document getDocumentShare() {
        return documentShare;
    }

    public static void setDocumentShare(Document documentShare) {
        ShareDataService.documentShare = documentShare;
    }

    public static Transaction getTransactionShare() {
        return transactionShare;
    }

    public static void setTransactionShare(Transaction transactionShare) {
        ShareDataService.transactionShare = transactionShare;
    }
}
