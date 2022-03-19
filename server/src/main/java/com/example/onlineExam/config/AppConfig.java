package com.example.onlineExam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.customer.service.ExamAnswerService;
import com.example.customer.service.ExamQuestionService;
import com.example.customer.service.ExamResultService;
import com.example.customer.service.ExamService;
import com.example.customer.service.ExamerService;
import com.example.customer.service.ExaminerService;
import com.example.customer.service.OptionService;
import com.example.customer.service.QuestionService;
import com.example.customer.service.RunningExamService;
import com.example.onlineExam.service.ServiceCoordinator;
import com.example.onlineExam.service.ServiceMap;

@Configuration
public class AppConfig {

	@Bean
	ServiceMap serviceMap() {
		ServiceMap ob = new ServiceMap();
		ob.addService(questionService());
		ob.addService(examAnswerService());
		ob.addService(examerService());
		ob.addService(examinerService());
		ob.addService(examQuestionService());
		ob.addService(examResultService());
		ob.addService(examService());
		ob.addService(optionService());
		ob.addService(runningExamService());
		return ob;
	}

	@Bean
	ServiceCoordinator serviceCoordinator() {
		ServiceCoordinator sc = new ServiceCoordinator();
		sc.setServiceMap(serviceMap());
		return sc;
	}

	@Bean
	QuestionService questionService() {
		return new QuestionService();
	}

	@Bean
	ExamAnswerService examAnswerService() {
		return new ExamAnswerService();
	}

	@Bean
	ExamerService examerService() {
		return new ExamerService();
	}

	@Bean
	ExaminerService examinerService() {
		return new ExaminerService();
	}

	@Bean
	ExamQuestionService examQuestionService() {
		return new ExamQuestionService();
	}

	@Bean
	ExamResultService examResultService() {
		return new ExamResultService();
	}

	@Bean
	ExamService examService() {
		return new ExamService();
	}

	@Bean
	OptionService optionService() {
		return new OptionService();
	}

	@Bean
	RunningExamService runningExamService() {
		return new RunningExamService();
	}
}
