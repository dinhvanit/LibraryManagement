package uet.librarymanagementsystem.services.PDFServices;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;

public class ExportTransactionToPDF {

    public static void exportTransactionToPDF(String idStudent) throws IOException, DocumentException {
        // Tên file PDF sẽ là idStudent.pdf
        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\uet\\librarymanagementsystem\\PDFfiles" + File.separator + idStudent + ".pdf";


        File pdfFile = new File(filePath);
        if (pdfFile.exists()) {
            System.out.println("File already exists. It will be overwritten: " + filePath);
        } else {
            System.out.println("Creating new file: " + filePath);
        }


        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();


        document.add(new Paragraph("Transaction Report for Student ID: " + idStudent));
        document.add(new Paragraph("\n"));

        // Lấy dữ liệu từ cơ sở dữ liệu
        String query = """
                SELECT id_transaction, name_student,
                       id_document, title_document, author, material, category
                FROM TransactionDocument
                WHERE id_student = ?;
                """;

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, idStudent);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                document.add(new Paragraph("No transactions found for student ID: " + idStudent));
                document.close();
                return;
            }

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);


            table.addCell("ID Transaction");
            table.addCell("Name");
            table.addCell("ID Document");
            table.addCell("Title");
            table.addCell("Author");
            table.addCell("Material");
            table.addCell("Category");

            do {
                table.addCell(rs.getString("id_transaction"));
                table.addCell(rs.getString("name_student"));
                table.addCell(rs.getString("id_document"));
                table.addCell(rs.getString("title_document"));
                table.addCell(rs.getString("author"));
                table.addCell(rs.getString("material"));
                table.addCell(rs.getString("category"));
            } while (rs.next());

            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Đóng tài liệu
        document.close();
    }

}
