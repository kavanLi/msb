package com.example.demo.designpattern.cor.servlet;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: kavanLi-R7000
 * @create: 2024-03-04 14:34
 * To change this template use File | Settings | File and Code Templates.
 */
public class Servlet_Main {
    public static void main(String[] args) {
        Request request = new Request();
        request.str = "大家好:)，<script>，欢迎访问 mashibing.com ，大家都是996 ";
        Response response = new Response();
        response.str = "response";

        FilterChain chain = new FilterChain();
        chain.add(new HTMLFilter()).add(new SensitiveFilter());
        chain.doFilter(request, response);
        System.out.println(request.str);
        System.out.println(response.str);
    }
}

class Request {
    String str;
}

class Response {
    String str;
}


interface Filter {
    void doFilter(Request request, Response response, FilterChain chain);
}

class SensitiveFilter implements Filter {
    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        request.str = request.str.replaceAll("996", "955");
        chain.doFilter(request, response);
        response.str += "--SensitiveFilter()";
    }
}

class HTMLFilter implements Filter {
    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        request.str = request.str.replaceAll("<", "[").replaceAll(">", "]");
        chain.doFilter(request, response);
        response.str += "--HTMLFilter()";
    }
}

class FilterChain {
    List <Filter> filters = new ArrayList <>();

    int index = 0;

    public FilterChain add(Filter f) {
        filters.add(f);
        return this;
    }

    public void doFilter(Request request, Response response) {
        if (index == filters.size()) {
            return;
        }
        Filter filter = filters.get(index);
        index++;

        filter.doFilter(request, response, this);
    }
}