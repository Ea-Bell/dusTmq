package com.example.dusTmq.controller.errorPage;

import com.example.dusTmq.common.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;

@Controller
@Slf4j
public class errorController {

    @GetMapping("/error-400")
    public void error400 (HttpServletResponse response) throws IOException{
        response.sendError(400, "400에러!");
    }
    @GetMapping("/error-403")
    public void error403 (HttpServletResponse response) throws IOException{
        response.sendError(403, "403에러!");
    }
    @GetMapping("/error-404")
    public void error404 (HttpServletResponse response) throws IOException{
            response.sendError(404, "404에러!");
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException{
        response.sendError(500, "500에러!");
    }

}
