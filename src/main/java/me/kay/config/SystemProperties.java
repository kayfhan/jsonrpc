package me.kay.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class SystemProperties {

    public static SystemProperties DEFAULT = new SystemProperties();

    private static final boolean ENABLE_WEB = true;

    private Config config;

    public SystemProperties(){
        String fileName = "system.conf";
        config = ConfigFactory.parseResources(fileName);
        if (!config.isEmpty()){
            log.info("Successful to load \"{}\" config file.", fileName);
        }
    }

    public boolean isEnableWeb(){
        if (config.hasPath("server.web.enable"))
            return config.getBoolean("server.web.enable");
        return ENABLE_WEB;
    }

    public List<String> peerDiscoveryIPList() {
        if (config.hasPath("peer.discovery.ip.list"))
            return config.getStringList("peer.discovery.ip.list");
         return null;
    }

    public boolean serverAcceptConnections(){
        if (config.hasPath("server.acceptConnections"))
            return config.getBoolean("server.acceptConnections");
        return true;
    }

    public void print(){
        Set<Map.Entry<String, ConfigValue>> objects = config.entrySet();
        for (Map.Entry<String, ConfigValue> object: objects) {
            System.out.println(object.getKey() + " = " + object.getValue());
        }
    }

    public static void main(String[] args) {
        SystemProperties sysProp = new SystemProperties();
        sysProp.print();
        sysProp.peerDiscoveryIPList().forEach(ip -> System.out.println(ip));

        boolean acceptConnections = sysProp.serverAcceptConnections();
        System.out.println("Accept Connections: " + acceptConnections);
    }
}
