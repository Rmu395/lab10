package com.example.demo;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.http.HttpHeaders;
import java.util.Base64;

@RestController
public class ImageController {
    @GetMapping("brightenImage/{brighenMagnitude}") // zadanie 6
    public String brightenImage(@PathVariable int brighenMagnitude, @RequestBody String imageInB64) {
        try {
            byte[] photo = Base64.getDecoder().decode(imageInB64);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(photo));

            lightUpPicture(image, brighenMagnitude);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", stream);
            byte[] photo2 = stream.toByteArray();
            String result = Base64.getEncoder().encodeToString(photo2);

            return result;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void lightUpPicture(BufferedImage image, int factor) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixelRGB = image.getRGB(x, y);
                pixelRGB = brightenPixel(pixelRGB, factor);
                image.setRGB(x, y, pixelRGB);
            }
        }
    }
    private int brightenPixel(int pixel, int factor) {
        int mask = 255;
        int blue = pixel & mask;
        int green = (pixel >> 8) & mask;
        int red = (pixel >> 16) & mask;
        blue = brightenPixelPart(blue, factor);
        green = brightenPixelPart(green, factor);
        red = brightenPixelPart(red, factor);
        return blue + (green << 8) + (red << 16);
    }
    private int brightenPixelPart(int color, int factor) {
        color += factor;
        if(color > 255) {
            return 255;
        } else {
            return color;
        }
    }
    @GetMapping("displayImage") // zadanie 7 (wersja działająca)
    public void displayImage(@RequestBody String imageInB64, HttpServletResponse response) {
        try {
            byte[] photo = Base64.getDecoder().decode(imageInB64);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(photo));

            response.setContentType("image/png");

            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "png", out);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//    @GetMapping("displayImage") // zadanie 7 (nie sądze że o to chodziło)
//    public byte[] displayImage(@RequestBody String imageInB64) {
//        byte[] photo = Base64.getDecoder().decode(imageInB64);
////            BufferedImage image = ImageIO.read(new ByteArrayInputStream(photo));
//        return photo;
//    }
}
