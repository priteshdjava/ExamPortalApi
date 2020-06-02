package com.nx.serviceimpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.nx.entity.Exam;
import com.nx.repository.ExamRepository;
import com.nx.service.BasicService;
import com.nx.service.ExamService;

@Service
public class ExamServiceImpl extends BasicService<Exam, ExamRepository> implements ExamService{

	@Override
	public Page<Exam> fetchAllExams(Integer pageNo, Integer pageSize, String sortBy) throws Exception {
		
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		return repository.findAll(paging);
	}

}
