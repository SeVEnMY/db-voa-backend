package com.nqz.voa.service.impl;

import com.nqz.voa.entry.CategoryEntry;
import com.nqz.voa.entry.MenuItemEntry;
import com.nqz.voa.entry.StoreEntry;
import com.nqz.voa.mapper.StoreMapper;
import com.nqz.voa.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

  @Autowired
  protected StoreMapper storeMapper;

  @Override
  public List<StoreEntry> findAllStores() {
    return storeMapper.findAllStores();
  }

  @Override
  public StoreEntry findStoreById(int stId) {
    return storeMapper.findStoreById(stId);
  }

  @Override
  public int addStore(String stName, String stDescription, int ctgId) {
    return storeMapper.addStore(stName, stDescription, ctgId);
  }

  @Override
  public List<CategoryEntry> findAllCategories() {
    return storeMapper.findAllCategories();
  }

  @Override
  public int addCategory(String ctgName) {
    return storeMapper.addCategory(ctgName);
  }

  @Override
  public List<MenuItemEntry> findAllMenuItems() {
    return storeMapper.findAllMenuItems();
  }

  @Override
  public MenuItemEntry findMenuItemById(int miId) {
    return storeMapper.findMenuItemById(miId);
  }

  @Override
  public List<MenuItemEntry> findMenuItemsByStoreId(int stId) {
    return storeMapper.findMenuItemsByStoreId(stId);
  }

  @Override
  public int addMenuItem(String miName, int miUnitPrice) {
    return storeMapper.addMenuItem(miName, miUnitPrice);
  }

  @Override
  public int addMenuItemToStore(int stId, int miId) {
    return storeMapper.addMenuItemToStore(stId, miId);
  }
}
