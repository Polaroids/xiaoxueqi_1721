package com.buaa.springBoot.Controller;

import com.buaa.springBoot.tool.RetResponse;
import com.buaa.springBoot.tool.RetResult;
import com.buaa.springBoot.entity.User;
import com.buaa.springBoot.service.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@ResponseBody
@RequestMapping("/api/user/")
public class UserController {
    @Autowired
    private UserInterface userService;

    @RequestMapping("/register")
    public RetResult<User> register(User user) {
        if (userService.findByName(user.getUserName())!=null)
            return RetResponse.makeErrRsp("用户名已存在");

        if(userService.findByEmail(user.getEmail())!=null)
            return RetResponse.makeErrRsp("邮箱已被注册");

        if(user.getUserName().length()>15)
            return RetResponse.makeErrRsp("用户名过长");

        if (user.getPassword().length()>20||user.getPassword().length()<6)
            return RetResponse.makeErrRsp("密码长度必须为6~20");

        userService.insert(user);
        return RetResponse.makeOKRsp();
    }

    @RequestMapping("/login")
    public RetResult<User> login(String userName,String password){
        User user = userService.findByName(userName);
        if (user==null)
            return RetResponse.makeErrRsp("用户名不存在");
        if (!user.getPassword().equals(password))
            return RetResponse.makeErrRsp("密码错误");
        user.setToken(userService.updateToken(user));
        return RetResponse.makeOKRsp(user);
    }

    @RequestMapping("/getUserInfoByToken")
    public RetResult<User> getUserInfoByToken(String token){
        long threeDay = 1000*60*60*24*3;
        User user = userService.findByToken(token);
        if (user == null)
            return RetResponse.makeErrRsp("token不存在");
        if (user.getLastOperationTime() + threeDay < new Date().getTime())
            return RetResponse.makeErrRsp("token已过期");
        userService.updateLastOperation(user);//每一次操作更新最后一次时间，自动延长token有效期
        return RetResponse.makeOKRsp(user);
    }

}
