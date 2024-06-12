package kz.bitlab.academy.asyl_market.items.service.impl;

import kz.bitlab.academy.asyl_market.items.dto.ItemEdit;
import kz.bitlab.academy.asyl_market.items.entity.ItemEntity;
import kz.bitlab.academy.asyl_market.items.mapper.ItemMapper;
import kz.bitlab.academy.asyl_market.items.repository.ItemRepository;
import kz.bitlab.academy.asyl_market.items.service.ItemService;
import kz.bitlab.academy.asyl_market.users.entity.UserEntity;
import kz.bitlab.academy.asyl_market.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;
    private final UserService userService;

    @Value("${targetURL}")
    private String targetURL;

    @Transactional
    @Override
    public String create(MultipartFile file, ItemEdit input) {
        ItemEntity entity = ItemMapper.INSTANCE.toEntity(input);
        String image = uploadImage(file, entity);
        entity.setImage(image);
        repository.save(entity);

        return "redirect:/";
    }

    @Override
    public String update(Long id, ItemEdit input) {
        return null;
    }

    @Override
    public List<ItemEntity> findAll() {
        return repository.findAll();
    }

    private String uploadImage(MultipartFile multipartFile, ItemEntity entity) {
        UserEntity currentUser = userService.getCurrentUser();
        String imageToken = DigestUtils.sha1Hex(currentUser.getUsername() + "_" + entity.getName() + "_" + Math.random());
        String image = imageToken + ".jpg";

        if (multipartFile.getContentType().equals("image/jpeg") || multipartFile.getContentType().equals("image/png")) {
            try {
                Path path = Paths.get(targetURL + "/items/" + image);
                byte[] bytes = multipartFile.getBytes();
                Files.write(path, bytes);
                entity.setImage(imageToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return imageToken;
    }

    @Override
    public void delete(Long id) {

    }
}

