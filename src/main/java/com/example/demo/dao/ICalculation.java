package com.example.demo.dao;

import com.example.demo.model.Element;
import com.example.demo.model.InsurancePlan;

public interface ICalculation {

    InsurancePlan yearlyFeeCalculator(Element element) throws Exception;

    Element saveInsuranceRequest(Element element) throws Exception;
    Element approveRequest(Element element) throws Exception;

    boolean trackingCodeValidator(int trackingCode) throws Exception;

}
