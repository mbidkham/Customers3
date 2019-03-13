package com.rayan.bank.controller;


import com.rayan.bank.dto.*;
import com.rayan.bank.dto.ResponseStatus;
import com.rayan.bank.model.dao.LegalCustomerDao;
import com.rayan.bank.model.dao.RealCustomerDao;
import com.rayan.bank.model.dao.SavingAccountDao;
import com.rayan.bank.model.entity.LegalCustomer;
import com.rayan.bank.model.entity.RealCustomer;
import com.rayan.bank.model.entity.SavingAccount;
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
import java.util.*;

@RestController
public class CustomerController {

    private static Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private RealCustomerDao realCustomerDao;
    private LegalCustomerDao legalCustomerDao;
    private SavingAccountDao savingAccountDao;


    public CustomerController(RealCustomerDao realCustomerDao, LegalCustomerDao legalCustomerDao,SavingAccountDao savingAccountDao) {
        this.realCustomerDao = realCustomerDao;
        this.legalCustomerDao = legalCustomerDao;
        this.savingAccountDao=savingAccountDao;

    }


    @RequestMapping(value = "/ws/menu/getUserMenu", method = RequestMethod.POST)
    public ResponseDto<MenuItmDto> getUserMenu() {
        MenuItmDto menuItmDto = new MenuItmDto(null, null, null, new ArrayList<MenuItmDto>(Arrays.asList(
                new MenuItmDto(MenuItemType.MENU, "کاربر جدید :", null, new ArrayList<MenuItmDto>(Arrays.asList(
                        new MenuItmDto(MenuItemType.PAGE, "ثبت کاربر حقیقی", new UIPageDto(null, "real.xml"), new ArrayList<MenuItmDto>()),
                        new MenuItmDto(MenuItemType.PAGE, "ثبت کاربر حقوقی", new UIPageDto(null, "legal.xml"), new ArrayList<MenuItmDto>())))),
                new MenuItmDto(MenuItemType.MENU, "جستجو  :", null, new ArrayList<MenuItmDto>(Arrays.asList(
                        new MenuItmDto(MenuItemType.PAGE, "جستجو کاربر حقیقی ", new UIPageDto(null, "searchReal.xml"), new ArrayList<MenuItmDto>()),
                        new MenuItmDto(MenuItemType.PAGE, "جستجوی پیشرفته(حقیقی) ", new UIPageDto(null, "advanceRealSearch.xml"), new ArrayList<MenuItmDto>()),
                        new MenuItmDto(MenuItemType.PAGE, "جستجو کاربر حقوقی ", new UIPageDto(null, "searchLegal.xml"), new ArrayList<MenuItmDto>()),
                        new MenuItmDto(MenuItemType.PAGE, "جستجوی پیشرفته(حقوقی) ", new UIPageDto(null, "advanceLegalSearch.xml"), new ArrayList<MenuItmDto>())
                ))),
                new MenuItmDto(MenuItemType.PAGE, " ویرایش اطلاعات(مشتریان حقیفی) ", new UIPageDto(null, "updateReal.xml"), null),
                new MenuItmDto(MenuItemType.PAGE, " ویرایش اطلاعات(مشتریان حقوقی) ", new UIPageDto(null, "updateLegal.xml"), null),
                new MenuItmDto(MenuItemType.MENU, "ایجاد سپرده  :", null, new ArrayList<MenuItmDto>(Arrays.asList(
                        new MenuItmDto(MenuItemType.PAGE, "سپرده کاربران حقیقی ", new UIPageDto(null, "savingAccountForReal.xml"), new ArrayList<MenuItmDto>()),
                        new MenuItmDto(MenuItemType.PAGE, "سپرده کاربران حقوقی ", new UIPageDto(null, "savingAccountForLegal.xml"), new ArrayList<MenuItmDto>())))),
                new MenuItmDto(MenuItemType.MENU, "خدمات  :", null, new ArrayList<MenuItmDto>(Arrays.asList(
                        new MenuItmDto(MenuItemType.MENU, " کاربران حقیقی ", null, new ArrayList<MenuItmDto>(Arrays.asList(

                                new MenuItmDto(MenuItemType.PAGE, "برداشت  ", new UIPageDto(null, "withdrawal.xml"), new ArrayList<MenuItmDto>()),
                                new MenuItmDto(MenuItemType.PAGE, "واریز ", new UIPageDto(null, "deposit.xml"), new ArrayList<MenuItmDto>()),
                                new MenuItmDto(MenuItemType.PAGE, "انتفال وجه  ", new UIPageDto(null, "transferMoney.xml"), new ArrayList<MenuItmDto>()),
                                new MenuItmDto(MenuItemType.PAGE, "موجودی  ", new UIPageDto(null, "showBalance.xml"), new ArrayList<MenuItmDto>())

                        )))
//                        ,
//                        new MenuItmDto(MenuItemType.MENU, " کاربران حقوقی ", null, new ArrayList<MenuItmDto>(Arrays.asList(
//                                new MenuItmDto(MenuItemType.PAGE, "برداشت  ", new UIPageDto(null, "savingAccountForR9eal.xml"), new ArrayList<MenuItmDto>()),
//                                new MenuItmDto(MenuItemType.PAGE, "واریز ", new UIPageDto(null, "savingAccountForLegal9.xml"), new ArrayList<MenuItmDto>()),
//                                new MenuItmDto(MenuItemType.PAGE, "انتفال وجه  ", new UIPageDto(null, "savingAccountForReal.xml"), new ArrayList<MenuItmDto>()),
//                                new MenuItmDto(MenuItemType.PAGE, "موجودی  ", new UIPageDto(null, "savingAccountForLegal.xml"), new ArrayList<MenuItmDto>())
//
//
//                        )))
                )))

        )));

        return new ResponseDto(ResponseStatus.Ok, menuItmDto, null, null);
    }


