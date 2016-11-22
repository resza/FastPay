package co.id.keriss.fastpay.beans.request;

public class Payment {
	private String kodeProduk;
	private String IDPelanggan1;
	private String IDPelanggan2;
	private String IDPelanggan3;
	private String nominal;
	private String UID;
	private String PIN;
	private String REF1;
	private String REF2;
	private String REF3;
	public String getKodeProduk() {
		return kodeProduk;
	}
	public void setKodeProduk(String kodeProduk) {
		this.kodeProduk = kodeProduk;
	}
	public String getIDPelanggan1() {
		return IDPelanggan1;
	}
	public void setIDPelanggan1(String iDPelanggan1) {
		IDPelanggan1 = iDPelanggan1;
	}
	public String getIDPelanggan2() {
		return IDPelanggan2;
	}
	public void setIDPelanggan2(String iDPelanggan2) {
		IDPelanggan2 = iDPelanggan2;
	}
	public String getIDPelanggan3() {
		return IDPelanggan3;
	}
	public void setIDPelanggan3(String iDPelanggan3) {
		IDPelanggan3 = iDPelanggan3;
	}
	public String getNominal() {
		return nominal;
	}
	public String getREF2() {
		return REF2;
	}
	public void setREF2(String rEF2) {
		System.out.println("before setting Ref2 = "+rEF2);
		REF2 = rEF2;
	}
	public String getREF3() {
		return REF3;
	}
	public void setREF3(String rEF3) {
		System.out.println("before setting Ref3 = "+rEF3);
		REF3 = rEF3;
	}
	public void setNominal(String nominal) {
		this.nominal = nominal;
	}
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	public String getPIN() {
		return PIN;
	}
	public void setPIN(String pIN) {
		PIN = pIN;
	}
	public String getREF1() {
		return REF1;
	}
	public void setREF1(String rEF1) {
		System.out.println("before setting Ref1 = "+rEF1);
		REF1 = rEF1;
	}
	
	
	
}
