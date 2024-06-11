package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RectangleController {
    List<Rectangle> rectangles = new ArrayList<>();
    @GetMapping("rectangle")
    public Rectangle getRect() {
        Rectangle rectangle = new Rectangle(
                20, 10,
                100, 213,
                "red");
        return rectangle;
    }
    @PostMapping("addRectangle")
    public int addRectangle(@RequestBody Rectangle rectangle) {
        rectangles.add(rectangle);
        return rectangles.size();
    }
    @GetMapping("rectangles")
    public List<Rectangle> getRectangles() {
        return rectangles;
    }
    @GetMapping("rectangle/{id}")
    public Rectangle getRectangle(@PathVariable Long id) {
        return rectangles.get(id.intValue());
    }
}
