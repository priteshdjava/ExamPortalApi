package com.nx.serviceimpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nx.entity.Question;
import com.nx.repository.QuestionRepository;
import com.nx.service.BasicService;
import com.nx.service.QuestionService;

@Service
public class QuestionServiceImpl extends BasicService<Question, QuestionRepository> implements QuestionService{

	@Override
	public Page<Question> fetchAllQuestions(Integer pageNo, Integer pageSize, String sortBy) throws Exception {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));	
		return repository.findAll(paging);
	}

}
