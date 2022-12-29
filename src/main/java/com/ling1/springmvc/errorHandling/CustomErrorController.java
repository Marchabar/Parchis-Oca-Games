package com.ling1.springmvc.errorHandling;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomErrorController implements ErrorController {

    public static final String  ERROR_HANDLING = "ErrorHandling/ErrorHandling";

    @RequestMapping("/error")
    @ResponseBody
    ModelAndView error(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Integer statusCode = Integer.valueOf(status.toString());
        ModelAndView result = new ModelAndView(ERROR_HANDLING);
        result.addObject("statusCode", statusCode);
        return result;

    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
    
}
