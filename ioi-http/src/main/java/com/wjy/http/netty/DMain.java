package com.wjy.http.netty;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

public class DMain {
    static class Out {
        private int id;
        private String name;
        private Map map;

        public Out(int id, String name, Map map) {
            this.id = id;
            this.name = name;
            this.map = map;
        }

        public Out() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map getMap() {
            return map;
        }

        public void setMap(Map map) {
            this.map = map;
        }

        @Override
        public String toString() {
            return "Out{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", map=" + map +
                    '}';
        }
    }
    public static void main(String[] args) throws NoSuchMethodException, IOException {
        /*String str = "/a/b/c/d/";
        String[] split = str.split("/");
        System.out.println(split.length);
        for (String s : split) {
            System.out.print(s+"/");
        }*/
        /*Class<Bc> aClass = Bc.class;
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            Parameter[] parameters = method.getParameters();
            System.out.println(method.getName()+"====");
            for (Parameter parameter : parameters) {
                System.out.println(parameter.getName());
            }
        }*/
        /*String s = JsonUtils.toJson(null);
        System.out.println(s);*/
        /*String str = "/123";
        int i = "/".indexOf("/");
        System.out.println(i);
        String substring = str.substring(0, 0);
        System.out.println(substring);*/
        /*File file = new File("/Users/jocker/opt/workspace/git/ioi/ioi-http/src/main/resources/web/404.html");
        String s = Files.probeContentType(Path.of(file.toURI()));
        System.out.println(s);*/
        File file = new File("/Users/jocker/opt/workspace/git/ioi/ioi-http/src/main/resources/web/404.html");
        RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r");
        System.out.println(randomAccessFile.length());
    }
}
