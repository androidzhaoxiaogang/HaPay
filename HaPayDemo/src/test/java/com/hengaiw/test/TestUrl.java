package com.hengaiw.test;

import java.net.URL;

public class TestUrl {
	 public static void main(String[] args) { 

         String host = "www.mpaypass.com.cn"; 
         String file = "/policy.html"; 

         String[] schemes = {"http", "https", "ftp", "mailto", "telnet", "file", "ldap", "gopher", 
                         "jdbc", "rmi", "jndi", "jar", "doc", "netdoc", "nfs", "verbatim", "finger", "daytime", 
                         "systemresource"}; 

         for (int i = 0; i < schemes.length; i++) { 
                 try { 
                         URL u = new URL(schemes[i], host, file); 
                         System.out.println(schemes[i] + " is supported\r\n"); 
                 } catch (Exception ex) { 
                         System.out.println(schemes[i] + " is not supported\r\n"); 
                 } 
         } 
 } 
}
