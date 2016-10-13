/** 
 * @(#)HistorylogServiceImplTest.java 1.0.0 2011-1-17 10:26:05 
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.demo.service;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import com.cicl.frame.demo.entity.DemoData;
import com.cicl.frame.demo.repository.DemoDataRepository;
import com.cicl.frame.demo.service.DemoDataService;

import junit.framework.TestCase;

/**
 * Class DemoDataServiceImplTest 
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 下午04:46:32  
 */
public class DemoDataServiceTest extends TestCase {

	public void testFindById() {

		Long demoDataId = Long.valueOf(1);
		DemoData demoData = new DemoData();
		demoData.setId(demoDataId);

		DemoDataRepository demoDataRepository = createMock(DemoDataRepository.class);

		expect(demoDataRepository.load(demoDataId)).andReturn(demoData);

		replay(demoDataRepository);

		DemoDataService service = new DemoDataService(demoDataRepository);
		DemoData _demoData = service.load(demoDataId);

		assertEquals("Incorrect Id return for demoData", demoDataId, _demoData.getId());

		verify(demoDataRepository);
	}

	public void testSave() {

		Long demoDataId = Long.valueOf(1);
		DemoData demoData = new DemoData();

		DemoDataRepository demoDataRepository = createMock(DemoDataRepository.class);

		expect(demoDataRepository.save(demoData)).andReturn(demoDataId);
		expect(demoDataRepository.load(demoDataId)).andReturn(demoData);

		replay(demoDataRepository);

		DemoDataService service = new DemoDataService(demoDataRepository);
		Long demoDataId2 = service.save(demoData);
//		DemoData demoData2 = service.findById(demoDataId2);

		assertEquals("Saving DemoData Failure", demoDataId, demoDataId2);

//		verify(demoDataRepository);
	}

	public void testUpdate() {

		Long demoDataId = Long.valueOf(1);
		DemoData demoData = new DemoData();
		demoData.setId(demoDataId);

		DemoDataRepository demoDataRepository = createMock(DemoDataRepository.class);

		expect(demoDataRepository.load(demoDataId)).andReturn(demoData);
		demoDataRepository.update(demoData);
		expectLastCall();

		replay(demoDataRepository);

		DemoDataService service = new DemoDataService(demoDataRepository);
		service.update(demoData);
		DemoData _demoData = service.load(demoDataId);

		assertEquals("Incorrect DemoData Id", demoDataId, _demoData.getId());

		verify(demoDataRepository);
	}
}
