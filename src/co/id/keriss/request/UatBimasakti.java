package co.id.keriss.request;

import java.util.Random;

import co.id.keriss.fastpay.beans.request.Game;
import co.id.keriss.fastpay.beans.request.Inquery;
import co.id.keriss.fastpay.beans.request.Payment;
import co.id.keriss.fastpay.beans.request.Pulsa;
import co.id.keriss.fastpay.beans.respone.GameRespone;
import co.id.keriss.fastpay.beans.respone.PLNPascaBayar;
import co.id.keriss.fastpay.beans.respone.PulsaRespone;
import co.id.keriss.fastpay.beans.respone.TVKabel;
import co.id.keriss.fastpay.beans.respone.TelPascaBayar;
import co.id.keriss.fastpay.beans.respone.TelkomGroup;

public class UatBimasakti {
	public UatBimasakti() {
		super();
		inquery = new Inquery();
		payment = new Payment();
	}
	private Inquery inquery;
	private Payment payment;
	private Object temp;
	public static void main(String[] args) {
		RequestBeans service = new RequestBeans();
		UatBimasakti uat = new UatBimasakti();
		/*uat.tc011telpon1(service);
		uat.tc011telpon2(service);
		uat.tc011telpon7(service);
		uat.tc012speedy1(service);
		uat.tc012speedy2(service);
		uat.tc012speedy7(service);
		uat.tc013tvision1(service);
		uat.tc013tvision2(service);
		uat.tc013tvision7(service);
		uat.tc021plnpasca1(service);
		uat.tc021plnpasca2(service);
		uat.tc021plnpasca7(service);
		uat.tc021plnnon1(service);
		uat.tc021plnnon2(service);
		uat.tc021plnnon3(service);
		uat.tc023plntoken1(service);
		uat.tc023plntoken2(service);*/
		/*uat.tc031tvkabel1(service);
		uat.tc031tvkabel2(service);
		uat.tc031tvkabel3(service);*/
		uat.tc041pulsa1(service);
		uat.tc041pulsa2(service);
		uat.tc041pulsa3(service);
		/*uat.tc051game1(service);
		uat.tc051game2(service);
		uat.tc051game3(service);
		uat.tc061selpasca1(service);
		uat.tc061selpasca2(service);
		uat.tc061selpasca7(service);*/
	}
	
	public void specialCase(UatBimasakti uat, RequestBeans service){
		uat.tc011telpon3(service);
		uat.tc011telpon4(service);
		uat.tc011telpon5(service);
		uat.tc011telpon6(service);
		uat.tc012speedy3(service);
		uat.tc012speedy4(service);
		uat.tc012speedy5(service);
		uat.tc012speedy6(service);
		uat.tc013tvision3(service);
		uat.tc013tvision4(service);
		uat.tc013tvision5(service);
		uat.tc013tvision6(service);
		uat.tc021plnpasca3(service);
		uat.tc021plnpasca4(service);
		uat.tc021plnpasca5(service);
		uat.tc021plnpasca6(service);
		uat.tc061selpasca3(service);
		uat.tc061selpasca4(service);
		uat.tc061selpasca5(service);
		uat.tc061selpasca6(service);
	}
	
