package com.nqz.voa.controller;

import com.nqz.voa.entry.*;
import com.nqz.voa.helper.Result;
import com.nqz.voa.service.AccountService;
import com.nqz.voa.service.StoreService;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/store")
@Api(tags = "Store")
public class StoreController {
  public static final String SESSION_NAME = "userInfo";

  @Autowired
  private StoreService storeService;

  @Autowired
  private AccountController accountController;

  @Autowired
  private AccountService accountService;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public Result<List<StoreEntry>> findAllStores(HttpServletRequest request, HttpServletResponse response) {
    Result<List<StoreEntry>> result = new Result<>();

    /*check admin*/
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    result.setResultSuccess("Success!", storeService.findAllStores());
    return result;
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public int addStore(@RequestParam String stName,
                      @RequestParam String stDescription,
                      @RequestParam int ctgId) {

    return storeService.addStore(stName, stDescription, ctgId);
  }

  @RequestMapping(value = "/get", method = RequestMethod.GET)
  public Result<StoreEntry> findStoreById(@RequestParam int stId, HttpServletRequest request, HttpServletResponse response) {
    Result<StoreEntry> result = new Result<>();
    // is login?
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    StoreEntry storeEntry = storeService.findStoreById(stId);
    if (storeEntry == null) {
      result.setResultSuccess("Not found!", null);
      return result;
    }
    result.setResultSuccess("Success!", storeEntry);
    return result;
  }

  @RequestMapping(value = "/listctg", method = RequestMethod.GET)
  public Result<List<CategoryEntry>> findAllCategories(HttpServletRequest request, HttpServletResponse response) {
    Result<List<CategoryEntry>> result = new Result<>();

    /*check admin*/
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    result.setResultSuccess("Success!", storeService.findAllCategories());
    return result;
  }

  @RequestMapping(value = "/addctg", method = RequestMethod.POST)
  public int addCategory(@RequestParam String ctgName, HttpServletRequest request, HttpServletResponse response) {
    if (!accountController.isLogin(request, response).isSuccess()) {
      response.setStatus(403);
      return -1;
    }

    // get user account name
    AccountEntry sessionUser = (AccountEntry) (request.getSession()).getAttribute(SESSION_NAME);
    String accEmail = sessionUser.getAcc_email();
    if (!accountService.isAdmin(accEmail)) {
      response.setStatus(403);
      return -1;
    }

    return storeService.addCategory(ctgName);
  }

  @RequestMapping(value = "/listmi", method = RequestMethod.GET)
  public Result<List<MenuItemEntry>> findAllMenuItems(HttpServletRequest request, HttpServletResponse response) {
    Result<List<MenuItemEntry>> result = new Result<>();

    /*check admin*/
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    result.setResultSuccess("Success!", storeService.findAllMenuItems());
    return result;
  }

  @RequestMapping(value = "/getmi", method = RequestMethod.GET)
  public Result<List<MenuItemEntry>> findMenuItemsByStoreId(@RequestParam int stId, HttpServletRequest request, HttpServletResponse response) {
    Result<List<MenuItemEntry>> result = new Result<>();
    // is login?
    if (!accountController.isLogin(request, response).isSuccess()) {
      result.setResultFailed("Not logged in！");
      return result;
    }

    List<MenuItemEntry> menuItemEntryList = storeService.findMenuItemsByStoreId(stId);
    if (menuItemEntryList == null) {
      result.setResultSuccess("Empty!", null);
      return result;
    }
    result.setResultSuccess("Success!", menuItemEntryList);
    return result;
  }

  @RequestMapping(value = "/addmi", method = RequestMethod.POST)
  public int addMenuItem(@RequestParam String miName, @RequestParam int miUnitPrice, HttpServletRequest request, HttpServletResponse response) {
    if (!accountController.isLogin(request, response).isSuccess()) {
      response.setStatus(403);
      return -1;
    }

    // get user account name
    AccountEntry sessionUser = (AccountEntry) (request.getSession()).getAttribute(SESSION_NAME);
    String accEmail = sessionUser.getAcc_email();
    if (!accountService.isAdmin(accEmail)) {
      response.setStatus(403);
      return -1;
    }

    return storeService.addMenuItem(miName, miUnitPrice);
  }

  @RequestMapping(value = "/addmist", method = RequestMethod.POST)
  public int addMenuItem(@RequestParam int stId, @RequestParam int miId, HttpServletRequest request, HttpServletResponse response) {
    if (!accountController.isLogin(request, response).isSuccess()) {
      response.setStatus(403);
      return -1;
    }

    // get user account name
    AccountEntry sessionUser = (AccountEntry) (request.getSession()).getAttribute(SESSION_NAME);
    String accEmail = sessionUser.getAcc_email();
    if (!accountService.isAdmin(accEmail)) {
      response.setStatus(403);
      return -1;
    }

    StoreEntry storeEntry = storeService.findStoreById(stId);
    MenuItemEntry menuItemEntry = storeService.findMenuItemById(miId);

    if (storeEntry == null || menuItemEntry == null) {
      response.setStatus(404);
      return -1;
    }

    return storeService.addMenuItemToStore(stId, miId);
  }

}
