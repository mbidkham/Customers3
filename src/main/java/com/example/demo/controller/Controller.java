package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.dto.ResponseStatus;
import com.example.demo.model.dao.LegalCustomerDao;
import com.example.demo.model.dao.RealCustomerDao;
import com.example.demo.model.entity.LegalCustomer;
import com.example.demo.model.entity.RealCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
public class Controller {
    private static Logger logger=LoggerFactory.getLogger(Controller.class);


    private RealCustomerDao realCustomerDao;
    private LegalCustomerDao legalCustomerDao;

    public Controller(RealCustomerDao realCustomerDao, LegalCustomerDao legalCustomerDao){
        this.realCustomerDao=realCustomerDao;
        this.legalCustomerDao=legalCustomerDao;

    }

    private Integer customerId=0;
    private Integer customerVersion=0;
    private Integer addressId=0;
    private Integer addressVersion=0;
    private Integer contactVersion=0;
    private Integer contactId=0;


    private boolean onUpdate;

    @RequestMapping(value = "/ws/menu/getUserMenu", method = RequestMethod.POST)
    public ResponseDto<MenuItmDto> getUserMenu() {
        MenuItmDto menuItmDto = new MenuItmDto(null, null, null, new ArrayList<MenuItmDto>(Arrays.asList(
                new MenuItmDto(MenuItemType.MENU, "کاربر جدید :", null, new ArrayList<MenuItmDto>(Arrays.asList(
                        new MenuItmDto(MenuItemType.PAGE, "ثبت کاربر حقیقی", new UIPageDto(null,"real.xml"), new ArrayList<MenuItmDto>()),
                        new MenuItmDto(MenuItemType.PAGE, "ثبت کاربر حقوقی", new UIPageDto(null,"legal.xml"), new ArrayList<MenuItmDto>())))),
                     //   new MenuItmDto(MenuItemType.MENU, " نمایش لیست کاربران ", new UIPageDto(null,"show.xml"),null),
                        new MenuItmDto(MenuItemType.MENU, "جستجو  :", null, new ArrayList<MenuItmDto>(Arrays.asList(
                        new MenuItmDto(MenuItemType.PAGE, "جستجو کاربر حقیقی ", new UIPageDto(null,"searchReal.xml"), new ArrayList<MenuItmDto>()),
                         new MenuItmDto(MenuItemType.PAGE, "جستجوی پیشرفته(حقیقی) ", new UIPageDto(null,"advanceRealSearch.xml"), new ArrayList<MenuItmDto>()),
                        new MenuItmDto(MenuItemType.PAGE, "جستجو کاربر حقوقی ", new UIPageDto(null,"searchLegal.xml"), new ArrayList<MenuItmDto>()),
                        new MenuItmDto(MenuItemType.PAGE, "جستجوی پیشرفته(حقوقی) ", new UIPageDto(null,"advanceLegalSearch.xml"), new ArrayList<MenuItmDto>())
                        ))),
                        new MenuItmDto(MenuItemType.PAGE, " ویرایش اطلاعات(مشتریان حقیقی) ", new UIPageDto(null,"updateReal.xml"),null),
                        new MenuItmDto(MenuItemType.PAGE, " ویرایش اطلاعات(مشتریان حقوقی) ", new UIPageDto(null,"updateLegal.xml"),null)

                )));

        return new ResponseDto(ResponseStatus.Ok, menuItmDto, null, null);
    }



    @RequestMapping(value = "/ws/uipage/getPage", method = RequestMethod.POST)
    public ResponseDto<String> getPage(@RequestParam String name) throws IOException {
        return new ResponseDto(ResponseStatus.Ok, readFile(name, StandardCharsets.UTF_8), null, null);
    }


    @RequestMapping(value = "/ws/saveLegalCustomer", method = RequestMethod.POST)
    @Transactional(rollbackOn =Exception.class)
    public ResponseDto<String> saveLegalCustomer(@Valid @RequestBody LegalCustomer legalCustomer) {

        if (onUpdate){
            legalCustomer.setId(customerId);
            legalCustomer.setVersion(customerVersion);
            if (Objects.nonNull(legalCustomer.getAddress()))
            {
                legalCustomer.getAddress().setId(customerId);
                legalCustomer.getAddress().setVersion(customerVersion);
            }try{

                legalCustomerDao.save(legalCustomer);
                logger.info("legalCustomer updated :"+legalCustomer.toString());
                onUpdate=false;
            }catch (Exception e){
                logger.info("optimistic locking ");
                return new ResponseDto(ResponseStatus.Error, null, null,new ResponseException("optimistic locking"));
            }


        }
        else if(Objects.isNull(legalCustomerDao.findByLegalCode(legalCustomer.getLegalCode()))){
            legalCustomerDao.save(legalCustomer);
            logger.info("legalCustomer added :"+legalCustomer.toString());



        }
        else{
            logger.info("legalCustomer's code is duplicated :");
            return new ResponseDto(ResponseStatus.Error, null, null,new ResponseException("قبلا ثبت نام کرده اید"));

        }





        return new ResponseDto(ResponseStatus.Ok, null, "اطلاعات ذخیره شد.",null);
    }




