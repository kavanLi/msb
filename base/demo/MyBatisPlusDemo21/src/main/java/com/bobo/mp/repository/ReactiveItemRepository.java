package com.bobo.mp.repository;

import com.mashibing.internalcommon.domain.pojo.DynaAmsOrgprovisions;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: kavanLi-R7000
 * @create: 2024-01-09 10:16
 * To change this template use File | Settings | File and Code Templates.
 */

@Repository
public interface ReactiveItemRepository extends ReactiveCrudRepository <DynaAmsOrgprovisions, String>{
}
