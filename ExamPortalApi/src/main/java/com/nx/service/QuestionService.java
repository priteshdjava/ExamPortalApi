package com.nx.service;

import org.springframework.data.domain.Page;
import com.nx.entity.Question;

public interface QuestionService extends IFinder<Question>, IService<Question> {
	public Page<Question> fetchAllQuestions(Integer pageNo, Integer pageSize, String sortBy) throws Exception;
}
