package com.springframework.security.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springframework.security.ServletInitializer;
import com.springframework.security.entity.AnimalEntity;
import com.springframework.security.entity.AnimalForm;
import com.springframework.security.service.AnimalService;
//@Controller
public class ZooController {
	private static final Logger log = LoggerFactory.getLogger(ZooController.class);  
	 @Autowired  
     private AnimalService service;  
       
     @RequestMapping(path = "/zoo/zoolist", method = RequestMethod.GET)  
     public ModelAndView showZooList(){  
         //�����ݿ�ȡ������  
         List<AnimalEntity> animals = service.getAllAnimals();  
         ModelAndView mav = new ModelAndView();  
         mav.setViewName("zoo/zoolist");  
         mav.addObject("animalForm", new AnimalForm());  
         mav.addObject("animalsList", animals);  
         return mav;  
     } 
     
     @RequestMapping(path = "/zoo/zoolist", params = {"save"}, method = RequestMethod.POST)
     public String doAdd(Model model, @Valid AnimalForm form, BindingResult result){
         if(result.hasErrors()){
             model.addAttribute("MSG", "��������");
         }else{
             //�������ݵ����ݿ�
             service.insertOne(this.copyDataFromForm2Entity(form));
             model.addAttribute("MSG", "�ύ�ɹ���");
         }
         //�����ݿ�ȡ������
         List<AnimalEntity> animals = service.getAllAnimals();
         model.addAttribute("animalsList", animals);
         return "zoo/zoolist";
     }
     //��form�������copy��entity��
     private AnimalEntity copyDataFromForm2Entity(AnimalForm form){
         AnimalEntity entity = new AnimalEntity();
         entity.setName(form.getOname());
         entity.setCount(Integer.valueOf(form.getOcount()));
         entity.setMemo(form.getMemo());
         log.info("entity name: "+entity.getName()+",Memo:"+entity.getMemo());   
         return entity;
     }
/*	@RequestMapping(path = "/list", params = { "save" }, method = RequestMethod.POST)
	public ModelAndView doAdd(ModelAndView model, @Valid AnimalForm animalForm,
			BindingResult result) {
		System.out.println("��������" + animalForm.getOname());
		System.out.println("������" + animalForm.getOcount());
		System.out.println("��ע��" + animalForm.getMemo());
		if (result.hasErrors()) {
			model.addObject("MSG", "��������");
		} else {
			model.addObject("MSG", "�ύ�ɹ���");
		}
		model.setViewName("zoo/zoolist");
		return model;
	}*/
	
	 @RequestMapping(method = RequestMethod.GET)
	    public String initForm(ModelMap model)
	    {
		    AnimalForm animalForm = new AnimalForm();	       
	        model.addAttribute("animalForm", animalForm);
	        return "zoo/zoolist";
	    }
	 
	
}
