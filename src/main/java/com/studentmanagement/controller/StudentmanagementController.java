package com.studentmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.studentmanagement.model.Student;
import com.studentmanagement.service.StudentDAO;
import com.studentmanagement.model.User;
import com.studentmanagement.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentmanagementController {

    @Autowired
    private UserService userService;
    @Autowired
	private StudentDAO studentDao;
	
	@RequestMapping(value="/")
	public String acceuilRegistration() {
		
		return "index";
	}
	@RequestMapping(value="/crud")
	public ModelAndView  getAll(){
		List<Student> list=studentDao.findAll();
		return new ModelAndView("Crud","list",list);
	}
	@RequestMapping(value="/template")
	public String templateRegistration() {
		
		return "index_template";
	}
	@RequestMapping(value="/enroll",method=RequestMethod.GET)
	public String newRegistration(ModelMap model) {
		Student student = new Student();
		model.addAttribute("student",student);
		return "enroll";
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String saveRegistration(@Valid Student student,BindingResult result,ModelMap model,RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			System.out.println("has errors");
			return "enroll";
		}
	
		studentDao.save(student);
		
		return "redirect:/admin/home";
	}
	
	
	/*
	 * @RequestMapping(value="/viewstudents") public ModelAndView getAll() {
	 * 
	 * List<Student> list=studentDao.findAll(); return new
	 * ModelAndView("viewstudents","list",list); }
	 */
	
	
	@RequestMapping(value="/editstudent/{id}")
	public String edit (@PathVariable int id,ModelMap model) {
		
		Student student=studentDao.findOne(id);
		model.addAttribute("student",student);
		return "editstudent";
	}
	
	@RequestMapping(value="/editsave",method=RequestMethod.POST)
	public ModelAndView editsave(@ModelAttribute("student") Student p) {
		
		Student student=studentDao.findOne(p.getId());
		
		student.setFirstName(p.getFirstName());
		student.setLastName(p.getLastName());
		student.setCountry(p.getCountry());
		student.setEmail(p.getEmail());
		student.setSection(p.getSection());
		student.setSex(p.getSex());
		
		studentDao.save(student);
		return new ModelAndView("redirect:/admin/home");
	}
	
	@RequestMapping(value="/deletestudent/{id}",method=RequestMethod.GET)
	public ModelAndView delete(@PathVariable int id) {
		Student student=studentDao.findOne(id);
		studentDao.delete(student);
		return new ModelAndView("redirect:/admin/home");
	}
	
	

	@ModelAttribute("sections")
	public List<String> intializeSections(){
		List<String> sections = new ArrayList<String>();
		sections.add("Faculté");
		sections.add("Facultés des sciences");
		sections.add("Facultés des lettres");
		return sections;
	}
	
	
	/*
	 * Method used to populate the country list in view. Note that here you can
	 * call external systems to provide real data.
	 */
	@ModelAttribute("countries")
	public List<String> initializeCountries() {

		List<String> countries = new ArrayList<String>();
		countries.add("CAMEROUN");
		countries.add("INDE");
		countries.add("USA");
		countries.add("CANADA");
		countries.add("FRANCE");
		countries.add("GERMANIE");
		countries.add("ITALIE");
		countries.add("AUTRE");
		return countries;
	}

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        List<Student> list=studentDao.findAll();
		return new ModelAndView("Crud","list",list);
       
    }


}
