package com.example.demo;

import org.springframework.web.bind.annotation.*;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RectangleController {
    private List<Rectangle> rectangles = new ArrayList<>(); // zadanie 3.1
    @GetMapping("rectangle")    // zadanie 2
    public Rectangle getRect() {
        Rectangle rectangle = new Rectangle(
                20, 10,
                100, 213,
                "red");
        return rectangle;
    }
    @PostMapping("addRectangle")    // zadanie 3.2  // zadanie 4
    public int addRectangle(@RequestBody Rectangle rectangle) {
        rectangles.add(rectangle);
        return rectangles.size();
    }
    @GetMapping("rectangles")   // zadanie 3.3
    public List<Rectangle> getRectangles() {
        return rectangles;
    }
    @GetMapping("svgRectangles")    // zadanie 3.4
    public String svgRectangles() {
        String svgRectanglesCode = "<svg width=\"500\" height=\"500\" >\n";

        for (Rectangle rectangle : rectangles) {
            svgRectanglesCode +=
                    "<rect width=\"" + rectangle.getWidth() +
                            "\" height=\"" + rectangle.getHeight() +
                            "\" x=\"" + rectangle.getX() +
                            "\" y=\"" + rectangle.getY() +
                            "\" fill=\"" + rectangle.getColor() +
                            "\" />\n";
        }

        svgRectanglesCode += "</svg>";
        return svgRectanglesCode;
    }
    @GetMapping("getRectangle/{id}")   // zadanie 5.1
    public Rectangle getRectangle(@PathVariable Long id) {
        return rectangles.get(id.intValue());
    }
    @PutMapping("putRectangle/{id}")    // zadanie 5.2
    public List<Rectangle> putRectangle(@PathVariable int id, @RequestBody Rectangle rectangle) {
        rectangles.set(id, rectangle);
        return rectangles;
    }
    @DeleteMapping("deleteRectangle/{id}")
    public List<Rectangle> deleteRectangle(@PathVariable int id) {
        rectangles.remove(id);
        return rectangles;
    }
}
