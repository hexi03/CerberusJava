package com.hexi.Cerberus.adapter.web.webstatic.UserGroupController;

import com.hexi.Cerberus.domain.group.GroupID;
import com.hexi.Cerberus.domain.user.UserID;
import com.hexi.Cerberus.adapter.web.webstatic.UserGroupController.ViewModels.GroupCreateModel;
import com.hexi.Cerberus.adapter.web.webstatic.UserGroupController.ViewModels.GroupEditModel;
import com.hexi.Cerberus.adapter.web.webstatic.UserGroupController.ViewModels.UserCreateModel;
import com.hexi.Cerberus.adapter.web.webstatic.UserGroupController.ViewModels.UserEditModel;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

//@RequestMapping("/UserGroup")
//@Controller
public class UserGroupController {

//    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserGroupController.class);
//    UserGroupModelService userGroupModelService;
//
//
//    public UserGroupController(
//            UserGroupModelService userGroupModelService) {
//        this.userGroupModelService = userGroupModelService;
//    }
//
//    @GetMapping("/")
//    public String index(Model model) {
//
//        model.addAttribute("model", userGroupModelService.getIndexModel());
//        return "UserGroupController/index.html";
//    }
//
//    @GetMapping("/userDetails/{id}")
//    public String userDetails(@PathVariable UserID id, Model model) {
//        model.addAttribute("model", userGroupModelService.getUserDetailsModel(id));
//        return "UserGroupController/userDetails.html";
//    }
//
//    // CRUD операции для сущности User
//
//    @GetMapping("/userCreate")
//    public String createUser(Model model) {
//        model.addAttribute("model", userGroupModelService.getUserCreateModel());
//        return "UserGroupController/userCreate.html";
//    }
//
//    @PostMapping("/userCreate")
//    public String createUser(@Valid @ModelAttribute("model") UserCreateModel user, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            // Обработка ошибок валидации
//            return "UserGroupController/userCreate.html";
//        }
//
////        try {
//            userGroupModelService.createUser(user);
//            logger.debug("User created: {}", user.getName());
////        } catch (Exception e) {
////            // Обработка других ошибок (например, база данных недоступна)
////            model.addAttribute("error", "Failed to create user.");
////
////            return "error";
////        }
//
//        return "redirect:/UserGroup/";
//    }
//
//    @GetMapping("/userEdit/{id}")
//    public String updateUser(@PathVariable UserID id, Model model) {
//        model.addAttribute("model", userGroupModelService.getUserEditModel(id));
//        return "UserGroupController/userEdit.html";
//    }
//
//    @PostMapping("/userEdit/{id}")
//    public String updateUser(@PathVariable UserID id, @Valid @ModelAttribute("model") UserEditModel user, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            return "UserGroupController/userEdit.html";
//        }
//
//        try {
//            user.setId(id);
//            userGroupModelService.updateUser(user);
//            logger.debug("User updated: {}", user.getName());
//        } catch (Exception e) {
//            // Обработка других ошибок
//            model.addAttribute("error", "Failed to update user.");
//            return "error";
//        }
//
//        return "redirect:/UserGroup/";
//    }
//
//    @GetMapping("/userDelete/{id}")
//    public String deleteUser(@PathVariable UserID id, Model model) {
//        model.addAttribute("model", userGroupModelService.getUserDetailsModel(id));
//        System.err.println(userGroupModelService.getUserDetailsModel(id));
//        return "UserGroupController/userDelete.html";
//    }
//
//    @PostMapping("/userDelete/{id}")
//    public String deleteUserPost(@PathVariable UserID id, Model model) {
//        try {
//            userGroupModelService.deleteUser(id);
//            logger.debug("User deleted with ID: {}", id);
//        } catch (Exception e) {
//            // Обработка других ошибок
//            model.addAttribute("error", "Failed to delete user.");
//            return "error";
//        }
//
//        return "redirect:/UserGroup/";
//    }
//
//    // CRUD операции для сущности Group
//
//    @GetMapping("/groupDetails/{id}")
//    public String groupDetails(@PathVariable GroupID id, Model model) {
//        model.addAttribute("model", userGroupModelService.getGroupEditModel(id));
//
//        return "UserGroupController/groupDetails.html";
//    }
//
//    @GetMapping("/groupCreate")
//    public String createGroup(Model model) {
//        model.addAttribute("model", new GroupCreateModel());
//        return "UserGroupController/groupCreate.html";
//    }
//
//    @PostMapping("/groupCreate")
//    public String createGroup(@Valid @ModelAttribute("model") GroupCreateModel group, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            // Обработка ошибок валидации
//            return "UserGroupController/groupCreate.html";
//        }
//
//        try {
//            userGroupModelService.createGroup(group);
//        } catch (Exception e) {
//            // Обработка других ошибок
//            model.addAttribute("error", "Failed to create group.");
//            return "error";
//        }
//
//        return "redirect:/UserGroup/";
//    }
//
//    @GetMapping("/groupEdit/{id}")
//    public String updateGroup(@PathVariable GroupID id, Model model) {
//        model.addAttribute("model", userGroupModelService.getGroupDetailsModel(id));
//        return "UserGroupController/groupEdit.html";
//    }
//
//    @PostMapping("/groupEdit/{id}")
//    public String updateGroup(@PathVariable GroupID id, @Valid @ModelAttribute("group") GroupEditModel group, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            // Обработка ошибок валидации
//            return "UserGroupController/groupEdit.html";
//        }
//
//        try {
//            group.setId(id);
//            userGroupModelService.updateGroup(group);
//            logger.debug("Group updated with ID: {}", id);
//        } catch (Exception e) {
//            // Обработка других ошибок
//            model.addAttribute("error", "Failed to update group.");
//            return "error";
//        }
//
//        return "redirect:/UserGroup/";
//    }
//
//    @GetMapping("/groupDelete/{id}")
//    public String deleteGroup(@PathVariable GroupID id, Model model) {
//        model.addAttribute("model", userGroupModelService.getGroupDetailsModel(id));
//        System.err.println(userGroupModelService.getGroupDetailsModel(id));
//        return "UserGroupController/groupDelete.html";
//    }
//    @PostMapping("/groupDelete/{id}")
//    public String deleteGroupPost(@PathVariable GroupID id, Model model) {
//        try {
//            userGroupModelService.deleteGroup(id);
//            logger.debug("Group deleted with ID: {}", id);
//        } catch (Exception e) {
//            // Обработка других ошибок
//            model.addAttribute("error", "Failed to delete group.");
//            return "error";
//        }
//
//        return "redirect:/UserGroup/";
//    }
//
//
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public String handleException(Exception e, Model model) {
//        model.addAttribute("error", "Internal Server Error");
//        return "error";
//    }
}