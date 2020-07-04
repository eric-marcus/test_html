package com.example.test_html.controller;

import net.minidev.json.JSONObject;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
//表明该类为处理器
public class solr_test
{
    private String url = "http://eric0.com/solr/solr_song";
    static int num =0;

    @RequestMapping("/ask")
    //映射请求，该处理器可处理的url请求，其中url是相对的且可以映射类和方法
    public JSONObject test(String msg) throws IOException, SolrServerException
    {
        //建立连接体solrj / api
        HttpSolrClient server = new HttpSolrClient.Builder(url).build();

        //查询对象
        SolrQuery query = new SolrQuery();
        query.setQuery("msg:"+msg);
        query.set("wt","json");
        //执行查询
        QueryResponse queryResponse = server.query(query);
        //读取文件列表
        SolrDocumentList resultList = queryResponse.getResults();
        //细分文件

        JSONObject result = new JSONObject();
        int count = 0;
        for (SolrDocument solrDocument : resultList)
        {
            count++;
            System.out.println(solrDocument);
            int dd = solrDocument.size();
            result.put(String.valueOf(count),solrDocument.get("msg"));
        }

        return result;
    }

    //跳转到index.html页面
    @RequestMapping("/")
    public String index()
    {
        return "index.html";
    }

    //获取index传回的信息 / ajax异步
    @RequestMapping("/get")
    @ResponseBody
    public String logi(String msg, HttpSession session) throws IOException, SolrServerException {
        //System.out.println("test");
        num++;
        System.out.println(num);
        System.out.println("name: "+msg);

        JSONObject result = test(msg);
        System.out.println(result);
        return result.toJSONString();
    }



    public ModelAndView login(String msg, HttpSession session) throws IOException, SolrServerException {
        //System.out.println("test");
        num++;
        System.out.println(num);
        System.out.println("name: "+msg);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        //String back = test(msg);
        //modelAndView.addObject("back",back);
        return modelAndView;
    }
}