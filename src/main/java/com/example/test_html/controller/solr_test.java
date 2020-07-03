package com.example.test_html.controller;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
//表明该类为处理器
public class solr_test
{
    private String url = "http://eric0.com/solr/solr_song_data";

    @RequestMapping("/ask")
    //映射请求，该处理器可处理的url请求，其中url是相对的且可以映射类和方法
    public void test() throws IOException, SolrServerException
    {
        //建立连接体solrj / api
        HttpSolrClient server = new HttpSolrClient.Builder(url).build();

        //查询对象
        SolrQuery query = new SolrQuery();
        query.setQuery("id0:354");

        //提取信息
        QueryResponse responseinfo = server.query(query);
        System.out.println(responseinfo);
    }

    @RequestMapping("/")
    public String index()
    {
        return "index";
    }

    @RequestMapping("/get")
    @ResponseBody
    public void login(String name, String password, HttpSession session)
    {
        //System.out.println("test");
        System.out.println("name:"+name+"  password:"+password);
    }
}