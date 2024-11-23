package uet.librarymanagementsystem.services.shareDataServers;

import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.entity.users.User;

public class ShareDataService {
    private static Document documentShare;
    private static Transaction transactionShare;
    private static String idStudentShare;

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

    public static String getIdStudentShare() {
        return idStudentShare;
    }

    public static void setIdStudentShare(String idStudentShare) {
        ShareDataService.idStudentShare = idStudentShare;
    }
}
