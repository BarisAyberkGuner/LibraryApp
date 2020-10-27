package com.ronins.libraryapp.Backend;


import net.sourceforge.tess4j.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.GenericMultipleBarcodeReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class OCR extends JFrame {

    public static String ocrr(String Filepath) {
/*public static void aaaa(){
            final String[] args =
            if (args.length != 1)
            {
                System.err.println("Usage: " + BarcodeScanner.class.getCanonicalName() + " <image dir>");
                System.exit(1);
            }

            File inputDir = new File(args[0]);
            if (!inputDir.isDirectory())
            {
                System.err.println("Input " + inputDir + " is not a directory");
                System.exit(1);
            }

            for (File input : inputDir.listFiles())
            {
                if (!input.isFile())
                    continue;

                String filename = input.getName();
                try (BufferedInputStream bfin = new BufferedInputStream(new FileInputStream(input)))
                {
                    BufferedImage bfi = ImageIO.read(bfin);
                    if (bfi == null)
                    {
                        System.err.println("Could not read image from " + filename);
                        continue;
                    }
                    LuminanceSource ls = new BufferedImageLuminanceSource(bfi);
                    BinaryBitmap bmp = new BinaryBitmap(new HybridBinarizer(ls));

                    GenericMultipleBarcodeReader reader = new GenericMultipleBarcodeReader(new MultiFormatReader());
                    Result[] results;

                    Map<DecodeHintType, Object> decodeHints = new EnumMap<>(DecodeHintType.class);
                    decodeHints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

                    results = reader.decodeMultiple(bmp, decodeHints);
                    System.out.println("Found " + results.length + " barcodes in " + filename);
                    int i = 0;
                    for (Result result : results)
                        System.out.println("Barcode " + ++i + ": " + result.getText());
                }
                catch (NotFoundException e)
                {
                    System.err.println("No barcodes found in " + filename);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    System.out.println("---------------------------------------------------------");
                }
            }
        }*/
        String text = "";
        String enter = "\n";

        Tesseract tesseract = new Tesseract();

        try {
            tesseract.setDatapath("Tess4j\\Tess4J-3.4.8-src\\Tess4J\\tessdata");
            // the path of your tess data folder
            // inside the extracted file
            text = tesseract.doOCR(new File(Filepath));
            // path of your image file
            System.out.print(text);
            System.out.println();
            text = text.substring(0, text.indexOf(enter));
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return text;
    }
}

