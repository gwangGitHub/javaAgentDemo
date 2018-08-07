package com.gwang.hotSwap.instrument.agentmain;

import com.sun.tools.attach.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by wanggang on 2018/8/5.
 */
public class JVMTIThread {

    public static final String AGENT_JAR_PATH = "/Users/wanggang/Desktop/eclipseworkspace/javaAgentDemo/target/javaAgentDemo-1.0-SNAPSHOT.jar";
    public static final String AGENT_PARAM = "test";

    public static void main(String[] args)
            throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            if (vmd.displayName().endsWith(AgentMainDemo.TRANS_CLASS_END_NAME)) {
                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                virtualMachine.loadAgent(AGENT_JAR_PATH, AGENT_PARAM);
                System.out.println("ok");
                virtualMachine.detach();
            }
        }
    }
}
