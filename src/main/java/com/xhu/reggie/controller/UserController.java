package com.xhu.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xhu.reggie.common.R;
import com.xhu.reggie.entity.User;
import com.xhu.reggie.service.UserService;
import com.xhu.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * send verifician code to the phone number
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            String code = ValidateCodeUtils.generateValidateCode(6).toString();
            log.info("code={}", code);
            //SMSUtils.sendMessage(code, phone);

            // store verification code in session
            //session.setAttribute(phone, code);

            // store verification in redis cache and set timeout
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);

            return R.success("Verification code sent successfully");
        }
        return R.error("Verification code sent failed");
    }

    /**
     * front user login
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        log.info(map.toString());
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();

        // get verification code from session
        //Object codeInSession = session.getAttribute(phone);

        // get verification code from redis cache
        Object codeInSession = redisTemplate.opsForValue().get(phone);

        if (codeInSession != null && codeInSession.equals(code)) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.eq(User::getPhone, phone);
            User user = userService.getOne(queryWrapper);
            // new user, register
            if (user == null) {
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());

            // delete verification code from cache if login succeed
            redisTemplate.delete(phone);

            return R.success(user);
        }
        return R.error("Login failed");
    }
}
