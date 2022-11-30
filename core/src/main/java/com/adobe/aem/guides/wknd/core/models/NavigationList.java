package com.adobe.aem.guides.wknd.core.models;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import com.adobe.aem.guides.wknd.core.beans.NavigationListBean;

@Model(adaptables = SlingHttpServletRequest.class,adapters = NavigationList.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NavigationList {

    @ChildResource(name = "navigationItems")
    Resource navigationiItems;

    public ArrayList<NavigationListBean> getNavigationItems(){
        ArrayList<NavigationListBean> object = new ArrayList<>();
       
        Iterator<Resource> itemResources = navigationiItems.listChildren();
        while (itemResources.hasNext()) {
            NavigationListBean navigationListBean = new NavigationListBean();
            Resource itemResource = itemResources.next();
            String title = itemResource.getValueMap().get("title",String.class);
            String redirectPath = itemResource.getValueMap().get("redirectLink",String.class);
            navigationListBean.setTitle(title);
            navigationListBean.setPagePath(redirectPath);
            object.add(navigationListBean);
        }
        return object;

    }
    
}