    @RequestMapping(value = "/ws/uipage/getPage", method = RequestMethod.POST)
    public ResponseDto<String> getPage(@RequestParam String name) throws IOException {
        return new ResponseDto(ResponseStatus.Ok, readFile(name, StandardCharsets.UTF_8), null, null);
    }


    @RequestMapping(value = "/ws/saveLegalCustomer", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto<String> saveLegalCustomer(@Valid @RequestBody LegalCustomer legalCustomer) {

        if (Objects.nonNull(legalCustomer.getId()) && Objects.nonNull(realCustomerDao.findById(legalCustomer.getId()))) {
//            legalCustomer.setId(legalCustomer.getId());
            legalCustomerDao.save(legalCustomer);
            logger.info("legalCustomer updated :" + legalCustomer.toString());
            return new ResponseDto<>(ResponseStatus.Ok, null, "ویرایش با موفقیت انجام  شد !", null);


        }
        if (Objects.nonNull(legalCustomer.getLegalCode()) && Objects.nonNull(legalCustomer.getName())) {

            if (Objects.isNull(legalCustomerDao.findByLegalCode(legalCustomer.getLegalCode()))) {
                legalCustomerDao.save(legalCustomer);
                logger.info("legalCustomer added :" + legalCustomer.toString());
                return new ResponseDto<>(ResponseStatus.Ok, null, "اطلاعات ذخیره شد.", null);
            } else {
                logger.error("legalCustomer 's national id is  duplicated :");
                return new ResponseDto(ResponseStatus.Error, null, null, new ResponseException("قبلا ثبت نام کرده اید"));

            }

        } else {
            return new ResponseDto(ResponseStatus.Error, null, null, new ResponseException("کد ثبت شرکت/ارگان یا اسم را وارد نکرده اید "));


        }


    }


    @RequestMapping(value = "/ws/saveRealCustomer", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto<String> saveRealCustomer(@Valid @RequestBody RealCustomer realCustomer) {


        if (Objects.nonNull(realCustomer.getId()) && Objects.nonNull(realCustomerDao.findById(realCustomer.getId()))) {
            // realCustomer.setId(realCustomer.getId());
            realCustomerDao.save(realCustomer);
            logger.info("realCustomer updated :" + realCustomer.toString());
            return new ResponseDto<>(ResponseStatus.Ok, null, "با موفقیت ویرایش شد !", null);


        }
        if (Objects.nonNull(realCustomer.getNationalCode()) && Objects.nonNull(realCustomer.getName())) {

            if (Objects.isNull(realCustomerDao.findByNationalCode(realCustomer.getNationalCode()))) {
                realCustomerDao.save(realCustomer);
                logger.info("realCustomer added :" + realCustomer.toString());
                return new ResponseDto<>(ResponseStatus.Ok, null, "اطلاعات ذخیره شد.", null);
            } else {
                logger.error("realCustomer 's national id is  duplicated :");
                return new ResponseDto(ResponseStatus.Error, null, null, new ResponseException("قبلا ثبت نام کرده اید"));

            }

        } else {
            return new ResponseDto(ResponseStatus.Error, null, null, new ResponseException("کد ملی یا اسم را وارد نکرده اید "));


        }


    }


    @RequestMapping(value = "/ws/searchLegal", method = RequestMethod.POST)
    public ResponseDto<CustomerDto> searchLegal(@Valid @RequestParam String legalCode) {


        LegalCustomer byLegalCode = legalCustomerDao.findByLegalCode(legalCode);

        if (Objects.isNull(byLegalCode)) {
            logger.info("legalCustomer doesnt exist:  :" + legalCode);
            return new ResponseDto(ResponseStatus.Error, null, "", new ResponseException("پیدا نشد!"));


        } else
            return new ResponseDto(ResponseStatus.Ok, byLegalCode, "", null);
    }


    @RequestMapping(value = "/ws/searchReal", method = RequestMethod.POST)
    public ResponseDto<CustomerDto> searchReal(@RequestParam String nationalCode) {


        RealCustomer byNationalCode = realCustomerDao.findByNationalCode(nationalCode);

        if (Objects.isNull(byNationalCode)) {
            logger.info("legalCustomer doesnt exist:  :" + nationalCode);
            return new ResponseDto(ResponseStatus.Error, null, null, new ResponseException("پیدا نشد!"));

        } else
            return new ResponseDto(ResponseStatus.Ok, byNationalCode, null, null);
    }

    @RequestMapping(value = "/ws/advanceLegalSearch", method = RequestMethod.POST)
    public ResponseDto<List<LegalCustomer>> advanceLegalSearch(@RequestParam String name) {
        List<LegalCustomer> byName = legalCustomerDao.findByName(name.toUpperCase());

        if (byName.size()==0)
            return new ResponseDto(ResponseStatus.Error, null, null, new ResponseException("پیدا نشد!"));


        return new ResponseDto<>(ResponseStatus.Ok, byName, null, null);
    }

    @RequestMapping(value = "/ws/advanceRealSearch", method = RequestMethod.POST)
    public ResponseDto<List<RealCustomerDto>> advanceRealSearch(@RequestParam String name) {

        List<RealCustomer> byName = realCustomerDao.findByName(name.toUpperCase());

        if (byName.size()!=0)
            return new ResponseDto(ResponseStatus.Ok, byName, null, null);

        return new ResponseDto<>(ResponseStatus.Error, null, null, new ResponseException("پیدا نشد!"));
    }

    @RequestMapping(value = "/ws/updateLegal", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto<RealCustomerDto> updateLegal(@RequestParam String legalCode) {


        LegalCustomer byLegalCode = legalCustomerDao.findByLegalCode(legalCode);

        if (Objects.nonNull(byLegalCode)) {

            return new ResponseDto(ResponseStatus.Ok, byLegalCode, null, null);
        } else

            return new ResponseDto<>(ResponseStatus.Error, null, null, new ResponseException("موجود نیست !"));
    }

    @RequestMapping(value = "/ws/updateReal", method = RequestMethod.POST)
    @Transactional(rollbackOn = Exception.class)
    public ResponseDto<RealCustomerDto> updateReal(@RequestParam String nationalCode) {

        RealCustomer byReal = realCustomerDao.findByNationalCode(nationalCode);

        if (Objects.nonNull(byReal)) {

            return new ResponseDto(ResponseStatus.Ok, byReal, null, new ResponseException("موجود نیست !"));
        } else

            return new ResponseDto(ResponseStatus.Error, null, null, new ResponseException("موجود نیست !"));
    }

    @RequestMapping(value = "/ws/savingAccountForReal", method = RequestMethod.POST)

    public ResponseDto<RealCustomerDto> savingAccountForReal(@Valid @RequestBody RealCustomer realCustomer) {

        SavingAccount sa = new SavingAccount();
        sa.setAccountNumber(realCustomer.getId());
        realCustomer.getSavingAccounts().add(sa);
        savingAccountDao.save(sa);
        realCustomerDao.save(realCustomer);

        return new ResponseDto(ResponseStatus.Ok, null, "سپرده جدید برای شما ایجاد شد", null);


    }

    @RequestMapping(value = "/ws/savingAccountForLegal", method = RequestMethod.POST)

    public ResponseDto<RealCustomerDto> savingAccountForLegal(@Valid @RequestBody LegalCustomer legalCustomer) {

        SavingAccount sa = new SavingAccount();
        sa.setAccountNumber(legalCustomer.getId());
        legalCustomer.getSavingAccounts().add(sa);
        savingAccountDao.save(sa);
        legalCustomerDao.save(legalCustomer);

        return new ResponseDto(ResponseStatus.Ok, null, "سپرده جدید برای شما ایجاد شد :)", null);


    }
    @RequestMapping(value = "/ws/searchByAccountNumber", method = RequestMethod.POST)
    public ResponseDto<RealCustomerDto> searchByAccountNumber(@Valid @RequestParam Integer accountNumber) {
       SavingAccount findByNum= savingAccountDao.findByAccountNumber(accountNumber);

         if (Objects.nonNull(findByNum))

        return new ResponseDto(ResponseStatus.Ok, findByNum, null, null);

        return new ResponseDto(ResponseStatus.Error, null, null, new ResponseException("پیدا نشد"));

    }
    @RequestMapping(value = "/ws/deposit", method = RequestMethod.POST)
    public ResponseDto<RealCustomerDto> deposit(@Valid @RequestBody SavingAccount savingAccount) {

        savingAccount.deposit(savingAccount.getAmount());
        savingAccountDao.save(savingAccount);

        return new ResponseDto(ResponseStatus.Ok, null, "واریز انجام شد ", null);


    }
    @RequestMapping(value = "/ws/withdrawal", method = RequestMethod.POST)
    public ResponseDto<RealCustomerDto> withdrawal(@Valid @RequestBody SavingAccount savingAccount) {

       if( savingAccount.withdrawal(savingAccount.getAmount())){
           savingAccountDao.save(savingAccount);
           return new ResponseDto(ResponseStatus.Ok, null, "برداشت انجام شد", null);
       }


       else
           return new ResponseDto(ResponseStatus.Error, null,null , new ResponseException("موجودی کافی نیست "));


    }

    @RequestMapping(value = "/ws/transferMoney", method = RequestMethod.POST)
    public ResponseDto<RealCustomerDto> transferMoney( @RequestBody TransferMoneyDto transferMoneyDto) {
        SavingAccount byAccountNumber = savingAccountDao.findByAccountNumber(transferMoneyDto.getSourceAccount());
        SavingAccount byAccountNumber2 = savingAccountDao.findByAccountNumber(transferMoneyDto.getDestinationAccount());

        if(Objects.nonNull(byAccountNumber) && Objects.nonNull(byAccountNumber2)) {
         if (byAccountNumber.withdrawal(transferMoneyDto.getAmount())){
             byAccountNumber2.deposit(transferMoneyDto.getAmount());
             savingAccountDao.save(byAccountNumber);
             savingAccountDao.save(byAccountNumber2);
             return new ResponseDto(ResponseStatus.Ok, null, "انتقال وجه با موفقیت انجام شد ", null);
         }
            return new ResponseDto(ResponseStatus.Error, null, null, new ResponseException("موجودی کافی نیست "));

     }





            return new ResponseDto(ResponseStatus.Ok, null, "موجودی کافی نیست ", null);


    }
    String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(new ClassPathResource(path).getFile().getPath()));
        return new String(encoded, encoding);
    }
}
