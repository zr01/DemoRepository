package com.aa.magic8ball.rest.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aa.magic8ball.utils.Base64;

/**
 * The REST Controller for the Magic 8 Ball application. This only has a single
 * request mapping /actions/askthyquestion.
 * 
 * @author Allan
 * @version 1.0
 */
@RestController
public class QueryController {
	/**
	 * Questions are only valid if they start with the following words.
	 */
	static String[] QUESTION_WORDS = { "will", "shall", "would", "should", "can", "may", "could", "do", "have" };

	/**
	 * Canned yes answers.
	 */
	static String[] YES_ANSWERS = { "You ${qstn}!", "Yes.", "Definitely.", "Yup!", "Go!" };
	/**
	 * Canned no answers.
	 */
	static String[] NO_ANSWERS = { "You ${qstn} not.", "No.", "Absolutely not!", "Nope!", "Stop it." };

	/**
	 * Initialises during the first time the service is called in
	 * {@link QueryController#askThyQuestion(String)}.
	 */
	static List<Answer> ANSWERS = null;

	/**
	 * This handles the requests to the URL /actions/askthyquestion. This
	 * decodes the Base64 question so that we don't have problems transferring
	 * the question via the URL.
	 * 
	 * @param question
	 *            - Base64 encoded question
	 * @return the answer to the question, randomly selected from
	 *         {@link QueryController#ANSWERS}
	 */
	@RequestMapping(value = "/actions/askthyquestion", method = RequestMethod.GET)
	public Answer askThyQuestion(@RequestParam(value = "question") String question) {
		if (ANSWERS == null) {
			ANSWERS = new ArrayList<Answer>();
			for (String yes : YES_ANSWERS) {
				ANSWERS.add(new Answer(yes, "yes"));
			}
			for (String no : NO_ANSWERS) {
				ANSWERS.add(new Answer(no, "no"));
			}
		}

		String decodedQuestion = new String(Base64.decode(question));
		String lowercase = decodedQuestion.toLowerCase();

		Random r = new Random();

		for (String qstn : QUESTION_WORDS) {
			if (lowercase.startsWith(qstn)) {
				Answer a = ANSWERS.get(r.nextInt(ANSWERS.size()));
				if(a.getAnswer().contains("${qstn}")){
					a = new Answer(a.getAnswer().replace("${qstn}", qstn), a.getType());
				}
				return a;
			}
		}

		return new Answer("I don't think I can answer that.", "maybe");
	}

	class Answer {
		String answer;
		String type;

		public Answer(String answer, String type) {
			this.answer = answer;
			this.type = type;
		}

		public String getAnswer() {
			return answer;
		}

		public String getType() {
			return type;
		}

	}
}
