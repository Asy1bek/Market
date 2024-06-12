package kz.bitlab.academy.asyl_market.items.service;

import kz.bitlab.academy.asyl_market.items.dto.ItemEdit;
import kz.bitlab.academy.asyl_market.items.entity.ItemEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {

    String create(MultipartFile file, ItemEdit input);

    String update(Long id, ItemEdit input);

    List<ItemEntity> findAll();

    void delete(Long id);

}
