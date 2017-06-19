package com.fudan.ooad;

import com.fudan.ooad.entity.*;
import com.fudan.ooad.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Calendar;

@SpringBootApplication
public class TroubleShootingSystemApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(TroubleShootingSystemApplication.class);

	@Autowired
	CheckItemRepository checkItemRepository;

	@Autowired
	TemplateRepository templateRepository;

    @Autowired
    private TaskProcessRepository taskProcessRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    CheckTaskRepository checkTaskRepository;

    public void testAddRepository() {
        Company company = new Company();
        company.setName("company1");
        company.setCompanyCode("company_code1");
        company.setOrganizationCode("organizationCode");

        CheckTask checkTask = new CheckTask();
        checkTask.setTitle("task1");
        checkTask.setPostDate(Date.from(Calendar.getInstance().toInstant()));

        checkTask.setTemplate(templateRepository.findAll().get(0));

        TaskProcess taskProcess = new TaskProcess();
        taskProcess.setCompany(company);
        taskProcess.setCheckTask(checkTaskRepository.findAll().get(0));
//        taskProcess.setId(taskProcessID);
        checkTaskRepository.save(checkTask);
        taskProcessRepository.save(taskProcess);
//        taskProcessRepository.findAll().get(0).equals(taskProcess);
    }

	public static void main(String[] args) {
		SpringApplication.run(TroubleShootingSystemApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... strings) throws Exception {

//		CheckItem checkItemA = new CheckItem();
//		checkItemA.setTitle("A");
//		checkItemA.setContent("contentA");
//
//		CheckItem checkItemB = new CheckItem();
//		checkItemB.setTitle("B");
//		checkItemB.setContent("contentB");
//
//		Template templateA = new Template();
//		templateA.setTitle("TemplateA");
//		templateA.setCheckItems(new HashSet<CheckItem>() {{add(checkItemA); add(checkItemB);}});
//
//		Template templateB = new Template();
//		templateB.setTitle("TemplateB");
//		templateB.setCheckItems(new HashSet<CheckItem>() {{add(checkItemB);}});
//
//		templateRepository.save(templateA);
//
////		Template templateA = templateRepository.findOne(4);
////		templateA.setCheckItems(new HashSet<CheckItem>() {{add(a);}});
////
////		templateRepository.save(templateA);
////
//		for (Template template : templateRepository.findAll()) {
//			logger.info(template.getTitle());
//			logger.info("" + template.getId());
//		}
//
//



	}
}
