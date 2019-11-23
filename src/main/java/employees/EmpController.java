package employees;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EmpController {
    private List<Emp> list;

    public EmpController() {
        list = new ArrayList<>();
        list.add(new Emp(1, "Jakub", 35000f, "Trener"));
        list.add(new Emp(2, "Adam", 25000f, "Siatkarz"));
        list.add(new Emp(3, "Robert", 55000f, "YouTuber"));
    }

    @RequestMapping("/empform")
    public ModelAndView showform() {
        return new ModelAndView("empform", "command", new Emp());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("emp") Emp emp) {
        emp.setId(list.size() + 1);
        list.add(emp);
        System.out.println(emp.getName() + " " + emp.getSalary() + " " + emp.getDesignation());
        return new ModelAndView("redirect:/viewemp");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam String id) {
        Emp emp = list.stream().filter(f -> f.getId() == Integer.parseInt(id)).findFirst().get();
        list.remove(emp);
        return new ModelAndView("redirect:/viewemp");
    }

/*    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@ModelAttribute("emp") Emp emp) {
    //Error Concurrent Modification Exception
        for (Emp one : list) {
            if (one.getId() == Integer.parseInt(id))
                list.remove(one);
        }

        return new ModelAndView("redirect:/viewemp");
    }*/

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public ModelAndView test() {
        System.out.println("Test");
        return new ModelAndView("redirect:/viewemp");
    }

    @RequestMapping("/viewemp")
    public ModelAndView viewemp() {
        return new ModelAndView("viewemp", "list", list);
    }
}