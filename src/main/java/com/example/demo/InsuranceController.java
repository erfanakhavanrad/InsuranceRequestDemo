package com.example.demo;

import com.example.demo.dao.ICalculation;
import com.example.demo.model.Element;
import com.example.demo.model.GeneralMessage;
import com.example.demo.model.InsurancePlan;
import com.example.demo.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/insurance")
public class InsuranceController {

    private final ICalculation iCalculation;
    private final RequestService requestService;

    public InsuranceController(ICalculation iCalculation, RequestService requestService) {
        this.iCalculation = iCalculation;
        this.requestService = requestService;
    }

    @GetMapping
    public ResponseEntity<GeneralMessage> Instructions() {
        GeneralMessage generalMessage;

        String welcomeMessage = "Hello. Welcome to the main branch for Iran Motor Cycle Insurance." +
                "Please state your Body Insurance amount and Third Party insurance amount." +
                "PLEASE BE WARE: Prices are in Million Toman";
        generalMessage = new GeneralMessage(welcomeMessage, 131312);

        return new ResponseEntity<>(generalMessage, HttpStatus.OK);
    }


    @PostMapping(path = "/request")
    public ResponseEntity<Element> retrieveRequest(@RequestBody Element element) throws Exception {

//        Element element1 = iCalculation.saveInsuranceRequest(element);

        return new ResponseEntity<Element>(requestService.saveRequest(element), HttpStatus.OK);
    }


    @PostMapping(path = "/confirmation")
    public ResponseEntity<Element> confirmRequest(@RequestBody Element element) throws Exception {

//        Element element1 = iCalculation.saveInsuranceRequest(element);

        return new ResponseEntity<Element>(iCalculation.approveRequest(element), HttpStatus.OK);
    }


    @PostMapping(path = "/calculate")
    public ResponseEntity<InsurancePlan> calculateFee(@RequestBody Element element) throws Exception {

        InsurancePlan insurancePlan = iCalculation.yearlyFeeCalculator(element);
        return new ResponseEntity<InsurancePlan>(insurancePlan, HttpStatus.OK);
    }



}
