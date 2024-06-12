package kz.bitlab.academy.asyl_market.items.mapper;

import kz.bitlab.academy.asyl_market.items.dto.ItemEdit;
import kz.bitlab.academy.asyl_market.items.entity.ItemEntity;
import kz.bitlab.academy.asyl_market.users.dto.UserCreate;
import kz.bitlab.academy.asyl_market.users.dto.UserUpdate;
import kz.bitlab.academy.asyl_market.users.entity.UserEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true))
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    ItemEntity toEntity(ItemEdit input);

    ItemEntity toEntity(@MappingTarget ItemEntity entity, ItemEdit input);

}
