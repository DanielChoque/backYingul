package com.valework.yingul.controller;

import java.util.List;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.valework.yingul.SmtpMailSender;
import com.valework.yingul.dao.BuyDao;
import com.valework.yingul.dao.CardDao;
import com.valework.yingul.dao.ItemDao;
import com.valework.yingul.dao.ListCreditCardDao;
import com.valework.yingul.dao.PaymentMethodDao;
import com.valework.yingul.dao.UserDao;
import com.valework.yingul.model.Yng_Buy;
import com.valework.yingul.model.Yng_Card;
import com.valework.yingul.model.Yng_CardProvider;
import com.valework.yingul.model.Yng_Item;
import com.valework.yingul.model.Yng_ListCreditCard;
import com.valework.yingul.model.Yng_PaymentMethod;
import com.valework.yingul.model.Yng_User;
import com.valework.yingul.service.CardService;
import com.valework.yingul.service.CreditCardProviderService;


@RestController
@RequestMapping("/buy")
public class BuyController {
	@Autowired
	private SmtpMailSender smtpMailSender;
	@Autowired
    private ListCreditCardDao listCreditCardDao;
	@Autowired
	private CreditCardProviderService creditCardProviderService;
	@Autowired
	UserDao userDao;
	@Autowired
	ItemDao itemDao;
	@Autowired
	CardDao cardDao;
	@Autowired
	PaymentMethodDao paymentMethodDao;
	@Autowired
	BuyDao buyDao;
	@Autowired 
	CardService cardService;
	@RequestMapping("/listCreditCard/all")
    public List<Yng_ListCreditCard> findProvinceList() {
        List<Yng_ListCreditCard> creditCardList = listCreditCardDao.findAll();
        return creditCardList;
    }

    @RequestMapping("/getCreditCardProvider/{listCreditCardId}")
    public List<Yng_CardProvider> findProviderByCreditCard(@PathVariable("listCreditCardId") Long listCreditCardId) {
    	Yng_ListCreditCard yng_ListCreditCard = listCreditCardDao.findByListCreditCardId(listCreditCardId);
        return creditCardProviderService.findByListCreditCard(yng_ListCreditCard);
    }
    @RequestMapping("/getCardForUser/{username}")
    public List<Yng_Card> findCardForUser(@PathVariable("username") String username) {
    	Yng_User yng_User = userDao.findByUsername(username);
        List<Yng_Card> cardList = cardService.findByUser(yng_User);
        return cardList;
    }
    
    @RequestMapping("/getSwForUser/{username}")
    public boolean getSwForUser(@PathVariable("username") String username) {
    	Yng_User yng_User = userDao.findByUsername(username);
        System.out.println(yng_User.getPhone());
        if(yng_User.getPhone()==null) {
        	return false;
        }
        else {
        	return true;
        }
    }
    
    @RequestMapping(value = "/createBuy", method = RequestMethod.POST)
    @ResponseBody
    public String createBuy(@Valid @RequestBody Yng_Buy buy) throws MessagingException {	
    	//para setear el item
    	Yng_Item itemTemp=itemDao.findByItemId(buy.getYng_item().getItemId());
    	buy.setYng_item(itemTemp);
    	//fin setear el item
   
    	//para setear el usuario
    	Yng_User userTemp= userDao.findByUsername(buy.getUser().getUsername());
    	//userTemp.setPhone(itemTemp.getUser().getPhone());
    	//userTemp.setPhone2(itemTemp.getUser().getPhone2());
    	//userTemp.setWebSite(itemTemp.getUser().getWebSite());
    	//userDao.save(userTemp);
    	buy.setUser(userTemp);
    	//hasta aqui para el usuario
    	
    	//setear metodo de pago y realizar el cobro 
    		//primero verificamos la tarjeta y la guardamos
    			//verificar la tarjeta
    				
    				//y realizar el cobro a la tarjeta
    			//
    			//guardar la tarjeta
    				Yng_Card cardTemp=buy.getYng_PaymentMethod().getYng_Card();
    				cardTemp.setFullName(cardTemp.getFullName().trim().toUpperCase());
    				cardTemp.setUser(userTemp);
    				cardTemp.setDueYear(cardTemp.getDueYear()-2000);
    				if(cardTemp.getType().toString().equals("DEBIT"))
    				{
    					cardTemp.setYng_CardProvider(null);
    				}
    				else {
    					//aqui asignar el banco que proporciono ala tarjeta de credito
    				}
    				Yng_PaymentMethod paymentMethodTemp=buy.getYng_PaymentMethod();
    				//para ver si la tarjeta existe 
    				if (null == cardDao.findByNumber(cardTemp.getNumber())) {
    					paymentMethodTemp.setYng_Card(cardDao.save(cardTemp)); 
    		        }
    				else {
    					cardTemp=cardDao.findByNumber(cardTemp.getNumber());
    					paymentMethodTemp.setYng_Card(cardTemp);
    				}
    			//
    		//
    		 buy.setYng_PaymentMethod(paymentMethodDao.save(paymentMethodTemp));
    	//fin del metodo de pago
    	buyDao.save(buy);
    	//modificar los correos para pagos no con tarjeta
    	System.out.print(buy.getYng_PaymentMethod().getYng_Card().getNumber()%10000);
    	try {
			smtpMailSender.send(userTemp.getEmail(), "Compra exitosa", "Adquirio: "+buy.getQuantity()+" "+buy.getYng_item().getName()+" a:"+buy.getCost()+" pago realizado con: "+buy.getYng_PaymentMethod().getType()+" "+buy.getYng_PaymentMethod().getYng_Card().getProvider()+" terminada en: "+buy.getYng_PaymentMethod().getYng_Card().getNumber()%10000+" nos pondremos en contacto con usted lo mas pronto posible.");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "save";
    }
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public String updateUser(@Valid @RequestBody Yng_User yng_user) throws MessagingException {	
    	Yng_User userTemp= userDao.findByUsername(yng_user.getUsername());
    	userTemp.setPhone(yng_user.getPhone());
    	userDao.save(userTemp);
    	return "save";
    }
    

}
