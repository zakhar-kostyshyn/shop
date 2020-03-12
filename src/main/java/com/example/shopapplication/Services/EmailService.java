package com.example.shopapplication.Services;

import com.example.shopapplication.Model.MobilePhone;
import com.example.shopapplication.Model.ShoppingCart;
import com.example.shopapplication.Model.User;
import com.example.shopapplication.Repositories.ShoppingCartRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.swing.table.TableCellEditor;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    public String generateResponseToOrder(User user){

        String message = "";

        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findByShoppingCartIdentifier(user.getUsername());

        return "Dear " + user.getFirstName() + " " + user.getLastName() + "\n" +
                "You did order on buy next mobile phones: " + "\n" +
                shoppingCart.get().toString() + "\n" +
                "We will generate pdf file with bill";
    }

    public byte[] generatePdfBill(String username){

        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findByShoppingCartIdentifier(username);

        try {

            PdfWriter writer = PdfWriter.getInstance(document, /*new FileOutputStream("BillOfSale.pdf")*/ byteArrayOutputStream);
            document.setPageSize(new Rectangle(710,500));
            document.open();
            document.add(new Paragraph(""));

            //Add Image
            Image image = Image.getInstance(resourceLoader.getResource("classpath:logo.png").getFile().toURI().toString());

            image.setAbsolutePosition(465f, 340f);
            image.scaleAbsolute(200,200);

            document.add(image);

            //Add Text Fragment

            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

            document.add(new Paragraph("This is your bill that will need to be paid at any of our stores \n" +
                                             "Main Office - city Ivano-Frankivsk, street Yevhen Konovalets Street, 108 \n" +
                                             "Number - +380960962314 \n" +
                                             "Date - " + new Date()));

            // Add table

            document.add(new Paragraph("\n"));

            double totalPrice = 0;

            PdfPTable table = new PdfPTable(4); // 3 columns.

            PdfPCell cell1 = new PdfPCell(new Paragraph("Mobile Identifier"));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Brand"));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Model"));
            PdfPCell cell4 = new PdfPCell(new Paragraph("Price"));

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);

            for(MobilePhone m : shoppingCart.get().getMobilePhoneList()){
                totalPrice += m.getPrice();
                PdfPCell c1 = new PdfPCell(new Paragraph(m.getMobileIdentifier()));
                PdfPCell c2 = new PdfPCell(new Paragraph(m.getBrand()));
                PdfPCell c3 = new PdfPCell(new Paragraph(m.getModel()));
                PdfPCell c4 = new PdfPCell(new Paragraph(m.getPrice().toString()));

                table.addCell(c1);
                table.addCell(c2);
                table.addCell(c3);
                table.addCell(c4);
            }

            document.add(table);

            document.add(new Paragraph("\n Total Price: " + totalPrice + "$"));

            document.add(new Paragraph("\n" +
                                             "\n" +
                                             "\n" + "\n                               customer's signature  _________________________________"));

            document.close();
            writer.close();

            return byteArrayOutputStream.toByteArray();

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
