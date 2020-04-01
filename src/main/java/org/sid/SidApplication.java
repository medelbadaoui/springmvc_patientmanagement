package org.sid;

import org.sid.dao.PatientRepository;
import org.sid.entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@SpringBootApplication
public class SidApplication implements CommandLineRunner {

    @Autowired
    private PatientRepository pr;
    public static void main(String[] args) {

        SpringApplication.run(SidApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        pr.save(new Patient(null,"Mohammed",dateFormat.parse("08/12/1998"),false));
        pr.save(new Patient(null,"Ayman",dateFormat.parse("08/09/1998"),false));
        pr.save(new Patient(null,"Ahmed",dateFormat.parse("01/01/1998"),true));

    }
}
