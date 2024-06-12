package kz.bitlab.academy.asyl_market.items.repository;

import kz.bitlab.academy.asyl_market.items.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
}

