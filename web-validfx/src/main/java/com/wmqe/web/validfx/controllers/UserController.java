package com.wmqe.web.validfx.controllers;

import com.wmqe.web.validfx.vo.UserVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 注册用户
     * @param user
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody(required = true) UserVo user ) {


        return new ResponseEntity<UserVo>(user, HttpStatus.OK);
    }

    /**
     * 获取用户
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity getUser(@PathVariable String userId) {
        UserVo user = new UserVo();
        user.setUserId(userId);
        user.setEmail(userId+"@qq.com");
        return new ResponseEntity<UserVo>(user, HttpStatus.OK);
    }
}
