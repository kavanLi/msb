package com.example.demo.service.lucene;

import java.util.List;

import com.mashibing.internal.common.domain.request.FuzzyQueryReq;


public interface LuceneService {

    List <String> fuzzyQuery4itemManagement(FuzzyQueryReq req);

}