	public void tc011telpon1(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("021");
		inquery.setIDPelanggan2("8629866");
		inquery.setKodeProduk("TELEPON");
		TelPascaBayar tpb = service.getTelPascaBayarRespone(inquery);
		System.out.println("tc011telpon1");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ "0218629866");
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}
	}
	public void tc011telpon2(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("021");
		inquery.setIDPelanggan2("8629866");
		inquery.setKodeProduk("TELEPON");
		TelPascaBayar tpb = service.getTelPascaBayarRespone(inquery);
		if(tpb.getStatus().equalsIgnoreCase("00")){
			payment = new Payment();
			payment.setIDPelanggan1(tpb.getIdpelanggan1());
			payment.setKodeProduk(tpb.getKodeProduk());
			payment.setNominal(tpb.getNominal());
			payment.setREF2(tpb.getRef2());
			System.out.println("tc011telpon2");
			System.out.println("Status : " + tpb.getStatus());
			System.out.println("Keterangan : " + tpb.getKeterangan());
			System.out.println("Account : "+ "0218629866");
		}
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}
	}
	public void tc011telpon3(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("021");
		inquery.setKodeProduk("TELEPON");
		TelPascaBayar tpb;
		while(true){
			int ran = this.createRandomInteger(8629866, 9299999, new Random());
			inquery.setIDPelanggan2(""+ran);
			tpb = service.getTelPascaBayarRespone(inquery);
			if(tpb.getJumlahbill().trim().equalsIgnoreCase("2")&&tpb.getStatus().equalsIgnoreCase("00"))break;
		}
		temp = tpb;
		System.out.println("tc011telpon3");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1()+tpb.getIdpelanggan2());
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc011telpon4(RequestBeans service){
		try{
		TelPascaBayar tpb = (TelPascaBayar)temp;
		payment = new Payment();
		payment.setIDPelanggan1(tpb.getIdpelanggan1());
		payment.setKodeProduk(tpb.getKodeProduk());
		payment.setNominal(tpb.getNominal());
		payment.setREF2(tpb.getRef2());
		tpb = service.getTelPascaBayarRespone(payment);
		System.out.println("tc011telpon4");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1()+tpb.getIdpelanggan2());
		
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc011telpon5(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("021");
		inquery.setKodeProduk("TELEPON");
		TelPascaBayar tpb;
		while(true){
			int ran = this.createRandomInteger(8629866, 9299999, new Random());
			inquery.setIDPelanggan2(""+ran);
			tpb = service.getTelPascaBayarRespone(inquery);
			if(tpb.getJumlahbill().trim().equalsIgnoreCase("3")&&tpb.getStatus().equalsIgnoreCase("00"))break;
		}
		temp = tpb;
		System.out.println("tc011telpon5");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1()+tpb.getIdpelanggan2());
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc011telpon6(RequestBeans service){
		try{
		TelPascaBayar tpb = (TelPascaBayar)temp;
		payment = new Payment();
		payment.setIDPelanggan1(tpb.getIdpelanggan1());
		payment.setKodeProduk(tpb.getKodeProduk());
		payment.setNominal(tpb.getNilaitagihan1());
		payment.setREF2(tpb.getRef2());
		tpb = service.getTelPascaBayarRespone(payment);
		System.out.println("tc011telpon6");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1()+tpb.getIdpelanggan2());
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc011telpon7(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("021");
		inquery.setIDPelanggan2("123456789");
		inquery.setKodeProduk("TELEPON");
		TelPascaBayar tpb = service.getTelPascaBayarRespone(inquery);
		System.out.println("tc011telpon7");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1()+tpb.getIdpelanggan2());
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	
	public void tc012speedy1(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("152426200082");
		inquery.setKodeProduk("SPEEDY");
		TelkomGroup tpb = service.getTelkomGroupRespone(inquery);
		System.out.println("tc012speedy1");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ "152426200082");
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc012speedy2(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("152426200082");
		inquery.setKodeProduk("SPEEDY");
		TelkomGroup tpb = service.getTelkomGroupRespone(inquery);
		if(tpb.getStatus().equalsIgnoreCase("00")){
			payment = new Payment();
			payment.setIDPelanggan1(tpb.getIdpelanggan1());
			payment.setKodeProduk(inquery.getKodeProduk());
			payment.setNominal(tpb.getNominal());
			payment.setREF2(tpb.getRef2());
			System.out.println("tc012speedy2");
			System.out.println("Status : " + tpb.getStatus());
			System.out.println("Keterangan : " + tpb.getKeterangan());
			System.out.println("Account : "+ "152426200082");
		}
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc012speedy3(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setKodeProduk("SPEEDY");
		TelkomGroup tpb;
		while(true){
			Random random = new Random();
			int rand = this.createRandomInteger(11442, 15242, random);
			int ran = this.createRandomInteger(8629866, 9299999, random);
			inquery.setIDPelanggan1(rand+""+ran);
			tpb = service.getTelkomGroupRespone(inquery);
			if(tpb.getJumlahbill().trim().equalsIgnoreCase("2")&&tpb.getStatus().equalsIgnoreCase("00"))break;
		}
		temp = tpb;
		System.out.println("tc012speedy3");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1());
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc012speedy4(RequestBeans service){
		try{
		TelkomGroup tpb = (TelkomGroup)temp;
		payment = new Payment();
		payment.setIDPelanggan1(tpb.getIdpelanggan1());
		payment.setKodeProduk("SPEEDY");
		payment.setNominal(tpb.getNominal());
		payment.setREF2(tpb.getRef2());
		tpb = service.getTelkomGroupRespone(payment);
		System.out.println("tc011telpon4");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1());
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc012speedy5(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setKodeProduk("SPEEDY");
		TelkomGroup tpb;
		while(true){
			Random random = new Random();
			int rand = this.createRandomInteger(11442, 15242, random);
			int ran = this.createRandomInteger(8629866, 9299999, random);
			inquery.setIDPelanggan1(rand+""+ran);
			tpb = service.getTelkomGroupRespone(inquery);
			if(tpb.getJumlahbill().trim().equalsIgnoreCase("3")&&tpb.getStatus().equalsIgnoreCase("00"))break;
		}
		temp = tpb;
		System.out.println("tc012speedy5");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1());

		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc012speedy6(RequestBeans service){
		try{
		TelkomGroup tpb = (TelkomGroup)temp;
		payment = new Payment();
		payment.setIDPelanggan1(tpb.getIdpelanggan1());
		payment.setKodeProduk("SPEEDY");
		Long nom = Long.parseLong(tpb.getNominal());
		Long nominal = (1/3)*nom;
		payment.setNominal(""+nominal);
		payment.setREF2(tpb.getRef2());
		tpb = service.getTelkomGroupRespone(payment);
		System.out.println("tc012speedy6");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1());
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc012speedy7(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("123456789101");
		inquery.setKodeProduk("SPEEDY");
		TelkomGroup tpb = service.getTelkomGroupRespone(inquery);
		System.out.println("tc012speedy7");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ "123456789101");
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	
	public void tc013tvision1(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("127202174731");
		inquery.setKodeProduk("TVTLKMV");
		TelkomGroup tpb = service.getTelkomGroupRespone(inquery);
		System.out.println("tc013tvision1");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ "127202174731");		
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc013tvision2(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("127202174731");
		inquery.setKodeProduk("TVTLKMV");
		TelkomGroup tpb = service.getTelkomGroupRespone(inquery);
		//if(tpb.getStatus().equalsIgnoreCase("00")){
			payment = new Payment();
			payment.setIDPelanggan1(tpb.getIdpelanggan1());
			payment.setKodeProduk(inquery.getKodeProduk());
			payment.setNominal(tpb.getNominal());
			payment.setREF2(tpb.getRef2());
			System.out.println("tc013tvision2");
			System.out.println("Status : " + tpb.getStatus());
			System.out.println("Keterangan : " + tpb.getKeterangan());
			System.out.println("Account : "+ "127202174731");
		//}
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc013tvision3(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setKodeProduk("TVTLKMV");
		TelkomGroup tpb;
		while(true){
			Random random = new Random();
			int rand = this.createRandomInteger(12720, 15242, random);
			int ran = this.createRandomInteger(8629866, 9299999, random);
			inquery.setIDPelanggan1(rand+""+ran);
			tpb = service.getTelkomGroupRespone(inquery);
			if(tpb.getJumlahbill().trim().equalsIgnoreCase("2")&&tpb.getStatus().equalsIgnoreCase("00"))break;
		}
		temp = tpb;
		System.out.println("tc013tvision3");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1());
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc013tvision4(RequestBeans service){
		try{
		TelkomGroup tpb = (TelkomGroup)temp;
		payment = new Payment();
		payment.setIDPelanggan1(tpb.getIdpelanggan1());
		payment.setKodeProduk("TVTLKMV");
		payment.setNominal(tpb.getNominal());
		payment.setREF2(tpb.getRef2());
		tpb = service.getTelkomGroupRespone(payment);
		System.out.println("tc013tvision6");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1());
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc013tvision5(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setKodeProduk("TVTLKMV");
		TelkomGroup tpb;
		while(true){
			Random random = new Random();
			int rand = this.createRandomInteger(12720, 15242, random);
			int ran = this.createRandomInteger(8629866, 9299999, random);
			inquery.setIDPelanggan1(rand+""+ran);
			tpb = service.getTelkomGroupRespone(inquery);
			if(tpb.getJumlahbill().trim().equalsIgnoreCase("3")&&tpb.getStatus().equalsIgnoreCase("00"))break;
		}
		temp = tpb;
		System.out.println("tc013tvision3");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1()+tpb.getIdpelanggan2());
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc013tvision6(RequestBeans service){
		try{
		TelkomGroup tpb = (TelkomGroup)temp;
		payment = new Payment();
		payment.setIDPelanggan1(tpb.getIdpelanggan1());
		payment.setKodeProduk("TVTLKMV");
		Long nom = Long.parseLong(tpb.getNominal());
		Long nominal = (1/3)*nom;
		payment.setNominal(""+nominal);
		payment.setREF2(tpb.getRef2());
		tpb = service.getTelkomGroupRespone(payment);
		System.out.println("tc013tvision6");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1());
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc013tvision7(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("1234567891013");
		inquery.setKodeProduk("TVTLKMV");
		TelkomGroup tpb = service.getTelkomGroupRespone(inquery);
		System.out.println("tc013tvision7");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ "1234567891013");
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc021plnpasca1(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("543600664033");
		inquery.setKodeProduk("PLNPASC");
		PLNPascaBayar tpb = service.getPLNPascaBayarRespone(inquery);
		System.out.println("tc021plnpasca1");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ "543600664033");		
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc021plnpasca2(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("543600664033");
		inquery.setKodeProduk("PLNPASC");
		PLNPascaBayar tpb = service.getPLNPascaBayarRespone(inquery);
		//if(tpb.getStatus().equalsIgnoreCase("00")){
			payment = new Payment();
			payment.setIDPelanggan1(tpb.getIdpelanggan1());
			payment.setKodeProduk(inquery.getKodeProduk());
			payment.setNominal(tpb.getNominal());
			payment.setREF2(tpb.getRef2());
			System.out.println("tc021plnpasca2");
			System.out.println("Status : " + tpb.getStatus());
			System.out.println("Keterangan : " + tpb.getKeterangan());
			System.out.println("Account : "+ "543600664033");
		//}
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc021plnpasca3(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setKodeProduk("PLNPASC");
		PLNPascaBayar tpb;
		while(true){
			Random random = new Random();
			int rand = this.createRandomInteger(01117, 15242, random);
			int ran = this.createRandomInteger(8629866, 9299999, random);
			inquery.setIDPelanggan1(rand+""+ran);
			tpb = service.getPLNPascaBayarRespone(inquery);
			if(tpb.getBlth2().trim()!=null&&tpb.getStatus().equalsIgnoreCase("00"))break;
		}
		temp = tpb;
		System.out.println("tc021plnpasca3");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1());
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc021plnpasca4(RequestBeans service){
		try{
		PLNPascaBayar tpb = (PLNPascaBayar)temp;
		payment = new Payment();
		payment.setIDPelanggan1(tpb.getIdpelanggan1());
		payment.setKodeProduk("PLNPASC");
		payment.setNominal(tpb.getNominal());
		payment.setREF2(tpb.getRef2());
		tpb = service.getRequestBean(payment);
		System.out.println("tc021plnpasca6");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1());
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc021plnpasca5(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setKodeProduk("PLNPASC");
		PLNPascaBayar tpb;
		while(true){
			Random random = new Random();
			int rand = this.createRandomInteger(12720, 15242, random);
			int ran = this.createRandomInteger(8629866, 9299999, random);
			inquery.setIDPelanggan1(rand+""+ran);
			tpb = service.getPLNPascaBayarRespone(inquery);
			if(tpb.getBlth3().trim()!=null&&tpb.getStatus().equalsIgnoreCase("00"))break;
		}
		temp = tpb;
		System.out.println("tc021plnpasca3");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1());
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc021plnpasca6(RequestBeans service){
		try{
		PLNPascaBayar tpb = (PLNPascaBayar)temp;
		payment = new Payment();
		payment.setIDPelanggan1(tpb.getIdpelanggan1());
		payment.setKodeProduk("PLNPASC");
		Long nom = Long.parseLong(tpb.getNominal());
		Long nominal = (1/3)*nom;
		payment.setNominal(""+nominal);
		payment.setREF2(tpb.getRef2());
		tpb = service.getRequestBean(payment);
		System.out.println("tc021plnpasca6");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1());
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc021plnpasca7(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("123456789101");
		inquery.setKodeProduk("PLNPASC");
		PLNPascaBayar tpb = service.getPLNPascaBayarRespone(inquery);
		System.out.println("tc021plnpasca7");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ "123456789101");
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}	
	public void tc021plnnon1(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("5392112011703");
		inquery.setKodeProduk("PLNNON");
		PLNPascaBayar tpb = service.getPLNPascaBayarRespone(inquery);
		System.out.println("tc021plnnon1");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ "5392112011703");
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc021plnnon2(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("5392112011703");
		inquery.setKodeProduk("PLNNON");
		PLNPascaBayar tpb = service.getPLNPascaBayarRespone(inquery);
		//if(tpb.getStatus().equalsIgnoreCase("00")){
			payment = new Payment();
			payment.setIDPelanggan1(tpb.getIdpelanggan1());
			payment.setKodeProduk(inquery.getKodeProduk());
			payment.setNominal(tpb.getNominal());
			payment.setREF2(tpb.getRef2());
			System.out.println("tc021plnnon2");
			System.out.println("Status : " + tpb.getStatus());
			System.out.println("Keterangan : " + tpb.getKeterangan());
			System.out.println("Account : "+ "5392112011703");
		//}

		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc021plnnon3(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("123456789101");
		inquery.setKodeProduk("PLNNON");
		PLNPascaBayar tpb = service.getPLNPascaBayarRespone(inquery);
		System.out.println("tc021plnnon3");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ "123456789101");
		
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc023plntoken1(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("022101029167");
		inquery.setIDPelanggan3("081807366329");
		inquery.setKodeProduk("PLNPRA");
		PLNPascaBayar tpb = service.getPLNPascaBayarRespone(inquery);
		//if(tpb.getStatus().equalsIgnoreCase("00")){
			payment = new Payment();
			payment.setIDPelanggan1(tpb.getIdpelanggan1());
			payment.setKodeProduk(inquery.getKodeProduk());
			payment.setNominal(tpb.getNominal());
			payment.setREF2(tpb.getRef2());
			System.out.println("tc023plntoken1");
			System.out.println("Status : " + tpb.getStatus());
			System.out.println("Keterangan : " + tpb.getKeterangan());
			System.out.println("Account : "+ "022101029167");
		//}
		
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc023plntoken2(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("123456789101");
		inquery.setIDPelanggan3("081807366329");
		inquery.setKodeProduk("PLNPRA");
		PLNPascaBayar tpb = service.getPLNPascaBayarRespone(inquery);
		//if(tpb.getStatus().equalsIgnoreCase("00")){
			payment = new Payment();
			payment.setIDPelanggan1(tpb.getIdpelanggan1());
			payment.setKodeProduk(inquery.getKodeProduk());
			payment.setNominal(tpb.getNominal());
			payment.setREF2(tpb.getRef2());
			System.out.println("tc023plntoken2");
			System.out.println("Status : " + tpb.getStatus());
			System.out.println("Keterangan : " + tpb.getKeterangan());
			System.out.println("Account : "+ "123456789101");
		//}
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}

	public void tc031tvkabel1(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("7000162882");
		inquery.setKodeProduk("TVAORA");
		TVKabel tpb = service.getTVkabelRespone(inquery);
		System.out.println("tc031tvkabel1");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ "7000162882");
		
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc031tvkabel2(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("7000162882");
		inquery.setKodeProduk("TVAORA");
		TVKabel tpb = service.getTVkabelRespone(inquery);
		//if(tpb.getStatus().equalsIgnoreCase("00")){
			payment = new Payment();
			payment.setIDPelanggan1(tpb.getIdpelanggan1());
			payment.setKodeProduk(inquery.getKodeProduk());
			payment.setNominal(tpb.getNominal());
			payment.setREF2(tpb.getRef2());
			System.out.println("tc031tvkabel2");
			System.out.println("Status : " + tpb.getStatus());
			System.out.println("Keterangan : " + tpb.getKeterangan());
			System.out.println("Account : "+ "7000162882");
		//}

		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc031tvkabel3(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("1234567891234");
		inquery.setKodeProduk("TVAORA");
		TVKabel tpb = service.getTVkabelRespone(inquery);
		System.out.println("tc031tvkabel3");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ "1234567891234");
		
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}

	public void tc041pulsa1(RequestBeans service){
		try{
		Pulsa pulsa = new Pulsa();
		pulsa.setUid("HH57010");
		pulsa.setKodeproduk("XR10");
		pulsa.setNohp("081807366329");
		pulsa.setPin("130641");
		PulsaRespone pulsaRes = service.getPulsaRespone(pulsa);
		System.out.println("tc041pulsa1");
		System.out.println("Status : " + pulsaRes.getStatus());
		System.out.println("Keterangan : " + pulsaRes.getKeterangan());
		System.out.println("Account : "+ "081807366329");

		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc041pulsa2(RequestBeans service){
		try{
		Pulsa pulsa = new Pulsa();
		pulsa.setUid("HH57010");
		pulsa.setKodeproduk("XR10");
		pulsa.setNohp("81387998643");
		pulsa.setPin("130641");
		PulsaRespone pulsaRes = service.getPulsaRespone(pulsa);
		System.out.println("tc041pulsa2");
		System.out.println("Status : " + pulsaRes.getStatus());
		System.out.println("Keterangan : " + pulsaRes.getKeterangan());
		System.out.println("Account : "+ "081387998643");
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc041pulsa3(RequestBeans service){
		try{
		Pulsa pulsa = new Pulsa();
		pulsa.setUid("HH57010");
		pulsa.setKodeproduk("XR10");
		pulsa.setNohp("081807366329");
		pulsa.setPin("130641");
		PulsaRespone pulsaRes = service.getPulsaRespone(pulsa);
		System.out.println("tc041pulsa3");
		System.out.println("Status : " + pulsaRes.getStatus());
		System.out.println("Keterangan : " + pulsaRes.getKeterangan());
		System.out.println("Account : "+ "081387998643");
		
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}

	public void tc051game1(RequestBeans service){
		try{
		Game game = new Game();
		game.setUID("HH57010");
		game.setKodeProduk("GS20");
		game.setNoHP("081295910296");
		game.setPIN("130641");
		GameRespone pulsaRes = service.getGameRespone(game);
		System.out.println("tc051game1");
		System.out.println("Status : " + pulsaRes.getStatus());
		System.out.println("Keterangan : " + pulsaRes.getKeterangan());
		System.out.println("Account : "+ "081295910296");
		
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc051game2(RequestBeans service){
		try{
		Game game = new Game();
		game.setUID("HH57010");
		game.setKodeProduk("GS20");
		game.setNoHP("4859");
		game.setPIN("130641");
		GameRespone pulsaRes = service.getGameRespone(game);
		System.out.println("tc051game2");
		System.out.println("Status : " + pulsaRes.getStatus());
		System.out.println("Keterangan : " + pulsaRes.getKeterangan());
		System.out.println("Account : "+ "4859");
		
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc051game3(RequestBeans service){
		try{
		Game game = new Game();
		game.setUID("HH57010");
		game.setKodeProduk("GS20");
		game.setNoHP("081295910296");
		game.setPIN("130641");
		GameRespone pulsaRes = service.getGameRespone(game);
		System.out.println("tc051game3");
		System.out.println("Status : " + pulsaRes.getStatus());
		System.out.println("Keterangan : " + pulsaRes.getKeterangan());
		System.out.println("Account : "+ "081295910296");
		
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc061selpasca1(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("0811100727");
		inquery.setKodeProduk("HPTSEL");
		TelPascaBayar tpb = service.getTelPascaBayarRespone(inquery);
		System.out.println("tc061selpasca1");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ "0811100727");		
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc061selpasca2(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("0811100727");
		inquery.setKodeProduk("HPTSEL");
		TelPascaBayar tpb = service.getTelPascaBayarRespone(inquery);
		//if(tpb.getStatus().equalsIgnoreCase("00")){
			payment = new Payment();
			payment.setIDPelanggan1(tpb.getIdpelanggan1());
			payment.setKodeProduk(inquery.getKodeProduk());
			payment.setNominal(tpb.getNominal());
			payment.setREF2(tpb.getRef2());
			System.out.println("tc061selpasca2");
			System.out.println("Status : " + tpb.getStatus());
			System.out.println("Keterangan : " + tpb.getKeterangan());
			System.out.println("Account : "+ "0811100727");
		//}
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc061selpasca3(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setKodeProduk("HPTSEL");
		TelPascaBayar tpb;
		while(true){
			Random random = new Random();
			int rand = this.createRandomInteger(01117, 15242, random);
			int ran = this.createRandomInteger(8629866, 9299999, random);
			inquery.setIDPelanggan1(rand+""+ran);
			tpb = service.getTelPascaBayarRespone(inquery);
			if(tpb.getJumlahbill().trim().equalsIgnoreCase("2")&&tpb.getStatus().equalsIgnoreCase("00"))break;
		}
		temp = tpb;
		System.out.println("tc061selpasca3");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1());
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc061selpasca4(RequestBeans service){
		try{
		TelPascaBayar tpb = (TelPascaBayar)temp;
		payment = new Payment();
		payment.setIDPelanggan1(tpb.getIdpelanggan1());
		payment.setKodeProduk("HPTSEL");
		payment.setNominal(tpb.getNominal());
		payment.setREF2(tpb.getRef2());
		tpb = service.getTelPascaBayarRespone(payment);
		System.out.println("tc061selpasca4");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1());
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc061selpasca5(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setKodeProduk("HPTSEL");
		TelPascaBayar tpb;
		while(true){
			Random random = new Random();
			int rand = this.createRandomInteger(12720, 15242, random);
			int ran = this.createRandomInteger(8629866, 9299999, random);
			inquery.setIDPelanggan1(rand+""+ran);
			tpb = service.getTelPascaBayarRespone(inquery);
			if(tpb.getJumlahbill().trim().equalsIgnoreCase("3")&&tpb.getStatus().equalsIgnoreCase("00"))break;
		}
		temp = tpb;
		System.out.println("tc061selpasca4");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1());
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc061selpasca6(RequestBeans service){
		try{
		TelPascaBayar tpb = (TelPascaBayar)temp;
		payment = new Payment();
		payment.setIDPelanggan1(tpb.getIdpelanggan1());
		payment.setKodeProduk("HPTSEL");
		Long nom = Long.parseLong(tpb.getNominal());
		Long nominal = (1/3)*nom;
		payment.setNominal(""+nominal);
		payment.setREF2(tpb.getRef2());
		tpb = service.getTelPascaBayarRespone(payment);
		System.out.println("tc061selpasca6");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ tpb.getIdpelanggan1());
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}
	public void tc061selpasca7(RequestBeans service){
		try{
		inquery.setUID("HH57010");
		inquery.setPIN("130641");
		inquery.setIDPelanggan1("123456789101");
		inquery.setKodeProduk("HPTSEL");
		TelPascaBayar tpb = service.getTelPascaBayarRespone(inquery);
		System.out.println("tc061selpasca7");
		System.out.println("Status : " + tpb.getStatus());
		System.out.println("Keterangan : " + tpb.getKeterangan());
		System.out.println("Account : "+ "123456789101");
		System.out.println("-------------");
		}catch(Exception e){
			System.out.println("Exception "+e.toString());		
		}

	}		
		
	private Integer createRandomInteger(int aStart, long aEnd, Random aRandom){
	    if ( aStart > aEnd ) {
	      throw new IllegalArgumentException("Start cannot exceed End.");
	    }
	    //get the range, casting to long to avoid overflow problems
	    long range = aEnd - (long)aStart + 1;
	    // compute a fraction of the range, 0 <= frac < range
	    long fraction = (long)(range * aRandom.nextDouble());
	    Long randomNumber =  fraction + (long)aStart;    
	    return randomNumber.intValue();
	}

}
