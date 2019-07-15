package cn.myweb01.money01.controller;

import cn.myweb01.money01.pojo.User;
import cn.myweb01.money01.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("rest/user")
public class NewUserController {

    @Autowired
    private IUserService userService;



   /* @GetMapping("{uid}")
    @ResponseBody
    public User queryUserByUid(@PathVariable("uid")Integer uid){
        User queryUser = userService.queryUserByUid(uid);
        return queryUser;
    }*/

    /**
     * ResponseEntity<User> : 就相当于使用@ResponseBody的功能
     *
     * @param uid
     * @return
     */
    @GetMapping("{uid}")
    public ResponseEntity<User> queryUserByUid(@PathVariable("uid")Integer uid){


        try {
            if(uid==null||uid<0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            User queryUser = userService.queryUserByUid(uid);
            //int x = 1 /0;
            if(queryUser==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            //return ResponseEntity.status(HttpStatus.OK).body(queryUser);
            return ResponseEntity.ok(queryUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * ResponseEntity<Void> ：如果没有数据需要响应，那么使用ResponseEntity<Void>即可。
     *
     * @param user
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> insertUser(User user){

        try {
            if(user==null||StringUtils.isBlank(user.getUsername())){

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

            }
            Boolean flag = userService.insertUser(user);
            if (flag){
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @PutMapping
    public ResponseEntity<Void> updateUser(User user){

        try {
            if(user==null||user.getUid()==null||user.getUid()<0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            Boolean flag = userService.updateUser(user);
            if (flag){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteUserByUid(@RequestParam("uid")Integer uid){

        try {
            if (uid==null||uid<0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            Boolean flag = userService.deleteUserByUid(uid);
            if (flag){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
