package com.example.onlineExam;

import org.junit.jupiter.api.Test;

import com.example.customer.model.Exam;
import com.example.onlineExam.utils.MyGson;

class OnlineExamApplicationTests {

	@Test
	void contextLoads() {
		String json = "{\"examinerKey\":1,\"examType\":\"SDFS\",\"totalTimeInMin\":16850000,\"doNegativeMarking\":false,"
				+ "\"negativeMark\":0,\"useEachQusTime\":false,\"examQuestionList\":[{\"id\":\"Desktop.model.ExamQuestion-1\","
				+ "\"mark\":10,\"question\":\"HI\",\"questionKey\":1,\"answerOptionKey\":3,\"answer\":\"4\","
				+ "\"timeInSecond\":10000000},{\"id\":\"Desktop.model.ExamQuestion-2\",\"mark\":10,\"question\":\"HI\","
				+ "\"questionKey\":2,\"answerOptionKey\":7,\"answer\":\"5\",\"timeInSecond\":1000000},{\"id\":"
				+ "\"Desktop.model.ExamQuestion-3\",\"mark\":10,\"question\":\"HI\",\"questionKey\":3,\"answerOptionKey\":9,"
				+ "\"answer\":\"6\",\"timeInSecond\":1000000000}]}";
		System.out.println(MyGson.gson.fromJson(json, Exam.class));
	}

}
