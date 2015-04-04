package org.wso2.carbon.mediator.throttle;

import org.apache.synapse.config.xml.*;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import javax.xml.namespace.QName;
import java.util.Map;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {

        System.out.println(this.getClass().getName() + "*********************************************");
        {
            Map<QName, Class> mediatorFactoryMap = MediatorFactoryFinder.getInstance().getFactoryMap();
            mediatorFactoryMap.put(ThrottleMediatorFactory.TAG_NAME, ThrottleMediatorFactory.class);
        }
        {
            Map<String, MediatorSerializer> mediatorSerializerMap = MediatorSerializerFinder.getInstance().getSerializerMap();
            mediatorSerializerMap.put(ThrottleMediator.class.getName(), ThrottleMediatorSerializer.class.newInstance());
        }
    }

    public void stop(BundleContext context) throws Exception {
        // Maybe undo what was done in the start(BundleContext) method..?
        System.out.println(this.getClass().getName() + ".stop(BundleContext) called");
    }
}