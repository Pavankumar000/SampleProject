package com.adobe.aem.guides.wknd.core.models;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import com.adobe.aem.guides.wknd.core.beans.NewsListBean;

@Model(adaptables = SlingHttpServletRequest.class,adapters = NewsList.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NewsList {

    @ChildResource(name = "addYear")
    Resource year;
    

    public ArrayList<NewsListBean> getNewsListItems(){
        ArrayList<NewsListBean> object = new ArrayList<>();
       
        Iterator<Resource> itemResources = year.listChildren();
        while (itemResources.hasNext()) {
            NewsListBean navigationListBean = new NewsListBean();
            Resource yearitemResource = itemResources.next();
            String year = yearitemResource.getValueMap().get("year",String.class);
            navigationListBean.setYear(year);
            if(yearitemResource.getChildren()!=null){
                Resource monthResource = yearitemResource.getChild("addMonth");
                Iterator<Resource> childResources = monthResource.listChildren();
                while (childResources.hasNext()) {
                    Resource monthItemResource = childResources.next();
                    String month = monthItemResource.getValueMap().get("month",String.class);
                    String newsTitle = monthItemResource.getValueMap().get("newsTitle",String.class);
                    String newsText = monthItemResource.getValueMap().get("newsText",String.class);
                    navigationListBean.setMonth(month);
                    navigationListBean.setNewsTitle(newsTitle);
                    navigationListBean.setNewsContent(newsText);
                  
                }
            }
            object.add(navigationListBean); 
        }
        return object;

    }
    
}