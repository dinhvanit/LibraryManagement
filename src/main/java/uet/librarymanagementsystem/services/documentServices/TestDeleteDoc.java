package uet.librarymanagementsystem.services.documentServices;

import uet.librarymanagementsystem.services.userServices.DeleteStudentService;

public class TestDeleteDoc {
    public static void main(String[] args) {
        DeleteDocumentService deleteDocumentService = new DeleteDocumentService();
        deleteDocumentService.deleteDocument("0103000300320004", "BOOK",
                "NON_FICTION", "Anarchism And Ecology", "Graham Purchase");
    }
}
