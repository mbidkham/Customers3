package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.dto.ResponseStatus;
import com.example.demo.model.dao.CustomerDao;
import com.example.demo.model.dao.LegalCustomerDao;
import com.example.demo.model.dao.RealCustomerDao;
import com.example.demo.model.entity.Customer;
import com.example.demo.model.entity.LegalCustomer;
import com.example.demo.model.entity.RealCustomer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

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
import java.util.stream.Stream;

@RestController
public class Controller {

    private RealCustomerDao realCustomerDao;
    private LegalCustomerDao legalCustomerDao;
    public Controller(RealCustomerDao realCustomerDao, LegalCustomerDao legalCustomerDao){
        this.realCustomerDao=realCustomerDao;
        this.legalCustomerDao=legalCustomerDao;
    }


    @RequestMapping(value = "/ws/menu/getUserMenu", method = RequestMethod.POST)
    public ResponseDto<MenuItmDto> getUserMenu() {
        MenuItmDto menuItmDto = new MenuItmDto(null, null, null, new ArrayList<MenuItmDto>(Arrays.asList(
                new MenuItmDto(MenuItemType.MENU, "کاربر جدید :", null, new ArrayList<MenuItmDto>(Arrays.asList(
                        new MenuItmDto(MenuItemType.PAGE, "ثبت کاربر حقیقی", new UIPageDto(null,"real.xml"), new ArrayList<MenuItmDto>()),
                        new MenuItmDto(MenuItemType.PAGE, "ثبت کاربر حقوقی", new UIPageDto(null,"legal.xml"), new ArrayList<MenuItmDto>())))),
                     //   new MenuItmDto(MenuItemType.MENU, " نمایش لیست کاربران ", new UIPageDto(null,"show.xml"),null),
                        new MenuItmDto(MenuItemType.PAGE, " جستجو(مشتریان حقیقی)  ", new UIPageDto(null,"searchReal.xml"),null),
                        new MenuItmDto(MenuItemType.PAGE, " جستجو(مشتریان حقوقی) ", new UIPageDto(null,"searchLegal.xml"),null)
        )));

        return new ResponseDto(ResponseStatus.Ok, menuItmDto, null, null);
    }



    @RequestMapping(value = "/ws/uipage/getPage", method = RequestMethod.POST)
    public ResponseDto<String> getPage(@RequestParam String name) throws IOException {
        return new ResponseDto(ResponseStatus.Ok, readFile(name, StandardCharsets.UTF_8), null, null);
    }


    @RequestMapping(value = "/ws/saveLegalCustomer", method = RequestMethod.POST)
    public ResponseDto<String> saveLegalCustomer(@RequestBody LegalCustomer legalCustomer){

        legalCustomerDao.save(legalCustomer);
        return new ResponseDto(ResponseStatus.Ok, "", "اطلاعات ذخیره شد.",null);
    }




    @RequestMapping(value = "/ws/saveRealCustomer", method = RequestMethod.POST)
    public ResponseDto<String> saveRealCustomer(@Valid @RequestBody RealCustomer realCustomer){
//        customerDtos.add(realCustomer);
         realCustomerDao.save(realCustomer);

        return new ResponseDto(ResponseStatus.Ok, "", "اطلاعات ذخیره شد.",null);
    }
//
//    @RequestMapping(value = "/ws/show", method = RequestMethod.POST)
//    public  ResponseDto< List<CustomerDto> > show(){
//
//        return new ResponseDto(ResponseStatus.Ok, customerDtos, "اطلاعات ذخیره شد.",null);
//    }



    @RequestMapping(value = "/ws/searchLegal", method = RequestMethod.POST)
    public ResponseDto<CustomerDto> searchLegal(@Valid @RequestParam String legalCode){


//        Stream<CustomerDto> result1 = customerDtos.stream()
//                .filter((i) ->   i instanceof LegalCustomerDto && Objects.equals(legalCode,(i.getName())));
//
//        Object [] obj=result1.toArray();

        LegalCustomer byLegalCode = legalCustomerDao.findByLegalCode(legalCode);


        if(Objects.isNull(byLegalCode))
        return new ResponseDto(ResponseStatus.Error, null,"پیدا نشد!",null);

        else
            return new ResponseDto(ResponseStatus.Ok, byLegalCode,"",null);
    }



    @RequestMapping(value = "/ws/searchReal", method = RequestMethod.POST)
    public ResponseDto<CustomerDto> searchReal(@RequestParam String nationalCode){

//        Stream<CustomerDto> result = customerDtos.stream()
//                .filter((i) ->   i instanceof RealCustomerDto && Objects.equals(nationalCode,(i.getName())));
//
//        Object [] obj=result.toArray();
        RealCustomer byNationalCode = realCustomerDao.findByNationalCode(nationalCode);
        if(Objects.isNull(byNationalCode))
            return new ResponseDto(ResponseStatus.Error,null,"پبدا نشد !",null);

        else
            return new ResponseDto(ResponseStatus.Ok, byNationalCode,"",null);
    }

    @RequestMapping(value = "/ws/findAllReal", method = RequestMethod.POST)
    public ResponseDto<List <RealCustomerDto> > findAllReal(@RequestBody SearchDto searchDto){
//        Stream<CustomerDto> result = customerDtos.stream()
//                .filter((i) ->   i instanceof LegalCustomerDto &&(((i.getName())).contains(searchDto.getName())));
//
//        Object [] obj=result.toArray();
//
//
//
//        if(obj.length!=0)
//            return new ResponseDto(ResponseStatus.Ok, obj,null,null);

        return  new ResponseDto(ResponseStatus.Error, null,"پیدا نشد!",null);
    }
    @RequestMapping(value = "/ws/findAllLegal", method = RequestMethod.POST)
    public ResponseDto<List <RealCustomerDto> > findAllLegal(@RequestBody SearchDto searchDto){
//        Stream<CustomerDto> result = customerDtos.stream()
//                .filter((i) ->   i instanceof RealCustomerDto &&((i.getName())).contains(searchDto.getName()));
//
//        Object [] obj=result.toArray();
//
//
//
//        if(obj.length!=0)
//            return new ResponseDto(ResponseStatus.Ok, obj,null,null);
//
        return  new ResponseDto(ResponseStatus.Error, null,"پیدا نشد!",null);
    }
    @RequestMapping(value = "/ws/update", method = RequestMethod.POST)
    public ResponseDto< RealCustomerDto> update(@RequestBody ContactDto contactDto){
//        Stream<CustomerDto> result = customerDtos.stream()
//                .filter((i) ->   i instanceof RealCustomerDto &&((i.getName())).contains(searchDto.getName()));
//
//        Object [] obj=result.toArray();
//
//
//
//        if(obj.length!=0)
//            return new ResponseDto(ResponseStatus.Ok, obj,null,null);


//
        return  new ResponseDto(ResponseStatus.Error, null,"پیدا نشد!",null);
    }

    String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(new ClassPathResource(path).getFile().getPath()));
        return new String(encoded, encoding);
    }
}
