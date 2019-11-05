package edu.udacity.java.nano;

import edu.udacity.java.nano.chat.WebSocketChatServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@SpringBootApplication
@RestController
public class WebSocketChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketChatApplication.class, args);
    }

    /**
     * Login Page
     */
    @GetMapping("/")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    @GetMapping("/*")
    public void redirectToLoginPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("/");
    }

    @GetMapping("/json/validate/{username}")
    public HashMap<String, Boolean> validateUsername(@PathVariable("username") String username) {
        Boolean isValid = !WebSocketChatServer.onlineSessions.containsKey(username);
        HashMap map = new HashMap();
        map.put("valid", isValid);
        return map;
    }

    /**
     * Chatroom Page
     */

    @GetMapping("/chat/{username}")
    public ModelAndView chat(@PathVariable("username") String username) {
        return new ModelAndView("/chat");
    }

}
