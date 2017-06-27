package com.neusoft.spring.task;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * �����л��ļ�Ⱥjob
 * @author King
 *
 */
public class ClusterJob implements Serializable {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	public static int i = 0;   

    public void execute() throws InterruptedException {
    	logger.debug("begin begin begin begin begin begin begin begin ! job = "+ ++i);
        System.out.print("job = "+i+" is running ");
        for(int j = 1 ; j <= 5;j++){//����5��
            System.out.print( j +" ");
            Thread.sleep(1*1000);
        }
        System.out.println();
        logger.debug("end end end end end end end end end end end end ! job = "+ i);
        //System.out.println();
    }

}
