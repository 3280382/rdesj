/** 
 * @(#)DemoDataRepositoryTest.java 1.0.0 2011-4-22 下午05:25:24  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.demo.repository;

import com.cicl.frame.core.repository.AbstractRepositoryTest;
import com.cicl.frame.demo.entity.DemoData;
import com.cicl.frame.demo.repository.DemoDataRepository;

/**
 * Class DemoDataRepositoryTest 
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 下午05:25:24
 */
public class DemoDataRepositoryTest extends AbstractRepositoryTest {
	   public void testFindByIdReturnsDemoData() {
		    
		   DemoDataRepository repository = new DemoDataRepository(
	                sessionFactory());
	    
	        Long id = Long.valueOf(1);
	        DemoData demoData = repository.load(id);
	    
	        assertEquals("Expected demoData ID 1", id, demoData.getId());        

		}
		
	    public void testCreateDemoData() {
	        DemoData demoData = new DemoData();


	        DemoDataRepository repository = new DemoDataRepository(sessionFactory());
	        repository.save(demoData);

	        flushAndClearSessionCache();
	        
	        assertNotNull("DemoData has not been assigned an id on create", demoData.getId());
	    }
	    

	/* (non-Javadoc)
	 * @see com.cicl.frame.repository.AbstractRepositoryTest#testData()
	 */
	//@Override
	protected String testData() {
		return "dbunit-DemoData.xml";
	}

}
