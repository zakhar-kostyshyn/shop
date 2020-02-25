package com.example.shopapplication.Services;

import com.example.shopapplication.Model.ShoppingCart;
import com.example.shopapplication.Model.User;
import com.example.shopapplication.Repositories.ShoppingCartRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
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

    public byte[] generatePdfBill(){

        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {

            PdfWriter writer = PdfWriter.getInstance(document, /*new FileOutputStream("BillOfSale.pdf")*/ byteArrayOutputStream);
            document.open();
            document.add(new Paragraph(""));

            //Add Image
            Image image = Image.getInstance(resourceLoader.getResource("classpath:logo.png").getFile().toURI().toString());

            image.setAbsolutePosition(100f, 550f);
            image.scaleAbsolute(200,200);

            document.add(image);

            document.close();
            writer.close();

            return byteArrayOutputStream.toByteArray();

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
