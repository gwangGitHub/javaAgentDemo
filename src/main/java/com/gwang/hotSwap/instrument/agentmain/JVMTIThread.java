package com.gwang.hotSwap.instrument.agentmain;

import com.sun.tools.attach.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by wanggang on 2018/8/5.
 */
public class JVMTIThread {
    public static void main(String[] args)
            throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            if (vmd.displayName().endsWith("Account")) {
                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                virtualMachine.loadAgent("/Users/wanggang/Desktop/instument/javaAgentDemo-AgentMainDemo.jar", "test");
                System.out.println("ok");
                virtualMachine.detach();
            }
        }
    }
}
