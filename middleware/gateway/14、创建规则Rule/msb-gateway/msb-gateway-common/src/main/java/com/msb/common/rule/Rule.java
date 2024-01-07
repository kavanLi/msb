package com.msb.common.rule;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 核心规则类
 */
public class Rule implements Comparable<Rule>, Serializable {

    /**
     * 规则ID ,全局唯一
     */
    private String id;

    /**
     * 规则名称
     */
    private String name;

    /**
     * 协议
     */
    private String protocol;

    /**
     * 规则排序，对应的场景：一个路径对应多条规则，然后只执行一条规则的情况
     */
    private Integer order;

    private Set<FilterConfig> filterConfigSet = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Set<FilterConfig> getFilterConfigSet() {
        return filterConfigSet;
    }

    public void setFilterConfigSet(Set<FilterConfig> filterConfigSet) {
        this.filterConfigSet = filterConfigSet;
    }

    public Rule() {
        super();
    }

    public Rule(String id, String name, String protocol, Integer order, Set<FilterConfig> filterConfigSet) {
        this.id = id;
        this.name = name;
        this.protocol = protocol;
        this.order = order;
        this.filterConfigSet = filterConfigSet;
    }

    public static class FilterConfig{

        /**
         * 过滤器唯一ID
         */
        private String id;

        /**
         *过滤器规则描述： {“timeout”：500}
         */
        private String config;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getConfig() {
            return config;
        }

        public void setConfig(String config) {
            this.config = config;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }

        @Override
        public boolean equals(Object o) {
            if(this == o){
                return true;
            }
            if(o == null || getClass() != o.getClass()){
                return false;
            }
            FilterConfig that = (FilterConfig) o;
            return id.equals(that.id);
        }
    }

    /**
     * 向规则中添加过滤器
     * @param filterConfig
     * @return
     */
    public boolean addFilterConfig(FilterConfig filterConfig){
        return filterConfigSet.add(filterConfig);
    }


    /**
     * 通过指定的FilterId获取FilterConfig
     * @param id
     * @return
     */
    public FilterConfig getFilterConfig(String id){
        for (FilterConfig filterConfig : filterConfigSet) {
            if(filterConfig.getId().equalsIgnoreCase(id)){
                return filterConfig;
            }
        }
        return null;
    }



    @Override
    public int compareTo(Rule o) {
        int orderCompare = Integer.compare(getOrder(), o.getOrder());
        if(orderCompare == 0){
            return getId().compareTo(o.getId());
        }
        return orderCompare;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {

        if(this == obj){
            return true;
        }
        if(obj == null || getClass() != obj.getClass()){
            return false;
        }
        Rule  that = (Rule) obj;
        return id.equals(that.id);
    }
}
