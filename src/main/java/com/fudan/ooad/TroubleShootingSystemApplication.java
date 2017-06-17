package com.fudan.ooad;

import com.fudan.ooad.repository.CheckItemRepository;
import com.fudan.ooad.repository.TemplateRepository;
import com.fudan.ooad.entity.CheckItem;
import com.fudan.ooad.entity.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@SpringBootApplication
public class TroubleShootingSystemApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(TroubleShootingSystemApplication.class);

	@Autowired
	CheckItemRepository checkItemRepository;

	@Autowired
	TemplateRepository templateRepository;

	public static void main(String[] args) {
		SpringApplication.run(TroubleShootingSystemApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... strings) throws Exception {
		CheckItem checkItemA = new CheckItem();
		checkItemA.setTitle("A");
		checkItemA.setContent("contentA");

		CheckItem checkItemB = new CheckItem();
		checkItemB.setTitle("B");
		checkItemB.setContent("contentB");

		Template templateA = new Template();
		templateA.setTitle("TemplateA");
		templateA.setCheckItems(new HashSet<CheckItem>() {{add(checkItemA); add(checkItemB);}});

		Template templateB = new Template();
		templateB.setTitle("TemplateB");
		templateB.setCheckItems(new HashSet<CheckItem>() {{add(checkItemB);}});

		templateRepository.save(templateA);

//		Template templateA = templateRepository.findOne(4);
//		templateA.setCheckItems(new HashSet<CheckItem>() {{add(a);}});
//
//		templateRepository.save(templateA);
//
		for (Template template : templateRepository.findAll()) {
			logger.info(template.getTitle());
			logger.info("" + template.getId());
		}





	}
}
