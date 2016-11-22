package co.id.keriss.request;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import co.id.keriss.fastpay.beans.request.CetakSaldo;
import co.id.keriss.fastpay.beans.request.CetakUlang;
import co.id.keriss.fastpay.beans.request.DataTransaksi;
import co.id.keriss.fastpay.beans.request.Game;
import co.id.keriss.fastpay.beans.request.GantiPin;
import co.id.keriss.fastpay.beans.request.Inquery;
import co.id.keriss.fastpay.beans.request.Payment;
import co.id.keriss.fastpay.beans.request.Pulsa;
import co.id.keriss.fastpay.beans.respone.AsuransiRespone;
import co.id.keriss.fastpay.beans.respone.CekSaldoRes;
import co.id.keriss.fastpay.beans.respone.DataTransaksiRes;
import co.id.keriss.fastpay.beans.respone.GameRespone;
import co.id.keriss.fastpay.beans.respone.GantiPinRes;
import co.id.keriss.fastpay.beans.respone.KartuKredit;
import co.id.keriss.fastpay.beans.respone.MultiFinance;
import co.id.keriss.fastpay.beans.respone.PDAMRespone;
import co.id.keriss.fastpay.beans.respone.PLNPascaBayar;
import co.id.keriss.fastpay.beans.respone.PLNPraBayar;
import co.id.keriss.fastpay.beans.respone.PLNnonTagList;
import co.id.keriss.fastpay.beans.respone.PulsaRespone;
import co.id.keriss.fastpay.beans.respone.TVKabel;
import co.id.keriss.fastpay.beans.respone.TelPascaBayar;
import co.id.keriss.fastpay.beans.respone.TelkomGroup;
/*
 * Function : getRespone from request
 * author 	: Tirta Adi Gunawan
 * email	: tirtavium@gmail.com
 */

public class RequestBeans {
	/*
	 * common method to getRespone
	 */
	private String getRespone(String xml) {
		String result = null;
		try {
			//URL url = new URL("http://10.0.151.1:8353/partner-vpn/datindo/");
			//URL url = new URL("http://61.8.79.178:8353/partner-vpn/datindo/");
			URL url = new URL("http://61.8.79.178:8353/partner-vpn/datindo/");
			//URL url = new URL("http://10.0.151.1:8353/partner-vpn/devel/");
			//URL url = new URL("http://localhost:8080/xml-rpc/devel/");
			//URL url = new URL("http://61.8.79.178:8353/partner-vpn/devel/");
			URLConnection con = url.openConnection();

			// specify that we will send output and accept input
			con.setDoInput(true);
			con.setDoOutput(true);

			con.setConnectTimeout(20000); // long timeout, but not infinite
			con.setReadTimeout(20000);

			con.setUseCaches(false);
			con.setDefaultUseCaches(false);

			// tell the web server what we are sending
			con.setRequestProperty("Content-Type", "text/xml");

			OutputStreamWriter writer = new OutputStreamWriter(
					con.getOutputStream());
			writer.write(xml);
			writer.flush();
			writer.close();

			// reading the response
			InputStreamReader reader = new InputStreamReader(
					con.getInputStream());

			StringBuilder buf = new StringBuilder();
			char[] cbuf = new char[2048];
			int num;

			while (-1 != (num = reader.read(cbuf))) {
				buf.append(cbuf, 0, num);
			}

			result = buf.toString();
			// System.err.println( "\nResponse from server after POST:\n" +
			// result );
		} catch (Throwable t) {
			t.printStackTrace(System.out);
		}

		return result;
	}

	/*
	 * pulsa respone
	 */
	public PulsaRespone getPulsaRespone(Pulsa pulsa) {
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.pulsa</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ pulsa.getKodeproduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ pulsa.getNohp()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ pulsa.getUid()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ pulsa.getPin()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ pulsa.getRef1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		System.out.println("Request : \n"+xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println("Response : \n"+result);

		PulsaRespone pr = new PulsaRespone();
		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}

		pr.setKodeProduksi(tagValues[0]);
		pr.setWaktu(tagValues[1]);
		pr.setNohp(tagValues[2]);
		pr.setUID(tagValues[3]);
		pr.setPin(tagValues[4]);
		pr.setSn(tagValues[5]);
		pr.setRef1(tagValues[6]);
		pr.setRef2(tagValues[7]);
		pr.setStatus(tagValues[8]);
		pr.setKeterangan(tagValues[9]);

		return pr;

	}
	/*
	 * game respone
	 */
	public GameRespone getGameRespone(Game game) {
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.game</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ game.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ game.getNoHP()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ game.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ game.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ game.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

		GameRespone gr = new GameRespone();

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}

		gr.setKodeProduksi(tagValues[0]);
		gr.setWaktu(tagValues[1]);
		gr.setNohp(tagValues[2]);
		gr.setUID(tagValues[3]);
		gr.setPin(tagValues[4]);
		gr.setSn(tagValues[5]);
		gr.setRef1(tagValues[6]);
		gr.setRef2(tagValues[7]);
		gr.setStatus(tagValues[8]);
		gr.setKeterangan(tagValues[9]);

