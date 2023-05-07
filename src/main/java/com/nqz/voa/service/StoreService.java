package com.nqz.voa.service;

import com.nqz.voa.entry.CategoryEntry;
import com.nqz.voa.entry.MenuItemEntry;
import com.nqz.voa.entry.StoreEntry;

import java.util.List;

public interface StoreService {

  List<StoreEntry> findAllStores();

  StoreEntry findStoreById(int stId);

  int addStore(String stName, String stDescription, int ctgId);

  List<CategoryEntry> findAllCategories();

  int addCategory(String ctgName);

  List<MenuItemEntry> findAllMenuItems();

  MenuItemEntry findMenuItemById(int miId);

  List<MenuItemEntry> findMenuItemsByStoreId(int stId);

  int addMenuItem(String miName, int miUnitPrice);

  int addMenuItemToStore(int stId, int miId);

  int getFoodStallCount();

  int getIceCreamParlorCount();

  int getRestaurantCount();

  int getGiftShopCount();

  int getApparelsCount();
}
