package com.nx.service;

import org.springframework.data.domain.Page;
import com.nx.entity.Exam;

public interface ExamService extends IFinder<Exam>,IService<Exam>{
	public Page<Exam> fetchAllExams(Integer pageNo, Integer pageSize, String sortBy) throws Exception;
}
