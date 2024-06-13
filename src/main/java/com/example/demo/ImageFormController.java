package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("imageform")
public class ImageFormController {
    String image;
    int brightFactor = 0;
    @GetMapping("index")
    public String index(Model model) {
        model.addAttribute("brightness", brightFactor);
        return "index";
    }
    @GetMapping("image")
    public String imageUpload(Model model) {
        model.addAttribute("image", image);
        return "image";
    }
    @PostMapping("upload")
    public String imageUpload(@RequestParam("image") MultipartFile file) {
        try {
            byte[] fileBytes = file.getBytes();
            if (brightFactor != 0) {
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(fileBytes));
                lightUpPicture(image, brightFactor);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                ImageIO.write(image, "png", stream);
                fileBytes = stream.toByteArray();
            }
            image = Base64.getEncoder().encodeToString(fileBytes);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/imageform/image";
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
}
