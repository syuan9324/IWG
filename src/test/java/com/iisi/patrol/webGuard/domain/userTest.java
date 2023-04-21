package com.iisi.patrol.webGuard.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iisi.patrol.webGuard.security.domain.ERole;
import com.iisi.patrol.webGuard.security.domain.Role;
import com.iisi.patrol.webGuard.security.domain.User;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class userTest {
    public static void main(String[] args) {
        System.out.println("userTest");
        ObjectMapper objectMapper = new ObjectMapper();


        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:data" + File.separator + "users.json");
            InputStream in = new FileInputStream(file);
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }

            List<Map<String, Object>> result = objectMapper.readValue(responseStrBuilder.toString(), ArrayList.class);
            List<User> users = result.stream().map(m -> {
                User user = new User();
                user.setUsername(m.get("username").toString());
                user.setPassword(m.get("password").toString());
                user.setId( Long.parseLong(m.get("id").toString()));
                HashSet<Role> set = new HashSet<>();
                List<String> roles = new ArrayList<>();
                String handledString = (m.get("roles").toString().replaceAll("[\\[\\]]", ""));
                if(handledString.length()>0){
                    roles = Arrays.asList(handledString.split("\\s*,\\s*"));
                }
                roles.stream().forEach(roleString->{

                    ERole roleEnum = ERole.valueOf(roleString);
                    Role role = new Role(roleEnum);
                    set.add(role);
                });
                user.setRoles(set);
                return user;
            }).collect(Collectors.toCollection(ArrayList::new));

            users.stream().forEach(u->{
                System.out.println(u);
                u.getRoles().forEach(System.out::println);
            });
            System.out.println("============");
//            Map<String, Object> aa = result.get(0);
//            for (Map.Entry map : aa.entrySet()) {
//                System.out.printf("key:%s,value:%s", map.getKey(), map.getValue());
//                System.out.println();
//            }
//            System.out.println("============");
//            System.out.println(aa.get("roles").toString().replaceAll("[\\[\\]]", "").length());
//            List<String> roles = new ArrayList<>();
//            String handledString = (aa.get("roles").toString().replaceAll("[\\[\\]]", ""));
//            if(handledString.length()>0){
//                roles = Arrays.asList(handledString.split("\\s*,\\s*"));
//            }
//            roles.stream().forEach(r-> System.out.println(r+"===="));


        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