    @RequestMapping(value = "/ws/saveRealCustomer", method = RequestMethod.POST)
    @Transactional(rollbackOn =Exception.class)
    public ResponseDto<String> saveRealCustomer( @Valid @RequestBody RealCustomer realCustomer){


        if (onUpdate){
            realCustomer.setId(customerId);
            realCustomer.setVersion(customerVersion);

            if (Objects.nonNull(realCustomer.getAddress())) {
                realCustomer.getAddress().setId(addressId);
                realCustomer.getAddress().setVersion(addressVersion);

            }
            if (Objects.nonNull(realCustomer.getContact())) {
                realCustomer.getContact().setId(contactId);
                realCustomer.getContact().setVersion(contactVersion);
            }
            try{

                realCustomerDao.save(realCustomer);
                onUpdate=false;
                logger.info("realCustomer updated :"+realCustomer.toString());
            }catch (Exception e){
                logger.info("optimistic locking ");
                return new ResponseDto(ResponseStatus.Error,  null, null,new ResponseException("optimistic locking"));
            }

        }
        else if(Objects.isNull(realCustomerDao.findByNationalCode(realCustomer.getNationalCode()))){
            realCustomerDao.save(realCustomer);
            logger.info("realCustomer added :"+realCustomer.toString());

        }

        else{
            logger.info("realCustomer 's national id is  duplicated :");
            return new ResponseDto(ResponseStatus.Error, null, null,new ResponseException("قبلا ثبت نام کرده اید"));

        }

        return new ResponseDto(ResponseStatus.Ok, null, "اطلاعات ذخیره شد.",null);
    }


    @RequestMapping(value = "/ws/searchLegal", method = RequestMethod.POST)
    public ResponseDto<CustomerDto> searchLegal(@Valid @RequestParam String legalCode){

//        Stream<CustomerDto> result1 = customerDtos.stream()
//                .filter((i) ->   i instanceof LegalCustomerDto && Objects.equals(legalCode,(i.getName())));
//
//        Object [] obj=result1.toArray();

        LegalCustomer byLegalCode = legalCustomerDao.findByLegalCode(legalCode);

        if(Objects.isNull(byLegalCode)){
            logger.info("legalCustomer doesnt exist:  :"+legalCode);
            return new ResponseDto(ResponseStatus.Error, null,null,new ResponseException("پیدا نشد!"));


        }



        else
            return new ResponseDto(ResponseStatus.Ok, byLegalCode,null,null);
    }



    @RequestMapping(value = "/ws/searchReal", method = RequestMethod.POST)
    public ResponseDto<CustomerDto> searchReal(@RequestParam String nationalCode){

//        Stream<CustomerDto> result = customerDtos.stream()
//                .filter((i) ->   i instanceof RealCustomerDto && Objects.equals(nationalCode,(i.getName())));
//
//        Object [] obj=result.toArray();
        RealCustomer byNationalCode = realCustomerDao.findByNationalCode(nationalCode);

        if(Objects.isNull(byNationalCode)){
            logger.info("legalCustomer doesnt exist:  :"+nationalCode);
            return new ResponseDto(ResponseStatus.Error,null,"پبدا نشد !",new ResponseException("پیدا نشد!"));

        }


        else
            return new ResponseDto(ResponseStatus.Ok, byNationalCode,"",null);
    }

    @RequestMapping(value = "/ws/advanceLegalSearch", method = RequestMethod.POST)
    public ResponseDto<List <LegalCustomer> > advanceLegalSearch(@RequestParam String name){
        List<LegalCustomer> byName = legalCustomerDao.findByName(name.toUpperCase());

        if(Objects.isNull(byName))
            return  new ResponseDto(ResponseStatus.Error, null,null,new ResponseException("پیدا نشد!"));


        return  new ResponseDto(ResponseStatus.Ok, byName,null,null);
    }
    @RequestMapping(value = "/ws/advanceRealSearch", method = RequestMethod.POST)

    public ResponseDto<List <RealCustomerDto> > advanceRealSearch(@RequestParam String name){

        List<RealCustomer> byName = realCustomerDao.findByName(name.toUpperCase());

        if(Objects.nonNull(byName))
            return  new ResponseDto(ResponseStatus.Ok, byName,null,null);

        return  new ResponseDto(ResponseStatus.Error, null,null,new ResponseException("پیدا نشد!"));
    }
    @RequestMapping(value = "/ws/updateLegal", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto< RealCustomerDto> updateLegal(@RequestParam String  legalCode){


        LegalCustomer byLegalCode = legalCustomerDao.findByLegalCode(legalCode);

        if(Objects.nonNull(byLegalCode)){
            onUpdate=true;
            customerId=byLegalCode.getId();
            customerVersion=byLegalCode.getVersion();

            if (Objects.nonNull(byLegalCode.getAddress())) {
                contactId=byLegalCode.getAddress().getId();
                contactVersion=byLegalCode.getAddress().getVersion();
            }

            return  new ResponseDto(ResponseStatus.Ok, byLegalCode,null,null);
        }
        else
//
        return  new ResponseDto(ResponseStatus.Error, null,null,new ResponseException("موجود نیست !"));
    }
    @RequestMapping(value = "/ws/updateReal", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto< RealCustomerDto> updateReal(@RequestParam String  nationalCode){


        RealCustomer byReal = realCustomerDao.findByNationalCode(nationalCode);

        if(Objects.nonNull(byReal)){
            onUpdate=true;
            customerId=byReal.getId();
            customerVersion=byReal.getVersion();
            if (Objects.nonNull(byReal.getAddress())) {
                addressId=byReal.getAddress().getId();
                addressId=byReal.getAddress().getVersion();
            }
            if (Objects.nonNull(byReal.getContact())) {
                contactId=byReal.getContact().getId();
                contactVersion=byReal.getContact().getVersion();
            }
            return  new ResponseDto(ResponseStatus.Ok, byReal,null,null);
        }
        else
//
            return  new ResponseDto(ResponseStatus.Error, null,null,new ResponseException("موجود نیست !"));
    }

    String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(new ClassPathResource(path).getFile().getPath()));
        return new String(encoded, encoding);
    }
}
