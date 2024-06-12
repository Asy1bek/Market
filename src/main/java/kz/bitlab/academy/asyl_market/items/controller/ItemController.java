package kz.bitlab.academy.asyl_market.items.controller;

import kz.bitlab.academy.asyl_market.items.dto.ItemEdit;
import kz.bitlab.academy.asyl_market.items.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @Value("${loadURL}")
    private String loadURL;

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("items", itemService.findAll());

        return "index";
    }

    @PostMapping("/items/create")
    public String createItem(@RequestParam(name = "imageToken") MultipartFile file,
                             ItemEdit input) {
        return itemService.create(file, input);
    }

    @GetMapping(value = "/getImage/{imageToken}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public @ResponseBody byte[] getImage(@PathVariable(name = "imageToken", required = false) String gameImageToken) throws IOException {
        String pictureURL = loadURL + "images/defaultImage.jpg";
        if (gameImageToken != null) {
            pictureURL = loadURL + "images/" + gameImageToken + ".jpg";
        }
        InputStream inputStream;
        try {
            ClassPathResource resource = new ClassPathResource(pictureURL);
            inputStream = resource.getInputStream();
        } catch (Exception e) {
            pictureURL = loadURL + "images/defaultImage.jpg";
            ClassPathResource resource = new ClassPathResource(pictureURL);
            inputStream = resource.getInputStream();
            e.printStackTrace();
        }
        return IOUtils.toByteArray(inputStream);
    }

}