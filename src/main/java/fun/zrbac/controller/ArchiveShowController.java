package fun.zrbac.controller;

import fun.zrbac.query.BlogQuery;
import fun.zrbac.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Description: 时间轴页面显示控制器
 * @Date: Created in 17:40 2020/6/27
 * @Author: ZrBac
 * @URL: https://zrbac.fun/
 */

@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archive(Model model) {
        List<BlogQuery> blogs = blogService.getAllBlog();
        model.addAttribute("blogs", blogs);
        return "archives";
    }
}
