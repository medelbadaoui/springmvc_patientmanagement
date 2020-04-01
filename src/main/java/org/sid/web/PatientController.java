package org.sid.web;

import org.sid.dao.PatientRepository;
import org.sid.entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @RequestMapping(value = "/patients", method = RequestMethod.GET)
    public String patients(Model model,
                           @RequestParam(name = "mc", defaultValue = "") String motCle,
                           @RequestParam(name = "page", defaultValue = "0") int page) {
        Page<Patient> pagePatients = patientRepository.findByNameContains(motCle, PageRequest.of(page, 6));

        model.addAttribute("pagePatient", pagePatients);
        model.addAttribute("pageCourante", page);
        model.addAttribute("mc", motCle);
        int[] pages = new int[pagePatients.getTotalPages()];
        for (int i = 0; i < pages.length; i++) pages[i] = i;
        model.addAttribute("pages", pages);
        return "patients";
    }

    @RequestMapping(value = "/form")
    public String formPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "formPatient";
    }

    @RequestMapping(value = "/savePatient", method = RequestMethod.POST)
    public String save(Model model, @Valid Patient p, BindingResult
            bindingResult) {
        if (bindingResult.hasErrors()) {
            return "formPatient";
        }
        patientRepository.save(p);
        model.addAttribute("patient", p);
        return "confirmation";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(Long id, String mc, int page) {
        patientRepository.deleteById(id);
        return "redirect:/patients?page=" + page + "&mc=" + mc;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model, Long id) {
        Patient p = patientRepository.getOne(id);
        model.addAttribute("patient", p);
        System.out.println("hello");
        return "editpatient";
    }


    @RequestMapping(value = "/confirmedit", method = RequestMethod.POST)
    public String confirmedit(Model model, @Valid Patient p, BindingResult
            bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editpatient";
        }
        patientRepository.save(p);
        model.addAttribute("patient", p);
        return "redirect:/patients";
    }
}