		return gr;

	}

	/*
	 * inquery paramether respone
	 */
	public PLNPascaBayar getPLNPascaBayarRespone(Inquery inquery){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.inq</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ inquery.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}

		PLNPascaBayar pb = new PLNPascaBayar();
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan2(tagValues[3]);
		pb.setIdpelanggan3(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setBiayaadmin(tagValues[6]);
		pb.setUid(tagValues[7]);
		pb.setPin(tagValues[8]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setSwitcherId(tagValues[14]);
		pb.setSubscriberId(tagValues[15]);
		pb.setBillstatus(tagValues[16]);
		pb.setPaymentstatus(tagValues[17]);
		pb.setTotaloutstandingbell(tagValues[18]);
		pb.setSwereferencenumber(tagValues[19]);
		pb.setSubscribename(tagValues[20]);
		pb.setServiceunit(tagValues[21]);
		pb.setServiceunitphone(tagValues[22]);
		pb.setSubscribeSegmentation(tagValues[23]);
		pb.setPowerconsumingcategory(tagValues[24]);
		pb.setTotaladmincharge(tagValues[25]);
		pb.setBlth1(tagValues[26]);
		pb.setDuedate1(tagValues[27]);
		pb.setMeterreaddate1(tagValues[28]);
		pb.setRptag1(tagValues[29]);
		pb.setIncentive1(tagValues[30]);
		pb.setValuedatetax1(tagValues[31]);
		pb.setPinaltyfee1(tagValues[32]);
		pb.setSlalwbp1(tagValues[33]);
		pb.setSahlwbp1(tagValues[34]);
		pb.setSlawbp1(tagValues[35]);
		pb.setSahwbp1(tagValues[36]);
		pb.setSlakvarh1(tagValues[37]);
		pb.setSahkvarh1(tagValues[38]);

		pb.setBlth2(tagValues[39]);
		pb.setDuedate2(tagValues[40]);
		pb.setMeterreaddate2(tagValues[41]);
		pb.setRptag2(tagValues[42]);
		pb.setIncentive2(tagValues[43]);
		pb.setValuedatetax2(tagValues[44]);
		pb.setPinaltyfee2(tagValues[45]);
		pb.setSlalwbp2(tagValues[46]);
		pb.setSahlwbp2(tagValues[47]);
		pb.setSlawbp2(tagValues[48]);
		pb.setSahwbp2(tagValues[49]);
		pb.setSlakvarh2(tagValues[50]);
		pb.setSahkvarh2(tagValues[51]);
		
		pb.setBlth3(tagValues[52]);
		pb.setDuedate3(tagValues[53]);
		pb.setMeterreaddate3(tagValues[54]);
		pb.setRptag3(tagValues[55]);
		pb.setIncentive3(tagValues[56]);
		pb.setValuedatetax3(tagValues[57]);
		pb.setPinaltyfee3(tagValues[58]);
		pb.setSlalwbp3(tagValues[59]);
		pb.setSahlwbp3(tagValues[60]);
		pb.setSlawbp3(tagValues[61]);
		pb.setSahwbp3(tagValues[62]);
		pb.setSlakvarh3(tagValues[63]);
		pb.setSahkvarh3(tagValues[64]);
		
		pb.setBlth4(tagValues[65]);
		pb.setDuedate4(tagValues[66]);
		pb.setMeterreaddate4(tagValues[67]);
		pb.setRptag4(tagValues[68]);
		pb.setIncentive4(tagValues[69]);
		pb.setValuedatetax4(tagValues[70]);
		pb.setPinaltyfee4(tagValues[71]);
		pb.setSlalwbp4(tagValues[72]);
		pb.setSahlwbp4(tagValues[73]);
		pb.setSlawbp4(tagValues[74]);
		pb.setSahwbp4(tagValues[75]);
		pb.setSlakvarh4(tagValues[76]);
		pb.setSahkvarh4(tagValues[77]);
		
		pb.setAlamat(tagValues[78]);
		pb.setPlnnpwp(tagValues[79]);
		pb.setSubscribernpwp(tagValues[80]);
		pb.setTotaladmincharge(tagValues[81]);
		pb.setInfoteks(tagValues[82]);
		return pb;
	}
	public PLNPraBayar getPLNPraBayarRespone(Inquery inquery){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.inq</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ inquery.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			i++;
		}

		PLNPraBayar pb = new PLNPraBayar();
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan2(tagValues[3]);
		pb.setIdpelanggan3(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setBiayaadmin(tagValues[6]);
		pb.setUid(tagValues[7]);
		pb.setPin(tagValues[8]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setSwitcherid(tagValues[14]);
		pb.setNomormeter(tagValues[15]);
		pb.setIdpelanggan(tagValues[16]);
		pb.setFlag(tagValues[17]);
		pb.setNoref1(tagValues[18]);
		pb.setNoref2(tagValues[19]);
		pb.setVendingreceiptnumber(tagValues[20]);
		pb.setNamapelanggan(tagValues[21]);
		pb.setSubscribersegmentation(tagValues[22]);
		pb.setPowerconsumingcategory(tagValues[23]);
		pb.setMinorunitofadmincharge(tagValues[24]);
		pb.setAdmincharge(tagValues[25]);
		pb.setBuyingoption(tagValues[26]);
		pb.setDistributioncode(tagValues[27]);
		pb.setServiceunit(tagValues[28]);
		pb.setServiceunitphone(tagValues[29]);
		pb.setMaxkwhlimit(tagValues[30]);
		pb.setTotalrepeatunsoldtoken(tagValues[31]);
		pb.setUnsold1(tagValues[32]);
		pb.setUnsold2(tagValues[33]);
		pb.setTokenpln(tagValues[34]);
		pb.setMinorunitstampduty(tagValues[35]);
		pb.setStampduty(tagValues[36]);
		pb.setMinorunitppn(tagValues[37]);
		pb.setPpn(tagValues[38]);
		pb.setMinorunitppj(tagValues[39]);
		pb.setPpj(tagValues[40]);
		pb.setMinorunitcustomerpayablesinstallment(tagValues[41]);
		pb.setCustomerpayablesinstallment(tagValues[42]);
		pb.setMinorunitofpowerpurchase(tagValues[43]);
		pb.setPowerpurchase(tagValues[44]);
		pb.setMinorunitofpurchasedkwhunit(tagValues[45]);
		pb.setPurchasedkwhunit(tagValues[46]);
		pb.setInfotext(tagValues[47]);
		return pb;
	}
	public PLNPraBayar getPLNPraBayarRespone(Payment payment){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.pay</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ payment.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ payment.getIDPelanggan1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ payment.getIDPelanggan2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ payment.getIDPelanggan3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ payment.getNominal()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ payment.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ payment.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ payment.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ payment.getREF2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ payment.getREF3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			i++;
		}

		PLNPraBayar pb = new PLNPraBayar();
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan2(tagValues[3]);
		pb.setIdpelanggan3(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setBiayaadmin(tagValues[6]);
		pb.setUid(tagValues[7]);
		pb.setPin(tagValues[8]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setSwitcherid(tagValues[14]);
		pb.setNomormeter(tagValues[15]);
		pb.setIdpelanggan(tagValues[16]);
		pb.setFlag(tagValues[17]);
		pb.setNoref1(tagValues[18]);
		pb.setNoref2(tagValues[19]);
		pb.setVendingreceiptnumber(tagValues[20]);
		pb.setNamapelanggan(tagValues[21]);
		pb.setSubscribersegmentation(tagValues[22]);
		pb.setPowerconsumingcategory(tagValues[23]);
		pb.setMinorunitofadmincharge(tagValues[24]);
		pb.setAdmincharge(tagValues[25]);
		pb.setBuyingoption(tagValues[26]);
		pb.setDistributioncode(tagValues[27]);
		pb.setServiceunit(tagValues[28]);
		pb.setServiceunitphone(tagValues[29]);
		pb.setMaxkwhlimit(tagValues[30]);
		pb.setTotalrepeatunsoldtoken(tagValues[31]);
		pb.setUnsold1(tagValues[32]);
		pb.setUnsold2(tagValues[33]);
		pb.setTokenpln(tagValues[34]);
		pb.setMinorunitstampduty(tagValues[35]);
		pb.setStampduty(tagValues[36]);
		pb.setMinorunitppn(tagValues[37]);
		pb.setPpn(tagValues[38]);
		pb.setMinorunitppj(tagValues[39]);
		pb.setPpj(tagValues[40]);
		pb.setMinorunitcustomerpayablesinstallment(tagValues[41]);
		pb.setCustomerpayablesinstallment(tagValues[42]);
		pb.setMinorunitofpowerpurchase(tagValues[43]);
		pb.setPowerpurchase(tagValues[44]);
		pb.setMinorunitofpurchasedkwhunit(tagValues[45]);
		pb.setPurchasedkwhunit(tagValues[46]);
		pb.setInfotext(tagValues[47]);
		return pb;
	}
	
	
	public PLNnonTagList getResponePLNTaglist(Inquery inquery){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.inq</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ inquery.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}

	PLNnonTagList pb = new PLNnonTagList();
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan2(tagValues[3]);
		pb.setIdpelanggan3(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setBiayaadmin(tagValues[6]);
		pb.setUid(tagValues[7]);
		pb.setPin(tagValues[8]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setSwitcherId(tagValues[14]);

			pb.setRegistrationnumber(tagValues[15]);
		pb.setAreacode(tagValues[16]);
		pb.setTransactioncode(tagValues[17]);
		pb.setTransactionname(tagValues[18]);
		pb.setRegistrationdate(tagValues[19]);
		pb.setExpirationDate(tagValues[20]);
		pb.setSubscriberid(tagValues[21]);
		pb.setSubcribername(tagValues[22]);
		pb.setPlnrefnumber(tagValues[23]);
		pb.setSwrefnumber(tagValues[24]);
		
		pb.setServiceunit(tagValues[25]);
		pb.setServiceunitphone(tagValues[26]);
		pb.setTotaltransactionAmmountminor(tagValues[27]);
		pb.setTotaltransactionamount(tagValues[28]);
		pb.setPlnbillminourunit(tagValues[29]);
		pb.setPlnbillvalue(tagValues[30]);
		pb.setAdminchargeminorunit(tagValues[31]);
		pb.setAdmincharge(tagValues[32]);
		pb.setMutationnumber(tagValues[33]);
		pb.setSubcribersegmentation(tagValues[34]);
		pb.setPowerconsumingcategory(tagValues[35]);
		pb.setTotalrepeat(tagValues[36]);
		pb.setCustomerdetailode1(tagValues[37]);
		pb.setCustomerdetailminorunit1(tagValues[38]);
		pb.setCustomerDetailvalueamount1(tagValues[39]);
		pb.setCustomerdetailode2(tagValues[40]);
		pb.setCustomerdetailminorunit2(tagValues[41]);
		pb.setCustomerDetailvalueamount2(tagValues[42]);
		pb.setInfotext(tagValues[43]);
		
		
		
		
		
		
		return pb;
	}
	
	
	public TelkomGroup getTelkomGroupRespone(Inquery inquery){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.inq</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ inquery.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			//tagValues.add(matcher.group(1));
			//System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
		
		TelkomGroup pb = new TelkomGroup();
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan2(tagValues[3]);
		pb.setIdpelanggan3(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setBiayaadmin(tagValues[6]);
		pb.setUid(tagValues[7]);
		pb.setPin(tagValues[8]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setKodearea(tagValues[14]);
		pb.setNomortelepon(tagValues[15]);
		pb.setKodedivre(tagValues[16]);
		pb.setKodedatel(tagValues[17]);
		pb.setJumlahbill(tagValues[18]);
		pb.setNomorreferensi3(tagValues[19]);
		pb.setNilaitagihan3(tagValues[20]);

		pb.setNomorreferensi2(tagValues[21]);
		pb.setNilaitagihan2(tagValues[22]);


		pb.setNomorreferensi1(tagValues[23]);
		pb.setNilaitagihan1(tagValues[24]);

		//pb.setNpwp(tagValues[25]);
		pb.setNamapelanggan(tagValues[25]);
		
		return pb;
	}
	
	
	
	public TVKabel getTVkabelRespone(Inquery inquery){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.inq</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ inquery.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
		
	TVKabel pb = new TVKabel();
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan2(tagValues[3]);
		pb.setIdpelanggan3(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setBiayaadmin(tagValues[6]);
		pb.setUid(tagValues[7]);
		pb.setPin(tagValues[8]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setSwitcherId(tagValues[14]);
		pb.setBillercode(tagValues[15]);
		pb.setCustomerId(tagValues[16]);
		pb.setBillquantity(tagValues[17]);
		pb.setNoref1(tagValues[18]);
		pb.setNoref2(tagValues[19]);
		pb.setCustomerName(tagValues[20]);
		pb.setCustomeraddress(tagValues[21]);
		pb.setProductcategory(tagValues[22]);
		pb.setBillamount(tagValues[23]);
		pb.setPenalty(tagValues[24]);
		pb.setStampduty(tagValues[25]);
		pb.setPpn(tagValues[26]);
		pb.setAdmincharge(tagValues[27]);
		pb.setBillerrefnumber(tagValues[28]);
		pb.setPtname(tagValues[29]);
		pb.setBilleradminfee(tagValues[30]);
		pb.setMiscfee(tagValues[31]);
		pb.setMiscnumber(tagValues[32]);
		pb.setPeriode(tagValues[33]);
		pb.setDuedate(tagValues[34]);
		pb.setCustomeinfo1(tagValues[35]);
		pb.setCustomeinfo2(tagValues[36]);
		pb.setCustomeinfo3(tagValues[37]);
		return pb;
	}
	

	
	public MultiFinance	 getMutiFinanceRespone(Inquery inquery){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.inq</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ inquery.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
		
	MultiFinance pb = new MultiFinance();
	
	pb.setKodeproduk(tagValues[0]);
	pb.setWaktu(tagValues[1]);
	pb.setIdpelanggan1(tagValues[2]);
	pb.setIdpelanggan2(tagValues[3]);
	pb.setIdpelanggan3(tagValues[4]);
	pb.setNominal(tagValues[5]);
	pb.setBiayaadmin(tagValues[6]);
	pb.setUid(tagValues[7]);
	pb.setPin(tagValues[8]);
	pb.setRef1(tagValues[9]);
	pb.setRef2(tagValues[10]);
	pb.setRef3(tagValues[11]);
	pb.setStatus(tagValues[12]);
	pb.setKeterangan(tagValues[13]);
	pb.setSwitcherid(tagValues[14]);
	pb.setBillercode(tagValues[15]);
	pb.setCustomerid(tagValues[16]);
	pb.setBillquantity(tagValues[17]);
	pb.setNoref1(tagValues[18]);
	pb.setNoref2(tagValues[19]);
	pb.setCustomername(tagValues[20]);
	pb.setProductcategory(tagValues[21]);
	pb.setMinorunit1(tagValues[22]);
	pb.setBillamount(tagValues[23]);
	pb.setStampduty(tagValues[24]);
	pb.setPpn(tagValues[25]);
	pb.setAdmincharge(tagValues[26]);
	pb.setBillerrefnumber(tagValues[27]);
	pb.setPtname(tagValues[28]);
	pb.setBranchname(tagValues[29]);
	pb.setItemmerktype(tagValues[30]);
	pb.setChasisnumber(tagValues[31]);
	pb.setCarnumberl(tagValues[32]);
	pb.setTenor(tagValues[33]);
	pb.setLastpaidperiode(tagValues[34]);
	pb.setLastpaidduedate(tagValues[35]);
	//pb.setMinorunit2(tagValues[36]);
	pb.setOsinstallmentamount(tagValues[36]);
	pb.setOsdinstallmentperiod(tagValues[37]);
	pb.setOdinstallmentamount(tagValues[38]);
	pb.setOdpenaltyfee(tagValues[39]);
	pb.setBilleradminFree(tagValues[40]);
	pb.setMiscfee(tagValues[41]);
	pb.setMinmumpayamount(tagValues[42]);
	pb.setMaximunpayamount(tagValues[43]);
		return pb;
	}
	
	public PDAMRespone getPDAMRespone(Inquery inquery){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.inq</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ inquery.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
		PDAMRespone pb = new PDAMRespone();
		pb.setKodeProduksi(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdPelanggan1(tagValues[2]);
		pb.setIdPelanggan1(tagValues[3]);
		pb.setIdPelanggan1(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setBiayaadmin(tagValues[6]);
		pb.setUID(tagValues[7]);
		pb.setPin(tagValues[8]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setSwitcherId(tagValues[14]);
		pb.setBillercode(tagValues[15]);
		pb.setCustomerid1(tagValues[16]);
		pb.setCustomerid2(tagValues[17]);
		pb.setCustomerid3(tagValues[18]);
		pb.setBillquantity(tagValues[19]);
		pb.setNoref1(tagValues[20]);
		pb.setNoref2(tagValues[21]);
		pb.setCustomername(tagValues[22]);
		pb.setCustomeraddress(tagValues[23]);
		pb.setCustomerdetailinformation(tagValues[24]);
		pb.setBilleradmincharge(tagValues[25]);
		pb.setTotalbillamount(tagValues[26]);
		pb.setPdamname(tagValues[27]);
		
		pb.setMothperiod2(tagValues[28]);
		pb.setYearperiod2(tagValues[29]);
		pb.setFirstmeterread2(tagValues[30]);
		pb.setLastmeteread2(tagValues[31]);
		pb.setPenalty2(tagValues[32]);
		pb.setBillamount2(tagValues[33]);
		pb.setMiscamount2(tagValues[34]);

		pb.setMothperiod2(tagValues[35]);
		pb.setYearperiod2(tagValues[36]);
		pb.setFirstmeterread2(tagValues[37]);
		pb.setLastmeteread2(tagValues[38]);
		pb.setPenalty2(tagValues[39]);
		pb.setBillamount2(tagValues[40]);
		pb.setMiscamount2(tagValues[41]);

		pb.setMothperiod3(tagValues[42]);
		pb.setYearperiod3(tagValues[43]);
		pb.setFirstmeterread3(tagValues[44]);
		pb.setLastmeteread3(tagValues[45]);
		pb.setPenalty3(tagValues[46]);
		pb.setBillamount3(tagValues[47]);
		pb.setMiscamount3(tagValues[48]);
		
		pb.setMothperiod4(tagValues[15]);
		pb.setYearperiod4(tagValues[15]);
		pb.setFirstmeterread4(tagValues[15]);
		pb.setLastmeteread4(tagValues[15]);
		pb.setPenalty4(tagValues[15]);
		pb.setBillamount4(tagValues[15]);
		pb.setMiscamount4(tagValues[15]);
		
		pb.setMothperiod5(tagValues[15]);
		pb.setYearperiod5(tagValues[15]);
		pb.setFirstmeterread5(tagValues[15]);
		pb.setLastmeteread5(tagValues[15]);
		pb.setPenalty5(tagValues[15]);
		pb.setBillamount5(tagValues[15]);
		pb.setMiscamount5(tagValues[15]);
		
		pb.setMothperiod6(tagValues[15]);
		pb.setYearperiod6(tagValues[15]);
		pb.setFirstmeterread6(tagValues[15]);
		pb.setLastmeteread6(tagValues[15]);
		pb.setPenalty6(tagValues[15]);
		pb.setBillamount6(tagValues[15]);
		pb.setMiscamount6(tagValues[15]);
		
		
		return pb;
		
		
		
	}
	
	
	
	
	public TelPascaBayar getTelPascaBayarRespone(Inquery inquery){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.inq</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ inquery.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);
		System.out.println(result);
		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke " + (i+1));
			i++;
		}
		TelPascaBayar pb = new TelPascaBayar();
		pb.setKodeProduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan2(tagValues[3]);
		pb.setIdpelanggan3(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setBiayaadmin(tagValues[6]);
		pb.setUID(tagValues[7]);
		pb.setPin(tagValues[8]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setKodearea(tagValues[14]);
		pb.setNomortelepon(tagValues[15]);
		pb.setKodedivre(tagValues[16]);
		pb.setKodedatel(tagValues[17]);
		pb.setJumlahbill(tagValues[18]);
		pb.setNomorreferensi3(tagValues[19]);
		pb.setNilaitagihan3(tagValues[20]);
		pb.setNomorreferensi2(tagValues[21]);
		pb.setNilaitagihan2(tagValues[22]);
		pb.setNomorreferensi1(tagValues[23]);
		pb.setNilaitagihan1(tagValues[24]);
		pb.setNamapelanggan(tagValues[25]);
		pb.setNpwp(tagValues[26]);		
		return pb;
	}
	
	
	public AsuransiRespone getAnsuransiRespone(Inquery inquery){

		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.inq</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ inquery.getKodeProduk() 
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);


	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
	AsuransiRespone pb = new AsuransiRespone();
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan1(tagValues[3]);
		pb.setIdpelanggan1(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setPin(tagValues[8]);
		pb.setUid(tagValues[7]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setSwitcherid(tagValues[14]);
		pb.setCustomername(tagValues[15]);
		pb.setProductcategory(tagValues[16]);
		pb.setBillamount(tagValues[17]);
		pb.setPenalty(tagValues[18]);
		pb.setStampduty(tagValues[19]);
		pb.setPpn(tagValues[20]);
		pb.setAdmincharge(tagValues[21]);
		pb.setClaimamount(tagValues[22]);
		pb.setBillerrefnumber(tagValues[23]);
		pb.setPtname(tagValues[24]);
		pb.setLastpaidperiode(tagValues[25]);
		pb.setLastpaidduedate(tagValues[26]);
		pb.setBilleradminfee(tagValues[27]);
		pb.setMiscfee(tagValues[28]);
		pb.setMiscnumber(tagValues[29]);
		pb.setCustomerphonenumber(tagValues[30]);
		pb.setCustomeradress(tagValues[31]);
		pb.setAhliwarisphonenumber(tagValues[32]);
		pb.setAhliwarisaddress(tagValues[33]);	
		
		return pb;
		
		
		
	}
	
	
	
	
	public KartuKredit getKartuKreditRespone(Inquery inquery){

		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.inq</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ inquery.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getIDPelanggan3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ inquery.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);


	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
	KartuKredit pb = new KartuKredit();
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan1(tagValues[3]);
		pb.setIdpelanggan1(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setPin(tagValues[8]);
		pb.setUid(tagValues[7]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		return pb;
		
		
		
	}
	
	
	
	/*
	 * etc Respone
	 */
	
	public DataTransaksiRes getDataTransaksiRespone(DataTransaksi dt){

		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.datatransaksi</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ dt.getTanggal()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ dt.getTanggal2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ dt.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ dt.getiDPelanggan()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ dt.getLimit()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ dt.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ dt.getPin()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
		DataTransaksiRes pb = new DataTransaksiRes();
		pb.setTanggal1(tagValues[1]);
		pb.setTanggal2(tagValues[2]);
		pb.setKodeproduk(tagValues[3]);
		pb.setIdpelanggan(tagValues[4]);
		pb.setIdpelanggan(tagValues[5]);
		pb.setLimit(tagValues[6]);
		pb.setUid(tagValues[7]);
		pb.setPin(tagValues[8]);
		pb.setStatus(tagValues[9]);
		pb.setKeterangan(tagValues[10]);
		
		
		return pb;
		
	}
	
	
	public GantiPinRes getGantiPinRespone(GantiPin gp){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.balance</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				
				+ gp.getUid()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ gp.getPinLama()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ gp.getPinBaru()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
		
		GantiPinRes pb = new GantiPinRes();
		pb.setUid(tagValues[0]);
		pb.setPinlama(tagValues[1]);
		pb.setPinbaru(tagValues[2]);
		pb.setStatus(tagValues[3]);
		pb.setKeterangan(tagValues[4]);
		
		
		return pb;
		
	}
	
	public CekSaldoRes getCekSaldoRespone(CetakSaldo cs){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.inq</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				
				+ cs.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cs.getPin()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
		CekSaldoRes bp = new CekSaldoRes();
		bp.setUid(tagValues[0]);
		bp.setPin(tagValues[1]);
		
		return bp;
		
		
	}
	
	
	
	/*
	 * payment paramether respone
	 */

	public PLNPascaBayar getRequestBean(Payment py){
		//System.out.println("\n\n\nRef1 = "+py.getREF1());
		//System.out.println("Ref2 = "+py.getREF2());
		//System.out.println("Ref3 = "+py.getREF3()+"\n" +
		//		"ID1 = "+py.getIDPelanggan1()+"\n\n\n");
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.pay</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ py.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getNominal()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}

		PLNPascaBayar pb = new PLNPascaBayar();
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan2(tagValues[3]);
		pb.setIdpelanggan3(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setBiayaadmin(tagValues[6]);
		pb.setUid(tagValues[7]);
		pb.setPin(tagValues[8]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setSwitcherId(tagValues[14]);
		pb.setSubscriberId(tagValues[15]);
		pb.setBillstatus(tagValues[16]);
		pb.setPaymentstatus(tagValues[17]);
		pb.setTotaloutstandingbell(tagValues[18]);
		pb.setSwereferencenumber(tagValues[19]);
		pb.setSubscribename(tagValues[20]);
		pb.setServiceunit(tagValues[21]);
		pb.setServiceunitphone(tagValues[22]);
		pb.setSubscribeSegmentation(tagValues[23]);
		pb.setPowerconsumingcategory(tagValues[24]);
		pb.setTotaladmincharge(tagValues[25]);
		pb.setBlth1(tagValues[26]);
		pb.setDuedate1(tagValues[27]);
		pb.setMeterreaddate1(tagValues[28]);
		pb.setRptag1(tagValues[29]);
		pb.setIncentive1(tagValues[30]);
		pb.setValuedatetax1(tagValues[31]);
		pb.setPinaltyfee1(tagValues[32]);
		pb.setSlalwbp1(tagValues[33]);
		pb.setSahlwbp1(tagValues[34]);
		pb.setSlawbp1(tagValues[35]);
		pb.setSahwbp1(tagValues[36]);
		pb.setSlakvarh1(tagValues[37]);
		pb.setSahkvarh1(tagValues[38]);

		pb.setBlth2(tagValues[39]);
		pb.setDuedate2(tagValues[40]);
		pb.setMeterreaddate2(tagValues[41]);
		pb.setRptag2(tagValues[42]);
		pb.setIncentive2(tagValues[43]);
		pb.setValuedatetax2(tagValues[44]);
		pb.setPinaltyfee2(tagValues[45]);
		pb.setSlalwbp2(tagValues[46]);
		pb.setSahlwbp2(tagValues[47]);
		pb.setSlawbp2(tagValues[48]);
		pb.setSahwbp2(tagValues[49]);
		pb.setSlakvarh2(tagValues[50]);
		pb.setSahkvarh2(tagValues[51]);
		
		pb.setBlth3(tagValues[52]);
		pb.setDuedate3(tagValues[53]);
		pb.setMeterreaddate3(tagValues[54]);
		pb.setRptag3(tagValues[55]);
		pb.setIncentive3(tagValues[56]);
		pb.setValuedatetax3(tagValues[57]);
		pb.setPinaltyfee3(tagValues[58]);
		pb.setSlalwbp3(tagValues[59]);
		pb.setSahlwbp3(tagValues[60]);
		pb.setSlawbp3(tagValues[61]);
		pb.setSahwbp3(tagValues[62]);
		pb.setSlakvarh3(tagValues[63]);
		pb.setSahkvarh3(tagValues[64]);
		
		pb.setBlth4(tagValues[65]);
		pb.setDuedate4(tagValues[66]);
		pb.setMeterreaddate4(tagValues[67]);
		pb.setRptag4(tagValues[68]);
		pb.setIncentive4(tagValues[69]);
		pb.setValuedatetax4(tagValues[70]);
		pb.setPinaltyfee4(tagValues[71]);
		pb.setSlalwbp4(tagValues[72]);
		pb.setSahlwbp4(tagValues[73]);
		pb.setSlawbp4(tagValues[74]);
		pb.setSahwbp4(tagValues[75]);
		pb.setSlakvarh4(tagValues[76]);
		pb.setSahkvarh4(tagValues[77]);
		
		pb.setAlamat(tagValues[78]);
		pb.setPlnnpwp(tagValues[79]);
		pb.setSubscribernpwp(tagValues[80]);
		pb.setTotaladmincharge(tagValues[81]);
		pb.setInfoteks(tagValues[82]);
		
		
		
		
		
		
		
		
		return pb;
	}
	

	
	public PLNnonTagList getResponePLNTaglist(Payment py){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.pay</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ py.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getNominal()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}

	PLNnonTagList pb = new PLNnonTagList();
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan2(tagValues[3]);
		pb.setIdpelanggan3(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setBiayaadmin(tagValues[6]);
		pb.setUid(tagValues[7]);
		pb.setPin(tagValues[8]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setSwitcherId(tagValues[14]);

			pb.setRegistrationnumber(tagValues[15]);
		pb.setAreacode(tagValues[16]);
		pb.setTransactioncode(tagValues[17]);
		pb.setTransactionname(tagValues[18]);
		pb.setRegistrationdate(tagValues[19]);
		pb.setExpirationDate(tagValues[20]);
		pb.setSubscriberid(tagValues[21]);
		pb.setSubcribername(tagValues[22]);
		pb.setPlnrefnumber(tagValues[23]);
		pb.setSwrefnumber(tagValues[24]);
		
		pb.setServiceunit(tagValues[25]);
		pb.setServiceunitphone(tagValues[26]);
		pb.setTotaltransactionAmmountminor(tagValues[27]);
		pb.setTotaltransactionamount(tagValues[28]);
		pb.setPlnbillminourunit(tagValues[29]);
		pb.setPlnbillvalue(tagValues[30]);
		pb.setAdminchargeminorunit(tagValues[31]);
		pb.setAdmincharge(tagValues[32]);
		pb.setMutationnumber(tagValues[33]);
		pb.setSubcribersegmentation(tagValues[34]);
		pb.setPowerconsumingcategory(tagValues[35]);
		pb.setTotalrepeat(tagValues[36]);
		pb.setCustomerdetailode1(tagValues[37]);
		pb.setCustomerdetailminorunit1(tagValues[38]);
		pb.setCustomerDetailvalueamount1(tagValues[39]);
		pb.setCustomerdetailode2(tagValues[40]);
		pb.setCustomerdetailminorunit2(tagValues[41]);
		pb.setCustomerDetailvalueamount2(tagValues[42]);
		pb.setInfotext(tagValues[43]);
		
		
		
		
		
		
		return pb;
	}
	
	
	public TelkomGroup getTelkomGroupRespone(Payment py){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.pay</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ py.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getNominal()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
		
		TelkomGroup pb = new TelkomGroup();
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan2(tagValues[3]);
		pb.setIdpelanggan3(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setBiayaadmin(tagValues[6]);
		pb.setUid(tagValues[7]);
		pb.setPin(tagValues[8]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setKodearea(tagValues[14]);
		pb.setNomortelepon(tagValues[15]);
		pb.setKodedivre(tagValues[16]);
		pb.setKodedatel(tagValues[17]);
		pb.setJumlahbill(tagValues[18]);
		pb.setNomorreferensi3(tagValues[19]);
		pb.setNilaitagihan3(tagValues[20]);

		pb.setNomorreferensi2(tagValues[21]);
		pb.setNilaitagihan2(tagValues[22]);


		pb.setNomorreferensi1(tagValues[23]);
		pb.setNilaitagihan1(tagValues[24]);

		pb.setNpwp(tagValues[25]);
		
		return pb;
	}
	
	
	
	public TVKabel getTVkabelRespone(Payment py){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.pay</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ py.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getNominal()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
		
	TVKabel pb = new TVKabel();
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan2(tagValues[3]);
		pb.setIdpelanggan3(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setBiayaadmin(tagValues[6]);
		pb.setUid(tagValues[7]);
		pb.setPin(tagValues[8]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setSwitcherId(tagValues[14]);
		pb.setBillercode(tagValues[15]);
		pb.setCustomerId(tagValues[16]);
		pb.setBillquantity(tagValues[17]);
		pb.setNoref1(tagValues[18]);
		pb.setNoref2(tagValues[19]);
		pb.setCustomerName(tagValues[20]);
		pb.setCustomeraddress(tagValues[21]);
		pb.setProductcategory(tagValues[22]);
		pb.setBillamount(tagValues[23]);
		pb.setPenalty(tagValues[24]);
		pb.setStampduty(tagValues[25]);
		pb.setPpn(tagValues[26]);
		pb.setAdmincharge(tagValues[27]);
		pb.setBillerrefnumber(tagValues[28]);
		pb.setPtname(tagValues[29]);
		pb.setBilleradminfee(tagValues[30]);
		pb.setMiscfee(tagValues[31]);
		pb.setMiscnumber(tagValues[32]);
		pb.setPeriode(tagValues[33]);
		pb.setDuedate(tagValues[34]);
		pb.setCustomeinfo1(tagValues[35]);
		pb.setCustomeinfo2(tagValues[36]);
		pb.setCustomeinfo3(tagValues[37]);
		return pb;
	}
	

	
	public MultiFinance	 getMutiFinanceRespone(Payment py){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.pay</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ py.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getNominal()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		
		
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
		
	MultiFinance pb = new MultiFinance();
	
	pb.setKodeproduk(tagValues[0]);
	pb.setWaktu(tagValues[1]);
	pb.setIdpelanggan1(tagValues[2]);
	pb.setIdpelanggan2(tagValues[3]);
	pb.setIdpelanggan3(tagValues[4]);
	pb.setNominal(tagValues[5]);
	pb.setBiayaadmin(tagValues[6]);
	pb.setUid(tagValues[7]);
	pb.setPin(tagValues[8]);
	pb.setRef1(tagValues[9]);
	pb.setRef2(tagValues[10]);
	pb.setRef3(tagValues[11]);
	pb.setStatus(tagValues[12]);
	pb.setKeterangan(tagValues[13]);
	pb.setSwitcherid(tagValues[14]);
	pb.setBillercode(tagValues[15]);
	pb.setCustomerid(tagValues[16]);
	pb.setBillquantity(tagValues[17]);
	pb.setNoref1(tagValues[18]);
	pb.setNoref2(tagValues[19]);
	pb.setCustomername(tagValues[20]);
	pb.setProductcategory(tagValues[21]);
	pb.setMinorunit1(tagValues[22]);
	pb.setBillamount(tagValues[23]);
	pb.setStampduty(tagValues[24]);
	pb.setPpn(tagValues[25]);
	pb.setAdmincharge(tagValues[26]);
	pb.setBillerrefnumber(tagValues[27]);
	pb.setPtname(tagValues[28]);
	pb.setBranchname(tagValues[29]);
	pb.setItemmerktype(tagValues[30]);
	pb.setChasisnumber(tagValues[31]);
	pb.setCarnumberl(tagValues[32]);
	pb.setTenor(tagValues[33]);
	pb.setLastpaidperiode(tagValues[34]);
	pb.setLastpaidduedate(tagValues[35]);
	//pb.setMinorunit2(tagValues[36]);
	pb.setOsinstallmentamount(tagValues[36]);
	pb.setOsdinstallmentperiod(tagValues[37]);
	pb.setOdinstallmentamount(tagValues[38]);
	pb.setOdpenaltyfee(tagValues[39]);
	pb.setBilleradminFree(tagValues[40]);
	pb.setMiscfee(tagValues[41]);
	pb.setMinmumpayamount(tagValues[42]);
	pb.setMaximunpayamount(tagValues[43]);
		return pb;
	}
	
	public PDAMRespone getPDAMRespone(Payment py){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.pay</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ py.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getNominal()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
		PDAMRespone pb = new PDAMRespone();
		pb.setKodeProduksi(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdPelanggan1(tagValues[2]);
		pb.setIdPelanggan1(tagValues[3]);
		pb.setIdPelanggan1(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setBiayaadmin(tagValues[6]);
		pb.setUID(tagValues[7]);
		pb.setPin(tagValues[8]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setSwitcherId(tagValues[14]);
		pb.setBillercode(tagValues[15]);
		pb.setCustomerid1(tagValues[16]);
		pb.setCustomerid2(tagValues[17]);
		pb.setCustomerid3(tagValues[18]);
		pb.setBillquantity(tagValues[19]);
		pb.setNoref1(tagValues[20]);
		pb.setNoref2(tagValues[21]);
		pb.setCustomername(tagValues[22]);
		pb.setCustomeraddress(tagValues[23]);
		pb.setCustomerdetailinformation(tagValues[24]);
		pb.setBilleradmincharge(tagValues[25]);
		pb.setTotalbillamount(tagValues[26]);
		pb.setPdamname(tagValues[27]);
		
		pb.setMothperiod2(tagValues[28]);
		pb.setYearperiod2(tagValues[29]);
		pb.setFirstmeterread2(tagValues[30]);
		pb.setLastmeteread2(tagValues[31]);
		pb.setPenalty2(tagValues[32]);
		pb.setBillamount2(tagValues[33]);
		pb.setMiscamount2(tagValues[34]);

		pb.setMothperiod2(tagValues[35]);
		pb.setYearperiod2(tagValues[36]);
		pb.setFirstmeterread2(tagValues[37]);
		pb.setLastmeteread2(tagValues[38]);
		pb.setPenalty2(tagValues[39]);
		pb.setBillamount2(tagValues[40]);
		pb.setMiscamount2(tagValues[41]);

		pb.setMothperiod3(tagValues[42]);
		pb.setYearperiod3(tagValues[43]);
		pb.setFirstmeterread3(tagValues[44]);
		pb.setLastmeteread3(tagValues[45]);
		pb.setPenalty3(tagValues[46]);
		pb.setBillamount3(tagValues[47]);
		pb.setMiscamount3(tagValues[48]);
		
		pb.setMothperiod4(tagValues[15]);
		pb.setYearperiod4(tagValues[15]);
		pb.setFirstmeterread4(tagValues[15]);
		pb.setLastmeteread4(tagValues[15]);
		pb.setPenalty4(tagValues[15]);
		pb.setBillamount4(tagValues[15]);
		pb.setMiscamount4(tagValues[15]);
		
		pb.setMothperiod5(tagValues[15]);
		pb.setYearperiod5(tagValues[15]);
		pb.setFirstmeterread5(tagValues[15]);
		pb.setLastmeteread5(tagValues[15]);
		pb.setPenalty5(tagValues[15]);
		pb.setBillamount5(tagValues[15]);
		pb.setMiscamount5(tagValues[15]);
		
		pb.setMothperiod6(tagValues[15]);
		pb.setYearperiod6(tagValues[15]);
		pb.setFirstmeterread6(tagValues[15]);
		pb.setLastmeteread6(tagValues[15]);
		pb.setPenalty6(tagValues[15]);
		pb.setBillamount6(tagValues[15]);
		pb.setMiscamount6(tagValues[15]);
		
		
		return pb;
		
		
		
	}
	
	
	
	
	public TelPascaBayar getTelPascaBayarRespone(Payment py){
		
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.pay</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ py.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getNominal()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
	TelPascaBayar pb = new TelPascaBayar();
	pb.setKodeProduk(tagValues[0]);
	pb.setWaktu(tagValues[1]);
	pb.setIdpelanggan1(tagValues[2]);
	pb.setIdpelanggan2(tagValues[3]);
	pb.setIdpelanggan3(tagValues[4]);
	pb.setNominal(tagValues[5]);
	pb.setBiayaadmin(tagValues[6]);
	pb.setUID(tagValues[7]);
	pb.setPin(tagValues[8]);
	pb.setRef1(tagValues[9]);
	pb.setRef2(tagValues[10]);
	pb.setRef3(tagValues[11]);
	pb.setStatus(tagValues[12]);
	pb.setKeterangan(tagValues[13]);
	pb.setKodearea(tagValues[14]);
	pb.setNomortelepon(tagValues[15]);
	pb.setKodedivre(tagValues[16]);
	pb.setKodedatel(tagValues[17]);
	pb.setJumlahbill(tagValues[18]);
	pb.setNomorreferensi3(tagValues[19]);
	pb.setNilaitagihan3(tagValues[20]);
	pb.setNomorreferensi2(tagValues[21]);
	pb.setNilaitagihan2(tagValues[22]);
	pb.setNomorreferensi1(tagValues[23]);
	pb.setNilaitagihan1(tagValues[24]);
	pb.setNamapelanggan(tagValues[25]);
	pb.setNpwp(tagValues[26]);		
		return pb;
	}
	
	public AsuransiRespone getAnsuransiRespone(Payment py){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.pay</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ py.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getNominal()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
	AsuransiRespone pb = new AsuransiRespone();
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan1(tagValues[3]);
		pb.setIdpelanggan1(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setPin(tagValues[8]);
		pb.setUid(tagValues[7]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setSwitcherid(tagValues[14]);
		pb.setCustomername(tagValues[15]);
		pb.setProductcategory(tagValues[16]);
		pb.setBillamount(tagValues[17]);
		pb.setPenalty(tagValues[18]);
		pb.setStampduty(tagValues[19]);
		pb.setPpn(tagValues[20]);
		pb.setAdmincharge(tagValues[21]);
		pb.setClaimamount(tagValues[22]);
		pb.setBillerrefnumber(tagValues[23]);
		pb.setPtname(tagValues[24]);
		pb.setLastpaidperiode(tagValues[25]);
		pb.setLastpaidduedate(tagValues[26]);
		pb.setBilleradminfee(tagValues[27]);
		pb.setMiscfee(tagValues[28]);
		pb.setMiscnumber(tagValues[29]);
		pb.setCustomerphonenumber(tagValues[30]);
		pb.setCustomeradress(tagValues[31]);
		pb.setAhliwarisphonenumber(tagValues[32]);
		pb.setAhliwarisaddress(tagValues[33]);	
		
		return pb;
		
		
		
	}
	
	
	public KartuKredit getKartuKreditRespone(Payment py){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.pay</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
				+ py.getKodeProduk()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getIDPelanggan3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getNominal()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getPIN()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ py.getREF3()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
	KartuKredit  pb = new KartuKredit();
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan1(tagValues[3]);
		pb.setIdpelanggan1(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setPin(tagValues[8]);
		pb.setUid(tagValues[7]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
	
		return pb;
		
		
		
	}
	
	
	/*
	 * Cetak Ulang paramether respone
	 */
	
	public PLNPascaBayar getRequestBean(CetakUlang cu){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.cu</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
			
				+ cu.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getPin()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getRef1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getRef2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}

		PLNPascaBayar pb = new PLNPascaBayar();
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan2(tagValues[3]);
		pb.setIdpelanggan3(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setBiayaadmin(tagValues[6]);
		pb.setUid(tagValues[7]);
		pb.setPin(tagValues[8]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setSwitcherId(tagValues[14]);
		pb.setSubscriberId(tagValues[15]);
		pb.setBillstatus(tagValues[16]);
		pb.setPaymentstatus(tagValues[17]);
		pb.setTotaloutstandingbell(tagValues[18]);
		pb.setSwereferencenumber(tagValues[19]);
		pb.setSubscribename(tagValues[20]);
		pb.setServiceunit(tagValues[21]);
		pb.setServiceunitphone(tagValues[22]);
		pb.setSubscribeSegmentation(tagValues[23]);
		pb.setPowerconsumingcategory(tagValues[24]);
		pb.setTotaladmincharge(tagValues[25]);
		pb.setBlth1(tagValues[26]);
		pb.setDuedate1(tagValues[27]);
		pb.setMeterreaddate1(tagValues[28]);
		pb.setRptag1(tagValues[29]);
		pb.setIncentive1(tagValues[30]);
		pb.setValuedatetax1(tagValues[31]);
		pb.setPinaltyfee1(tagValues[32]);
		pb.setSlalwbp1(tagValues[33]);
		pb.setSahlwbp1(tagValues[34]);
		pb.setSlawbp1(tagValues[35]);
		pb.setSahwbp1(tagValues[36]);
		pb.setSlakvarh1(tagValues[37]);
		pb.setSahkvarh1(tagValues[38]);

		pb.setBlth2(tagValues[39]);
		pb.setDuedate2(tagValues[40]);
		pb.setMeterreaddate2(tagValues[41]);
		pb.setRptag2(tagValues[42]);
		pb.setIncentive2(tagValues[43]);
		pb.setValuedatetax2(tagValues[44]);
		pb.setPinaltyfee2(tagValues[45]);
		pb.setSlalwbp2(tagValues[46]);
		pb.setSahlwbp2(tagValues[47]);
		pb.setSlawbp2(tagValues[48]);
		pb.setSahwbp2(tagValues[49]);
		pb.setSlakvarh2(tagValues[50]);
		pb.setSahkvarh2(tagValues[51]);
		
		pb.setBlth3(tagValues[52]);
		pb.setDuedate3(tagValues[53]);
		pb.setMeterreaddate3(tagValues[54]);
		pb.setRptag3(tagValues[55]);
		pb.setIncentive3(tagValues[56]);
		pb.setValuedatetax3(tagValues[57]);
		pb.setPinaltyfee3(tagValues[58]);
		pb.setSlalwbp3(tagValues[59]);
		pb.setSahlwbp3(tagValues[60]);
		pb.setSlawbp3(tagValues[61]);
		pb.setSahwbp3(tagValues[62]);
		pb.setSlakvarh3(tagValues[63]);
		pb.setSahkvarh3(tagValues[64]);
		
		pb.setBlth4(tagValues[65]);
		pb.setDuedate4(tagValues[66]);
		pb.setMeterreaddate4(tagValues[67]);
		pb.setRptag4(tagValues[68]);
		pb.setIncentive4(tagValues[69]);
		pb.setValuedatetax4(tagValues[70]);
		pb.setPinaltyfee4(tagValues[71]);
		pb.setSlalwbp4(tagValues[72]);
		pb.setSahlwbp4(tagValues[73]);
		pb.setSlawbp4(tagValues[74]);
		pb.setSahwbp4(tagValues[75]);
		pb.setSlakvarh4(tagValues[76]);
		pb.setSahkvarh4(tagValues[77]);
		
		pb.setAlamat(tagValues[78]);
		pb.setPlnnpwp(tagValues[79]);
		pb.setSubscribernpwp(tagValues[80]);
		pb.setTotaladmincharge(tagValues[81]);
		pb.setInfoteks(tagValues[82]);
		
		
		
		
		
		
		
		
		return pb;
	}
	

	
	public PLNnonTagList getResponePLNTaglist(CetakUlang cu){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.cu</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
			
				+ cu.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getPin()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getRef1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getRef2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}

	PLNnonTagList pb = new PLNnonTagList();
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan2(tagValues[3]);
		pb.setIdpelanggan3(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setBiayaadmin(tagValues[6]);
		pb.setUid(tagValues[7]);
		pb.setPin(tagValues[8]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setSwitcherId(tagValues[14]);

			pb.setRegistrationnumber(tagValues[15]);
		pb.setAreacode(tagValues[16]);
		pb.setTransactioncode(tagValues[17]);
		pb.setTransactionname(tagValues[18]);
		pb.setRegistrationdate(tagValues[19]);
		pb.setExpirationDate(tagValues[20]);
		pb.setSubscriberid(tagValues[21]);
		pb.setSubcribername(tagValues[22]);
		pb.setPlnrefnumber(tagValues[23]);
		pb.setSwrefnumber(tagValues[24]);
		
		pb.setServiceunit(tagValues[25]);
		pb.setServiceunitphone(tagValues[26]);
		pb.setTotaltransactionAmmountminor(tagValues[27]);
		pb.setTotaltransactionamount(tagValues[28]);
		pb.setPlnbillminourunit(tagValues[29]);
		pb.setPlnbillvalue(tagValues[30]);
		pb.setAdminchargeminorunit(tagValues[31]);
		pb.setAdmincharge(tagValues[32]);
		pb.setMutationnumber(tagValues[33]);
		pb.setSubcribersegmentation(tagValues[34]);
		pb.setPowerconsumingcategory(tagValues[35]);
		pb.setTotalrepeat(tagValues[36]);
		pb.setCustomerdetailode1(tagValues[37]);
		pb.setCustomerdetailminorunit1(tagValues[38]);
		pb.setCustomerDetailvalueamount1(tagValues[39]);
		pb.setCustomerdetailode2(tagValues[40]);
		pb.setCustomerdetailminorunit2(tagValues[41]);
		pb.setCustomerDetailvalueamount2(tagValues[42]);
		pb.setInfotext(tagValues[43]);
		
		
		
		
		
		
		return pb;
	}
	
	
	public TelkomGroup getTelkomGroupRespone(CetakUlang	 cu){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.cu</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
			
				+ cu.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getPin()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getRef1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getRef2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
		
		TelkomGroup pb = new TelkomGroup();
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan2(tagValues[3]);
		pb.setIdpelanggan3(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setBiayaadmin(tagValues[6]);
		pb.setUid(tagValues[7]);
		pb.setPin(tagValues[8]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setKodearea(tagValues[14]);
		pb.setNomortelepon(tagValues[15]);
		pb.setKodedivre(tagValues[16]);
		pb.setKodedatel(tagValues[17]);
		pb.setJumlahbill(tagValues[18]);
		pb.setNomorreferensi3(tagValues[19]);
		pb.setNilaitagihan3(tagValues[20]);

		pb.setNomorreferensi2(tagValues[21]);
		pb.setNilaitagihan2(tagValues[22]);


		pb.setNomorreferensi1(tagValues[23]);
		pb.setNilaitagihan1(tagValues[24]);

		pb.setNpwp(tagValues[25]);
		
		return null;
	}	
	
	public TVKabel getTVkabelRespone(CetakUlang cu){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.cu</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
			
				+ cu.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getPin()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getRef1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getRef2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
		
	TVKabel pb = new TVKabel();
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan2(tagValues[3]);
		pb.setIdpelanggan3(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setBiayaadmin(tagValues[6]);
		pb.setUid(tagValues[7]);
		pb.setPin(tagValues[8]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setSwitcherId(tagValues[14]);
		pb.setBillercode(tagValues[15]);
		pb.setCustomerId(tagValues[16]);
		pb.setBillquantity(tagValues[17]);
		pb.setNoref1(tagValues[18]);
		pb.setNoref2(tagValues[19]);
		pb.setCustomerName(tagValues[20]);
		pb.setCustomeraddress(tagValues[21]);
		pb.setProductcategory(tagValues[22]);
		pb.setBillamount(tagValues[23]);
		pb.setPenalty(tagValues[24]);
		pb.setStampduty(tagValues[25]);
		pb.setPpn(tagValues[26]);
		pb.setAdmincharge(tagValues[27]);
		pb.setBillerrefnumber(tagValues[28]);
		pb.setPtname(tagValues[29]);
		pb.setBilleradminfee(tagValues[30]);
		pb.setMiscfee(tagValues[31]);
		pb.setMiscnumber(tagValues[32]);
		pb.setPeriode(tagValues[33]);
		pb.setDuedate(tagValues[34]);
		pb.setCustomeinfo1(tagValues[35]);
		pb.setCustomeinfo2(tagValues[36]);
		pb.setCustomeinfo3(tagValues[37]);
		return pb;
	}
	

	
	public MultiFinance	 getMutiFinanceRespone(CetakUlang cu){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.cu</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
			
				+ cu.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getPin()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getRef1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getRef2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		
		
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
		
	MultiFinance pb = new MultiFinance();
	
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan2(tagValues[3]);
		pb.setIdpelanggan3(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setBiayaadmin(tagValues[6]);
		pb.setUid(tagValues[7]);
		pb.setPin(tagValues[8]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setSwitcherid(tagValues[14]);
		pb.setBillercode(tagValues[15]);
		pb.setCustomerid(tagValues[16]);
		pb.setBillquantity(tagValues[17]);
		pb.setNoref1(tagValues[18]);
		pb.setNoref2(tagValues[19]);
		pb.setCustomername(tagValues[20]);
		pb.setProductcategory(tagValues[21]);
		pb.setMinorunit1(tagValues[22]);
		pb.setBillamount(tagValues[23]);
		pb.setStampduty(tagValues[24]);
		pb.setPpn(tagValues[25]);
		pb.setAdmincharge(tagValues[26]);
		pb.setBillerrefnumber(tagValues[27]);
		pb.setPtname(tagValues[28]);
		pb.setBranchname(tagValues[29]);
		pb.setItemmerktype(tagValues[30]);
		pb.setChasisnumber(tagValues[31]);
		pb.setCarnumberl(tagValues[32]);
		pb.setTenor(tagValues[33]);
		pb.setLastpaidperiode(tagValues[34]);
		pb.setLastpaidduedate(tagValues[35]);
		//pb.setMinorunit2(tagValues[36]);
		pb.setOsinstallmentamount(tagValues[36]);
		pb.setOsdinstallmentperiod(tagValues[37]);
		pb.setOdinstallmentamount(tagValues[38]);
		pb.setOdpenaltyfee(tagValues[39]);
		pb.setBilleradminFree(tagValues[40]);
		pb.setMiscfee(tagValues[41]);
		pb.setMinmumpayamount(tagValues[42]);
		pb.setMaximunpayamount(tagValues[43]);
		return pb;
	}
	
	public PDAMRespone getPDAMRespone(CetakUlang cu){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.cu</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
			
				+ cu.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getPin()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getRef1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getRef2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
		PDAMRespone pb = new PDAMRespone();
		pb.setKodeProduksi(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdPelanggan1(tagValues[2]);
		pb.setIdPelanggan1(tagValues[3]);
		pb.setIdPelanggan1(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setBiayaadmin(tagValues[6]);
		pb.setUID(tagValues[7]);
		pb.setPin(tagValues[8]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setSwitcherId(tagValues[14]);
		pb.setBillercode(tagValues[15]);
		pb.setCustomerid1(tagValues[16]);
		pb.setCustomerid2(tagValues[17]);
		pb.setCustomerid3(tagValues[18]);
		pb.setBillquantity(tagValues[19]);
		pb.setNoref1(tagValues[20]);
		pb.setNoref2(tagValues[21]);
		pb.setCustomername(tagValues[22]);
		pb.setCustomeraddress(tagValues[23]);
		pb.setCustomerdetailinformation(tagValues[24]);
		pb.setBilleradmincharge(tagValues[25]);
		pb.setTotalbillamount(tagValues[26]);
		pb.setPdamname(tagValues[27]);
		pb.setMothperiod2(tagValues[28]);
		pb.setYearperiod2(tagValues[29]);
		pb.setFirstmeterread2(tagValues[30]);
		pb.setLastmeteread2(tagValues[31]);
		pb.setPenalty2(tagValues[32]);
		pb.setBillamount2(tagValues[33]);
		pb.setMiscamount2(tagValues[34]);
		pb.setMothperiod2(tagValues[35]);
		pb.setYearperiod2(tagValues[36]);
		pb.setFirstmeterread2(tagValues[37]);
		pb.setLastmeteread2(tagValues[38]);
		pb.setPenalty2(tagValues[39]);
		pb.setBillamount2(tagValues[40]);
		pb.setMiscamount2(tagValues[41]);
		pb.setMothperiod3(tagValues[42]);
		pb.setYearperiod3(tagValues[43]);
		pb.setFirstmeterread3(tagValues[44]);
		pb.setLastmeteread3(tagValues[45]);
		pb.setPenalty3(tagValues[46]);
		pb.setBillamount3(tagValues[47]);
		pb.setMiscamount3(tagValues[48]);
		pb.setMothperiod4(tagValues[15]);
		pb.setYearperiod4(tagValues[15]);
		pb.setFirstmeterread4(tagValues[15]);
		pb.setLastmeteread4(tagValues[15]);
		pb.setPenalty4(tagValues[15]);
		pb.setBillamount4(tagValues[15]);
		pb.setMiscamount4(tagValues[15]);
		pb.setMothperiod5(tagValues[15]);
		pb.setYearperiod5(tagValues[15]);
		pb.setFirstmeterread5(tagValues[15]);
		pb.setLastmeteread5(tagValues[15]);
		pb.setPenalty5(tagValues[15]);
		pb.setBillamount5(tagValues[15]);
		pb.setMiscamount5(tagValues[15]);
		pb.setMothperiod6(tagValues[15]);
		pb.setYearperiod6(tagValues[15]);
		pb.setFirstmeterread6(tagValues[15]);
		pb.setLastmeteread6(tagValues[15]);
		pb.setPenalty6(tagValues[15]);
		pb.setBillamount6(tagValues[15]);
		pb.setMiscamount6(tagValues[15]);
		return pb;
	}
	
	
	
	
	public TelPascaBayar getTelPascaBayarRespone(CetakUlang cu){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.cu</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
			
				+ cu.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getPin()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getRef1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getRef2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
	TelPascaBayar pb = new TelPascaBayar();
	pb.setKodeProduk(tagValues[0]);
	pb.setWaktu(tagValues[1]);
	pb.setIdpelanggan1(tagValues[2]);
	pb.setIdpelanggan2(tagValues[3]);
	pb.setIdpelanggan3(tagValues[4]);
	pb.setNominal(tagValues[5]);
	pb.setBiayaadmin(tagValues[6]);
	pb.setUID(tagValues[7]);
	pb.setPin(tagValues[8]);
	pb.setRef1(tagValues[9]);
	pb.setRef2(tagValues[10]);
	pb.setRef3(tagValues[11]);
	pb.setStatus(tagValues[12]);
	pb.setKeterangan(tagValues[13]);
	pb.setKodearea(tagValues[14]);
	pb.setNomortelepon(tagValues[15]);
	pb.setKodedivre(tagValues[16]);
	pb.setKodedatel(tagValues[17]);
	pb.setJumlahbill(tagValues[18]);
	pb.setNomorreferensi3(tagValues[19]);
	pb.setNilaitagihan3(tagValues[20]);
	pb.setNomorreferensi2(tagValues[21]);
	pb.setNilaitagihan2(tagValues[22]);
	pb.setNomorreferensi1(tagValues[23]);
	pb.setNilaitagihan1(tagValues[24]);
	pb.setNamapelanggan(tagValues[25]);
	pb.setNpwp(tagValues[26]);		
		return pb;
		
		
		
	}
	
	
	
	
	public AsuransiRespone getAnsuransiRespone(CetakUlang cu){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.cu</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
			
				+ cu.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getPin()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getRef1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getRef2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
	AsuransiRespone pb = new AsuransiRespone();
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan1(tagValues[3]);
		pb.setIdpelanggan1(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setPin(tagValues[8]);
		pb.setUid(tagValues[7]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
		pb.setStatus(tagValues[12]);
		pb.setKeterangan(tagValues[13]);
		pb.setSwitcherid(tagValues[14]);
		pb.setCustomername(tagValues[15]);
		pb.setProductcategory(tagValues[16]);
		pb.setBillamount(tagValues[17]);
		pb.setPenalty(tagValues[18]);
		pb.setStampduty(tagValues[19]);
		pb.setPpn(tagValues[20]);
		pb.setAdmincharge(tagValues[21]);
		pb.setClaimamount(tagValues[22]);
		pb.setBillerrefnumber(tagValues[23]);
		pb.setPtname(tagValues[24]);
		pb.setLastpaidperiode(tagValues[25]);
		pb.setLastpaidduedate(tagValues[26]);
		pb.setBilleradminfee(tagValues[27]);
		pb.setMiscfee(tagValues[28]);
		pb.setMiscnumber(tagValues[29]);
		pb.setCustomerphonenumber(tagValues[30]);
		pb.setCustomeradress(tagValues[31]);
		pb.setAhliwarisphonenumber(tagValues[32]);
		pb.setAhliwarisaddress(tagValues[33]);	
		
		return pb;
		
		
		
	}
	
	
	
	
	
	
	
	public KartuKredit getKartuKreditRespone(CetakUlang cu){
		String xml = "<?xml version=\"1.0\"?>" + "\n" + "<methodCall>" + "\n"
				+ "<methodName>fastpay.cu</methodName>" + "\n" + "<params>"
				+ "\n" + "<param>" + "\n" + "<value><string>"
			
				+ cu.getUID()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getPin()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getRef1()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "<param>"
				+ "\n"
				+ "<value><string>"
				+ cu.getRef2()
				+ "</string></value>"
				+ "\n"
				+ "</param>"
				+ "\n"
				+ "</params>"
				+ "\n" + "</methodCall>";
		
		//System.out.println(xml.replaceAll("null", ""));
		String result = this.getRespone(xml);

		System.out.println(result);

	

		final Pattern TAG_REGEX = Pattern.compile("<string>(.*)</string>");
		final String[] tagValues = new String[result.length()];
		final Matcher matcher = TAG_REGEX.matcher(result);
		int i = 0;
		while (matcher.find()) {
			tagValues[i] = matcher.group(1);
			// tagValues.add(matcher.group(1));
			////System.out.println(tagValues[i] + " data ke" + i);
			i++;
		}
	KartuKredit pb = new KartuKredit();
		pb.setKodeproduk(tagValues[0]);
		pb.setWaktu(tagValues[1]);
		pb.setIdpelanggan1(tagValues[2]);
		pb.setIdpelanggan1(tagValues[3]);
		pb.setIdpelanggan1(tagValues[4]);
		pb.setNominal(tagValues[5]);
		pb.setPin(tagValues[8]);
		pb.setUid(tagValues[7]);
		pb.setRef1(tagValues[9]);
		pb.setRef2(tagValues[10]);
		pb.setRef3(tagValues[11]);
	
		
		return pb;
		
		
		
	}
	
	
	
	
	
	
	
}
