package com.nqz.voa.mapper;

import com.nqz.voa.entry.CategoryEntry;
import com.nqz.voa.entry.MenuItemEntry;
import com.nqz.voa.entry.StoreEntry;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StoreMapper {

  @Select("SELECT * FROM nqz_store")
  List<StoreEntry> findAllStores();

  @Select("SELECT * FROM nqz_store WHERE st_id = #{stId}")
  StoreEntry findStoreById(int stId);

  @Insert("INSERT INTO nqz_store (st_name, st_description, ctg_id) " +
          "VALUES (#{stName}, {stDescription}, #{ctgId})")
  int addStore(String stName, String stDescription, int ctgId);

  @Select("SELECT * FROM nqz_category")
  List<CategoryEntry> findAllCategories();

  @Insert("INSERT INTO nqz_category (ctg_name) VALUES (#{ctgName})")
  int addCategory(String ctgName);

  @Select("SELECT * FROM nqz_menu_item")
  List<MenuItemEntry> findAllMenuItems();

  @Select("SELECT * FROM nqz_menu_item WHERE mi_id = #{miId}")
  MenuItemEntry findMenuItemById(int miId);

  @Select("SELECT * FROM nqz_menu_item NATURAL JOIN nqz_st_mi WHERE st_id = #{stId}")
  List<MenuItemEntry> findMenuItemsByStoreId(int stId);

  @Insert("INSERT INTO nqz_menu_item (mi_name, mi_unit_price) " +
          "VALUES (#{miName}, #{miUnitPrice});")
  int addMenuItem(String miName, int miUnitPrice);

  @Insert("INSERT INTO nqz_st_mi  (st_id, mi_id) " +
          "VALUES (#{stId}, #{miId);")
  int addMenuItemToStore(int stId, int miId);

}
