package com.example.demo.service;

import com.example.demo.dao.RequestDao;
import com.example.demo.dao.Requests;
import com.example.demo.model.Element;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RequestService {
    @Autowired
    private RequestDao requestDao;

    public Element saveRequest(Element element) {

        Requests requestsModel = createFrom(element);
        requestDao.save(requestsModel);
        return element;
    }

    private Requests createFrom(Element element) {
        Requests requests = new Requests();
        requests.setName(element.getName());
        requests.setLastName(element.getLastName());
        return requests;
    }
}
