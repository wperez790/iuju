package ar.iua.iuju.webservices;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@CrossOrigin
@ApiIgnore
public class SwaggerController {
    @RequestMapping("/api/v1/docs")
    public String docs() {
        return "redirect:/swagger-ui.html";
    }
}